package com.example.service;

import com.example.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzq
 * @since 2023-04-17
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
