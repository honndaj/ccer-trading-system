package com.example.service.impl;

import com.example.common.Constants;
import com.example.entity.History;
import com.example.mapper.HistoryMapper;
import com.example.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.YearMonth;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

    @Resource
    HistoryMapper historyMapper;

    @Override
    public List<List<Object>> getLineData() {
        List<Map<String, Object>> resultMapList = historyMapper.selectWeightedAvgPrice();
        List<List<Object>> resultList = new ArrayList<>();

        for (Map<String, Object> resultMap : resultMapList) {
            List<Object> row = new ArrayList<>();
            row.add(resultMap.get("date"));
            row.add(resultMap.get("area"));
            row.add(resultMap.get("weighted_avg_price"));
            resultList.add(row);
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> getAreaLatest() {
        List<Map<String, Object>> areaLatest = new ArrayList<>();
        for(String area : Constants.STRING_AREA) {
            areaLatest.add(historyMapper.getLatestByArea(area));
        }
        return areaLatest;
    }

    @Override
    public List<Map<String, Object>> getKindCount() {
        List<Map<String, Object>> kindCount = new ArrayList<>();
        for(String area : Constants.STRING_KIND) {
            kindCount.add(historyMapper.getKindCount(area));
        }
        return kindCount;
    }

    @Override
    public List<Map<String, Object>> getCountDay(String area, String kind) {
        //返回最近一周的数据
//        LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2023, 3, 31);
        LocalDate oneWeekAgo = today.minusDays(6);

        List<Map<String, Object>> result = new ArrayList<>();

        for (LocalDate date = oneWeekAgo; !date.isAfter(today); date = date.plusDays(1)) {
            Map<String, Object> dailyData = new HashMap<>();
            dailyData.put("name", getDayOfWeekAbbreviation(date));
            dailyData.put("value", historyMapper.getDay(convertLocalDateToTimestamp(date), area, kind));
            result.add(dailyData);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> getCountMonth(String area, String kind) {
        // 返回最近12个月的数据
        LocalDate today = LocalDate.of(2023, 3, 31);
        LocalDate twelveMonthsAgo = today.minusMonths(11).withDayOfMonth(1);

        List<Map<String, Object>> result = new ArrayList<>();

        for (LocalDate date = twelveMonthsAgo; !date.isAfter(today); date = date.plusMonths(1)) {
            YearMonth currentMonth = YearMonth.from(date);
            int totalDaysInMonth = currentMonth.lengthOfMonth();
            LocalDate lastDayOfMonth = date.withDayOfMonth(totalDaysInMonth);

            Map<String, Object> monthlyData = new HashMap<>();
            monthlyData.put("name", currentMonth.toString());
            monthlyData.put("value", historyMapper.getMonthlyCount(
                    convertLocalDateToTimestamp(date),
                    convertLocalDateToTimestamp(lastDayOfMonth),
                    area, kind));
            result.add(monthlyData);
        }

        return result;
    }

    public String getDayOfWeekAbbreviation(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E", Locale.ENGLISH);
        return date.format(formatter);
    }

    public Timestamp convertLocalDateToTimestamp(LocalDate date) {
        // 将 LocalDate 转换为 LocalDateTime，默认时间为 00:00:00
        LocalDateTime dateTime = date.atTime(LocalTime.MIDNIGHT);

        // 将 LocalDateTime 转换为 Timestamp
        return Timestamp.valueOf(dateTime);
    }
}
