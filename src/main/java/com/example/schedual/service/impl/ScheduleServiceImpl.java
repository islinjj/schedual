package com.example.schedual.service.impl;

import com.example.schedual.entity.Schedule;
import com.example.schedual.repository.ScheduleRepository;
import com.example.schedual.service.ScheduleService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final RabbitTemplate rabbitTemplate;

    public ScheduleServiceImpl(
        ScheduleRepository scheduleRepository,
        RabbitTemplate rabbitTemplate) {
        this.scheduleRepository = scheduleRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void scheduleToDb(Schedule schedule) throws ParseException {
        Schedule save = scheduleRepository.save(schedule);
        schedule(schedule,save.getId());
    }

    public void schedule(Schedule schedule,Integer id) throws ParseException {
        ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        concurrentTaskScheduler
            .schedule(printMsg("ring",id), new Date(simpleDateFormat.parse(schedule.getDate()).getTime()
                + getMinuteToMillisecond(schedule.getTime())
                + getHourToMillisecond(schedule.getTime())
                + getSecondToMillisecond(schedule.getTime())));
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

    public Runnable printMsg(String msg,Integer id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Send to MQ...." + msg);
                rabbitTemplate.convertAndSend("DirectRouting", "DirectExchange", msg);
                Schedule schedule = scheduleRepository.findById(id).orElse(null);
                schedule.setExec(true);
                scheduleRepository.save(schedule);
            }
        };

        return runnable;
    }
}
