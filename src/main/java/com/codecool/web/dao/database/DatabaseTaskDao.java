package com.codecool.web.dao.database;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseTaskDao extends AbstractDao implements TaskDao {

    public DatabaseTaskDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Task> findAllByUserId(int user_id) throws SQLException {
        if (user_id == 0) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            List<Task> tasks = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(fetchTask(resultSet));
                }
                return tasks;
            }
        }
    }

    @Override
    public void addTask(int userId, String title, String content) throws SQLException {
        String sql = "INSERT INTO tasks(user_id, title, content) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, title);
            statement.setString(3, content);
            executeInsert(statement);
        }
    }

    @Override
    public void removeTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Task fetchTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        return new Task(id, title, content);
    }

    @Override
    public void updateTask(int id, String title, String content) throws SQLException {
        String sql = "UPDATE tasks SET title = ?, content = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Task findTaskById(int task_id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, task_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchTask(resultSet);
                }
                return null;
            }
        }
    }

    @Override
    public List<Task> getTasksForSlot(int scheduleId, int columnId, int userId, String time) throws SQLException {
        String timePlusOne = Integer.parseInt(time.split(":")[0]) + 1 + ":00";
        String timeMinusOne = Integer.parseInt(time.split(":")[0]) - 1 + ":00";
        List<Task> tasks = new ArrayList<>();
        String sql = "select * from tasks\n" +
            "left join slots on tasks.id = slots.task_id\n" +
            "left join schedule_columns on slots.column_id = schedule_columns.id\n" +
            "left join schedules on schedule_columns.id = schedules.id\n" +
            "where ((schedules.id != ? or schedules.id is null) and tasks.user_id = ? and\n" +
            "(column_id != ? or column_id is null) and \n" +
            "(time is null or time = ? or time = ?))\n" +
            "or\n" +
            "(schedules.id = ? and column_id = ? and tasks.user_id = ? and\n" +
            "(time = ? or time = ? or time = ?))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, userId);
            statement.setInt(3, columnId);
            statement.setString(4, timePlusOne);
            statement.setString(5, timeMinusOne);
            statement.setInt(6, scheduleId);
            statement.setInt(7, columnId);
            statement.setInt(8, userId);
            statement.setString(9, timeMinusOne);
            statement.setString(10, time);
            statement.setString(11, timePlusOne);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(fetchTask(resultSet));
                }
            }
        }
        return tasks;
    }
}
