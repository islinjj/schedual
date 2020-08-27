package com.example.schedual.controller;

import com.example.schedual.entity.Schedule;
import com.example.schedual.service.ScheduleService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LINVI7
 * @Date 8/25/2020 12:55 PM
 * @Version 1.0
 */
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/schedule")
    public void schedule(@RequestBody Schedule schedule)
        throws ParseException {
        scheduleService.scheduleToDb(schedule);
    }
}
