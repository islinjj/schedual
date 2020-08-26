package com.example.schedual.service.impl;

import com.example.schedual.entity.Schedule;
import com.example.schedual.repository.ScheduleRepository;
import com.example.schedual.service.ScheduleService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

/**
 * @Author LINVI7
 * @Date 8/25/2020 10:54 PM
 * @Version 1.0
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(
        ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void scheduleToDb(String date, String time) throws ParseException {
        Schedule schedule = new Schedule();
        schedule.setDate(date);
        schedule.setTime(time);
        scheduleRepository.save(schedule);
        schedule(date, time);
    }

    public void schedule(String date, String time) throws ParseException {
        Schedule schedule = new Schedule();
        schedule.setDate(date);
        schedule.setTime(time);
        ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        concurrentTaskScheduler
            .schedule(printMsg("ring"), new Date(simpleDateFormat.parse(date).getTime()
                + getMinuteToMillisecond(time)
                + getHourToMillisecond(time)
                + getSecondToMillisecond(time)));
    }

    private int getSecondToMillisecond(String time) {
        return Integer.valueOf(time.substring(6, 8)) * 1000;
    }

    private int getMinuteToMillisecond(String time) {
        return Integer.valueOf(time.substring(3, 5)) * 60 * 1000;
    }

    private int getHourToMillisecond(String time){
        return Integer.valueOf(time.substring(0, 2)) * 60 * 60 * 1000;
    }

    public Runnable printMsg(String msg) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Ring...." + msg);
            }
        };

        return runnable;
    }
}
