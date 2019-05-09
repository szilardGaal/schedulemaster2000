package com.codecool.web.dto;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.util.List;

public class ScheduleDisplayDto {

    private Schedule schedule;
    private List<Task> allTaskForSchedule;
    private List<Task> allTaskForUser;

    public ScheduleDisplayDto(Schedule schedule, List<Task> allTaskForSchedule, List<Task> allTaskForUser){
        this.schedule = schedule;
        this.allTaskForSchedule = allTaskForSchedule;
        this.allTaskForUser = allTaskForUser;
    }

    public List<Task> getAllTaskForSchedule() {
        return allTaskForSchedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Task> getAllTaskForUser() {
        return allTaskForUser;
    }
}
