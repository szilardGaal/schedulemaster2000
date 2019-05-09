package com.codecool.web.dao.database;

import com.codecool.web.dao.ScheduleDisplayDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseScheduleDisplayDao  extends AbstractDao implements ScheduleDisplayDao {

    public DatabaseScheduleDisplayDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Task> getAllTaskForSchedule(int schedule_id) throws SQLException {
        List<Task> allTask = new ArrayList<>();
        String sql = "SELECT * FROM tasks RIGHT JOIN (SELECT * FROM tasks_schedules RIGHT JOIN schedules ON schedules.id=tasks_schedules.schedule_id) schedule ON tasks.id = schedule.task_id WHERE schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, schedule_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = new Task(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"));
                    task.setBegins(resultSet.getInt("begins"));
                    task.setDuration(resultSet.getInt("duration"));
                    allTask.add(task);

                }
            }
            return allTask;
        }
    }

    @Override
    public Schedule findByScheduleId(int schedule_id) throws SQLException {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, schedule_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return new Schedule(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getString("title"), resultSet.getInt("numofcol"), resultSet.getBoolean("ispublic"));
                }
            }
        } return null;
    }






}
