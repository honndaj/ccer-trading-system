package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.SystemString;
import com.example.controller.dto.UserDTO;
import com.example.entity.Menu;
import com.example.entity.User;
import com.example.exception.ServiceException;
import com.example.mapper.RoleMapper;
import com.example.mapper.RoleMenuMapper;
import com.example.mapper.UserMapper;
import com.example.service.IMenuService;
import com.example.service.IUserService;
import com.example.utils.TokenGenerator;
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
        User user = getUserByUserDTO(userDTO);
        if(user != null) {
            BeanUtil.copyProperties(user, userDTO, true);
            userDTO.setToken(TokenGenerator.genToken(user.getId().toString(), user.getPassword()));
            String uniqueKey = user.getUniqueKey();
            userDTO.setMenus(getUserAuthMenus(uniqueKey));

            return userDTO;
        }else {
            throw new ServiceException(SystemString.SERVICE_ERROR, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User user = getUserByUserDTO(userDTO);
        if(user == null) {
            user = new User();
            BeanUtil.copyProperties(userDTO, user, true);
            save(user);
        }else {
            throw new ServiceException(SystemString.SERVICE_ERROR, "用户已存在");
        }
        return user;
    }
    
    private User getUserByUserDTO(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User user;

        try{
            user = getOne(queryWrapper);
        } catch (Exception e){
            throw new ServiceException(SystemString.SYSTEM_ERROR, "系统错误");
        }
        return user;
    }

    public List<Menu> getUserAuthMenus(String uniqueKey) {
        Integer roleId = roleMapper.selectByUniqueKey(uniqueKey);
        List<Integer> menuids = roleMenuMapper.selectByRoleId(roleId);
        List<Menu> menus = new ArrayList<>();
        for(Integer id : menuids) {
            menus.add(menuService.getById(id));
        }
        //找出一级菜单
        List<Integer> parentNodeIds = new ArrayList<>();
        for(Menu menu : menus) {
            if(menu.getParentId() == null) {
                parentNodeIds.add(menu.getId());
            }
            else {
                parentNodeIds.add(menu.getParentId());
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

        for (Menu parentNode : parentNodes) {
            parentNode.setChildren(menus.stream().filter(node -> parentNode.getId().equals(node.getParentId())).collect(Collectors.toList()));
        }
        return parentNodes;
    }

    @Override
    public void saveMoney(Integer uid, BigDecimal num) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("money = money + " + num)
                .eq("id", uid);
        userMapper.update(null, updateWrapper);
    }
}
