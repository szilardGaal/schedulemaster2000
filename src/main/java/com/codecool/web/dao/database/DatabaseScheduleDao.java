package com.codecool.web.dao.database;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseScheduleDao extends AbstractDao implements ScheduleDao {

    public DatabaseScheduleDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Schedule> findByUserId(int user_id) throws SQLException {
        if (user_id == 0) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        String sql = "SELECT * FROM schedules WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            List<Schedule> schedules = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    schedules.add(fetchSchedule(resultSet));
                }
                return schedules;
            }
        }
    }

    @Override
    public void addSchedule(int user_id, String name, int cols) throws SQLException {
        String sql = "INSERT INTO schedules(user_id, name, cols) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            statement.setString(1, name);
            statement.setInt(2, cols);
            executeInsert(statement);
        }
    }

    @Override
    public void deleteSchedule(int schedule_id) throws SQLException{
        String sql = "DELETE FROM schedules WHERE id=?";
        try (PreparedStatement  statement = connection.prepareStatement(sql)){
            statement.setInt(1, schedule_id);
            statement.execute();
        }
    }

    @Override
    public void updateSchedule(Schedule newSchedule) throws SQLException{
        String sql = "UPDATE schedules SET title=?, numOfCol=?, isPublic=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, newSchedule.getName());
            statement.setInt(2, newSchedule.getCols());
            statement.setBoolean(3, newSchedule.isPublic());
            statement.setInt(4, newSchedule.getId());
            statement.execute();
        }
    }

    @Override
    public List<Schedule> getAllPublic(int user_id) throws SQLException {
        List<Schedule> getAllPublic = new ArrayList<>();
        String sql = "SELECT * FROM schedules WHERE isPublic='true' AND user_id!=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, user_id);
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    getAllPublic.add(new Schedule(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getString("title"), resultSet.getInt("numofcol"), resultSet.getBoolean("ispublic")));
                }
            }
        } return getAllPublic;
    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        String name = resultSet.getString("title");
        int cols = resultSet.getInt("numofcol");
        boolean isPublic = resultSet.getBoolean("ispublic");
        return new Schedule(id, user_id, name, cols, isPublic);
    }


}
