package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDisplayDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.ScheduleDisplayDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleDisplayService {

    private ScheduleDisplayDao scheduleDisplayDao;
    private TaskDao taskDao;

    public SimpleScheduleDisplayService(ScheduleDisplayDao scheduleDisplayDao, TaskDao taskDao) {
        this.scheduleDisplayDao = scheduleDisplayDao;
        this.taskDao = taskDao;
    }

    public ScheduleDisplayDto createScheduleDisplay(int schedule_id, int user_id) throws SQLException {
        return new ScheduleDisplayDto(findByScheduleId(schedule_id), getAllTaskForSchedule(schedule_id), getAllTaskForUser(user_id));
    }

    private List<Task> getAllTaskForSchedule(int schedule_id) throws SQLException {
        return scheduleDisplayDao.getAllTaskForSchedule(schedule_id);
    }

    private Schedule findByScheduleId(int schedule_id) throws SQLException {
        return scheduleDisplayDao.findByScheduleId(schedule_id);
    }

    private List<Task> getAllTaskForUser(int user_id) throws SQLException {
        return taskDao.findAllByUserId(user_id);
    }
}
