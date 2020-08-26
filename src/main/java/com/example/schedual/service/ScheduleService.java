package com.example.schedual.service;

import java.text.ParseException;
import org.springframework.stereotype.Service;

/**
 * @Author LINVI7
 * @Date 8/25/2020 11:23 PM
 * @Version 1.0
 */
@Service
public interface ScheduleService {
    public void schedule(String date,String time) throws ParseException;
}
