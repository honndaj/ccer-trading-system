package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Constants;
import com.example.controller.dto.UserDTO;
import com.example.entity.Menu;
import com.example.entity.User;
import com.example.exception.ServiceException;
import com.example.mapper.RoleMapper;
import com.example.mapper.RoleMenuMapper;
import com.example.mapper.UserMapper;
import com.example.service.IMenuService;
import com.example.service.IUserService;
import com.example.utils.TokenUtils;
import org.apache.xmlbeans.impl.jam.JParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzq
 * @since 2023-04-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            String roleFlag = one.getRoleFlag();
            userDTO.setMenus(getRoleMenus(roleFlag));

            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }
    
    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());//TODO necessary?
        User one;
        // 处理异常情况
        try{
            one = getOne(queryWrapper);
        } catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    public List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        List<Integer> menuids = roleMenuMapper.selectByRoleId(roleId);
        List<Menu> menus = new ArrayList<>();
        for(Integer id : menuids) {
            menus.add(menuService.getById(id));
        }
        //找出一级菜单
        List<Integer> parentNodeIds = new ArrayList<>();
        for(Menu menu : menus) {
            if(menu.getPid() == null) {
                parentNodeIds.add(menu.getId());
            }
            else {
                parentNodeIds.add(menu.getPid());
            }
        }
        //排序加去重
        parentNodeIds = parentNodeIds.stream()
                .distinct() // 去重
                .sorted(Comparator.naturalOrder()) // 自然排序
                .collect(Collectors.toList());

        List<Menu> parentNodes = new ArrayList<>();
        for(Integer pid : parentNodeIds) {
            parentNodes.add(menuService.getById(pid));
        }

        for (Menu menu : parentNodes) {
            //子菜单
            menu.setChildren(menus.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }

    @Override
    public void updateMoney(Integer uid, BigDecimal num) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("money = money + " + num)
                .eq("id", uid);
        userMapper.update(null, updateWrapper);
    }
}
