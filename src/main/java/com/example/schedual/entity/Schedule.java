package com.example.schedual.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author LINVI7
 * @Date 8/26/2020 8:41 AM
 * @Version 1.0
 */
@Table(name = "schedule")
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "appointment_time")
    private String appointmentDate;
    @Column(name = "schedule_time")
    private String scheduleTime;
    private boolean exec;
    @Column(name = "exec_time")
    private String execTime;

    public boolean isExec() {
        return exec;
    }

    public void setExec(boolean exec) {
        this.exec = exec;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }
}
