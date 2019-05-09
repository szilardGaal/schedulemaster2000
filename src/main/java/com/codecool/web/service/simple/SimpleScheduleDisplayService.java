package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.ScheduleDisplayDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.sql.SQLException;

public class SimpleScheduleDisplayService {

    private ScheduleDao scheduleDao;
    private TaskDao taskDao;

    public SimpleScheduleDisplayService(ScheduleDao scheduleDao, TaskDao taskDao){
        this.scheduleDao = scheduleDao;
        this.taskDao = taskDao;
    }

    public ScheduleDisplayDto createDto(int schedule_id) throws SQLException {
        return new ScheduleDisplayDto(scheduleDao.findByScheduleId(schedule_id), taskDao.getAllTaskForSchedule(schedule_id));
    }

}
