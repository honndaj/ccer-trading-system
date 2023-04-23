package com.example.mapper;

import com.example.entity.Ccer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
public interface CcerMapper extends BaseMapper<Ccer> {

    @Select("select sum(count) from sys_ccer where area = #{areaName}")
    Integer sumByArea(String areaName);

    @Select("select sum(count) from sys_ccer where kind = #{kindName}")
    Integer sumByKind(String kindName);
}
