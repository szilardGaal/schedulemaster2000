package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> findAllByUserId(int user_id) throws SQLException;

    void addTask(String title, String content) throws SQLException;

    void removeTask(int id) throws SQLException;

    void updateTask(int id, String title, String content) throws SQLException;
}
