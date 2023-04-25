package com.example.mapper;

import com.example.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
public interface HistoryMapper extends BaseMapper<History> {
    List<Map<String, Object>> selectWeightedAvgPrice();

    @Select("select area , price from sys_history where area = #{areaName} order by create_time DESC limit 1")
    Map<String, Object> getLatestByArea(String areaName);

    @Select("select kind as name, sum(count) as value from sys_history where kind = #{kind}")
    Map<String, Object> getKindCount(String kind);


    @Select("SELECT SUM(count) FROM sys_history WHERE area = #{area} AND kind = #{kind} AND DATE(create_time) = DATE(#{date})")
    Integer getDay(Timestamp date, String area, String kind);

    @Select("SELECT SUM(count) FROM sys_history WHERE area = #{area} AND kind = #{kind} AND create_time BETWEEN #{startDate} AND #{endDate}")
    Integer getMonthlyCount(Timestamp startDate, Timestamp endDate, String area, String kind);


}

