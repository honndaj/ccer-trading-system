package com.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Menu;
import com.example.mapper.MenuMapper;
import com.example.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzq
 * @since 2023-04-17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Override
    public List<Menu> findAllMenus(String menuName) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(menuName)) {
            queryWrapper.like("name", menuName);
        }
        List<Menu> list = list(queryWrapper);
        // 父级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getParentId() == null).collect(Collectors.toList());
        for (Menu menu : parentNodes) {
            //子菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getParentId())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
