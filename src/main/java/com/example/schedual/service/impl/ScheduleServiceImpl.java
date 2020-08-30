package com.example.schedual.service.impl;

import com.example.schedual.component.MailComponent;
import com.example.schedual.entity.Schedule;
import com.example.schedual.repository.ScheduleRepository;
import com.example.schedual.service.ScheduleService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    @Autowired
    MailComponent mailComponent;

    public ScheduleServiceImpl(
        ScheduleRepository scheduleRepository,
        RabbitTemplate rabbitTemplate) {
        this.scheduleRepository = scheduleRepository;
        this.rabbitTemplate = rabbitTemplate;
        create();
    }

    public void create() {
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.initialize();
    }

    public void scheduleToDb(Schedule schedule) throws ParseException {
        Schedule save = scheduleRepository.save(schedule);
        schedule(schedule,save.getId());
    }

    public void schedule(Schedule schedule,Integer id) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        threadPoolTaskScheduler
            .schedule(sendQueue("ring",id), new Date(simpleDateFormat.parse(schedule.getDate()).getTime()
                - getMinuteToMillisecond(schedule.getTime())
                - getHourToMillisecond(schedule.getTime())
                - getSecondToMillisecond(schedule.getTime())));
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

    public Runnable sendQueue(String msg,Integer id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Send to MQ...." + msg+" "+Thread.currentThread().getName());
                rabbitTemplate.convertAndSend("DirectExchange", "DirectRouting", msg);
                Schedule schedule = scheduleRepository.findById(id).orElse(null);
                schedule.setExec(true);
                schedule.setExecTime(new Date().toString());
                scheduleRepository.save(schedule);
                mailComponent.sendSimpleMail("1413537501@qq.com","提箱通知","请于"+schedule.getDate()+"提取集装箱");
            }
        };
        return runnable;
    }
}
