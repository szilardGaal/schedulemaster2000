package com.codecool.web.dto;

import com.codecool.web.model.Schedule;

import java.util.List;

public class ScheduleDto {

    private List<Schedule> myList;
    private List<Schedule> publicList;

    public ScheduleDto(List<Schedule> myList, List<Schedule> publicList){
        this.myList = myList;
        this.publicList = publicList;
    }
}
