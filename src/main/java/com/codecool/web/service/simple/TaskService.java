package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.ScheduleDisplayDto;

import java.sql.SQLException;

public class TaskService {

    private TaskDao taskDao;

    public TaskService (TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void addTask(int userId, String title, String content) throws SQLException {
        taskDao.addTask(userId, title, content);
    }

    public void removeTask(int id) throws SQLException {
        taskDao.removeTask(id);
    }

    public void updateTask(int id, String title, String content) throws SQLException {
        taskDao.updateTask(id, title, content);
    }

}
