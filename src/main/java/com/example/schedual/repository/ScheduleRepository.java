package com.example.schedual.repository;

import com.example.schedual.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author LINVI7
 * @Date 8/26/2020 1:02 PM
 * @Version 1.0
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

}
