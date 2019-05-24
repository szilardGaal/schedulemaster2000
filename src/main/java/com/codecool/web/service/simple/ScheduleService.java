package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public class ScheduleService {

    private ScheduleDao schedulesDao;

    public ScheduleService(ScheduleDao schedulesDao) {
        this.schedulesDao = schedulesDao;
    }

    public List<Schedule> getAllByUserId(int user_id) throws SQLException {
        return schedulesDao.findByUserId(user_id);
    }

    public void addNewSchedule(int user_id, String name, int cols, boolean isPublic) throws SQLException {
        schedulesDao.addSchedule(user_id, name, cols, isPublic);
    }

    public void deleteSchedule(int schedule_id) throws SQLException {
        schedulesDao.deleteSchedule(schedule_id);
    }

    public void updateSchedule(String newTitle, int newColCount, boolean newVisibility, int id) throws SQLException {
        schedulesDao.updateSchedule(newTitle, newColCount, newVisibility, id);
    }

    public List<Schedule> getAllPublicNotOwned(int user_id) throws SQLException {
        return schedulesDao.getAllPublic(user_id);
    }

    public void addTaskToSchedule(int scheduleId, int taskId, int columnId, String time) throws SQLException {
        schedulesDao.addTaskToSchedule(scheduleId, taskId, columnId, time);
    }
}
