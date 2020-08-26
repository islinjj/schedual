package com.example.schedual.component;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author LINVI7
 * @Date 8/24/2020 10:21 PM
 * @Version 1.0
 */
@Component
public class ScheduledComponent {

    @Async
    @Scheduled(cron = "0/10 * * * * *")
    public void scheduled(){
        System.out.println("lllll");
    }
}
