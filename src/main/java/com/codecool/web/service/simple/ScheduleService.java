package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public class ScheduleService {

    private ScheduleDao schedulesDao;

    public ScheduleService(ScheduleDao schedulesDao){
        this.schedulesDao = schedulesDao;
    }

    public List<Schedule> getAllByUserId(int user_id) throws SQLException{
        return schedulesDao.findByUserId(user_id);
    }

    public void addNewSchedule(int user_id, String name, int cols) throws SQLException{
        schedulesDao.addSchedule(user_id, name, cols);
    }

    public void deleteSchedule(int schedule_id) throws SQLException{
        schedulesDao.deleteSchedule(schedule_id);
    }

    public void updateSchedule(Schedule schedule) throws SQLException{
        schedulesDao.updateSchedule(schedule);
    }

    public List<Schedule> getAllPublicNotOwned(int user_id) throws SQLException{
        return schedulesDao.getAllPublic(user_id);
    }

}
