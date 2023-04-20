package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("sys_role_menu")
@Data
@AllArgsConstructor
public class RoleMenu {
    private Integer roleId;
    private Integer menuId;
}
