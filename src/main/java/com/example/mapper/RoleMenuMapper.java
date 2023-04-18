package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Delete("delete from role_menu where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") Integer roleId);

    @Select("select menu_id from role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(Integer roleId);
}
