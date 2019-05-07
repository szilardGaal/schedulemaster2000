package com.codecool.web.dao.database;

import com.codecool.web.dao.SchedulesDao;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseScheduleDao extends AbstractDao implements SchedulesDao {

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

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        String name = resultSet.getString("name");
        int cols = resultSet.getInt("numofcol");
        boolean isPublic = resultSet.getBoolean("isPublic");
        return new Schedule(id, user_id, name, cols, isPublic);
    }
}
