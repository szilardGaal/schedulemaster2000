package com.codecool.web.dto;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.util.List;

public class ScheduleDisplayDto {

    private Schedule schedule;
    private List<Task> allTaskCForSchedule;

    public ScheduleDisplayDto(Schedule schedule, List<Task> allTaskForSchedule){
        this.schedule = schedule;
        this.allTaskCForSchedule = allTaskForSchedule;
    }

    public List<Task> getAllTaskCForSchedule() {
        return allTaskCForSchedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
