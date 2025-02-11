package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    public void runTask() {
        System.out.println("Task executed at: " + LocalTime.now());
    }
}