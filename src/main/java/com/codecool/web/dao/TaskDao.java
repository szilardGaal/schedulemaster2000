package com.codecool.web.dao;

import com.codecool.web.dto.ScheduleDisplayDto;
import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> findAllByUserId(int user_id) throws SQLException;

    void addTask(int userId, String title, String content) throws SQLException;

    void removeTask(int id) throws SQLException;

    void updateTask(int id, String title, String content) throws SQLException;

    Task findTaskById(int task_id) throws SQLException;

    List<Task> getTasksForSlot(int scheduleId, int columnId, int userId, String time) throws SQLException;
}
