package com.example.schedual.component;

import com.example.schedual.entity.Schedule;
import com.example.schedual.repository.ScheduleRepository;
import com.example.schedual.service.ScheduleService;
import java.text.ParseException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostConstruct
    public void scheduled() throws ParseException {
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule:schedules){
            scheduleService.schedule(schedule.getDate(),schedule.getTime());
        }
    }
}
