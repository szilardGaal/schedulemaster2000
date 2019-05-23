package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDao {

    List<Schedule> findByUserId(int user_id) throws SQLException;

    void addSchedule(int user_id, String name, int cols, boolean isPublic) throws SQLException;

    void deleteSchedule(int schedule_id) throws SQLException;

    void updateSchedule(String newTitle, int newColCount, boolean newVisibility, int id) throws SQLException;

    List<Schedule> getAllPublic(int user_id) throws SQLException;

    void addTaskToSchedule(int scheduleId, int taskId, int columnId, String time) throws SQLException;
}
