package com.example.mapper;

import com.example.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzq
 * @since 2023-04-17
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where unique_key = #{uniqueKey}")
    Integer selectByUniqueKey(String uniqueKey);
}
