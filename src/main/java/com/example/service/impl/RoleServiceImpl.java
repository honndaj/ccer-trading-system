package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Menu;
import com.example.entity.Role;
import com.example.entity.RoleMenu;
import com.example.mapper.RoleMapper;
import com.example.mapper.RoleMenuMapper;
import com.example.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzq
 * @since 2023-04-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional//保证原子性
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        roleMenuMapper.deleteByRoleId(roleId);
        for(Integer id : menuIds) {
            roleMenuMapper.insert(new RoleMenu(roleId, id));
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
