package com.example;

import com.example.common.SystemString;
import com.example.entity.History;
import com.example.service.IHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class CcerTradingSystemApplicationTests {

    @Resource
    IHistoryService historyService;

    @Test
    void deleteHistory() {
        Map<String, Object> mp = new HashMap<>();
        mp.put("ccer_from", 1);
        historyService.removeByMap(mp);
    }

    @Test
    void generatorHistory() {
        for(String area : SystemString.STRING_AREA) {
            for(String kind : SystemString.STRING_KIND) {
                LocalDateTime start = LocalDateTime.of(2022, 4, 1, 0, 0, 0);
                LocalDateTime end = LocalDateTime.of(2023, 3, 31, 0, 0, 0);

                long daysBetween = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());

                for (int i = 0; i <= daysBetween; i++) {
                    LocalDateTime currentDay = start.plusDays(i);

                    History day = new History();
                    day.setCreateTime(currentDay);
                    day.setArea(area);
                    day.setKind(kind);
                    day.setCcerFrom(1);
                    day.setCcerTo(2);

                    int max = 50;
                    int min = 40;

                    switch(area) {
                        case "北京": min = 80; max = 120;break;
                        case "广东": min = 60; max = 90;break;
                        case "上海": min = 55; max = 85;break;
                        case "深圳": min = 50; max = 70;break;
                        case "天津": min = 45; max = 75;break;
                        case "湖北": min = 30; max = 60;break;
                        default: min = 50;max = 80;
                    }

                    // 创建一个Random对象
                    Random random = new Random();

                    // 生成指定范围的随机数
                    int randomNumber = random.nextInt(max - min + 1) + min;

                    day.setCount(BigDecimal.valueOf(randomNumber * 10));
                    day.setPrice(BigDecimal.valueOf(randomNumber));
                    historyService.save(day);
                }

            }
        }
    }

}
