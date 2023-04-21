package com.example.mapper;

import com.example.entity.Now;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
public interface NowMapper extends BaseMapper<Now> {

    @Insert("insert into sys_now (uid, buy_sell, area, kind, price, count) values(#{uid}, 'buy', #{area}, #{kind}, #{price}, #{count})")
    void saveBuy(Now now);
}
