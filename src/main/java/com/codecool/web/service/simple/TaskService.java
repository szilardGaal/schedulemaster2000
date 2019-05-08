package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;

import java.sql.SQLException;

public class TaskService {

    private TaskDao taskDao;

    public TaskService (TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void addTask(String title, String content) throws SQLException {
        taskDao.addTask(title, content);
    }

    public void removeTask(int id) throws SQLException {
        taskDao.removeTask(id);
    }
}
