package com.example;

import com.example.entity.Now;
import com.example.service.INowService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

@EnableScheduling
@SpringBootApplication
public class CcerTradingSystemApplication {
    @Resource
    INowService nowService;

    @Scheduled(fixedRate = 60 * 10 * 1000)
    public void timedCheck() {
        LocalDateTime nowTime = LocalDateTime.now();
        int count = 0;
        for (Now now : nowService.list()) {
            Duration duration = Duration.between(now.getCreateTime(), nowTime);
            long days = duration.toDays();
            if(days > 0) {
                nowService.removeById(now.getId());
                count++;
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~now is " + nowTime);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~delete count: " + count);
    }

    public static void main(String[] args) {
        SpringApplication.run(CcerTradingSystemApplication.class, args);
    }

}
