package com.codecool.web.dto;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.util.List;

public class ScheduleDisplayDto {

    private Schedule schedule;
    private List<Task> allTaskForSchedule;

    public ScheduleDisplayDto(Schedule schedule, List<Task> allTaskForSchedule){
        this.schedule = schedule;
        this.allTaskForSchedule = allTaskForSchedule;
    }

    public List<Task> getAllTaskForSchedule() {
        return allTaskForSchedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
