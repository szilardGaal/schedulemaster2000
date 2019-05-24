package com.codecool.web.dao.database;

import com.codecool.web.dao.SlotTaskDao;
import com.codecool.web.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSlotTaskDao extends AbstractDao implements SlotTaskDao {

    public DatabaseSlotTaskDao(Connection connection) {
        super(connection);
    }

    @Override
    public Task getTaskFromSlot(int columnId, String time) throws SQLException {
        String sql = "select tasks.id, tasks.content, tasks.title from slots\n" +
            "join tasks on task_id = tasks.id\n" +
            "where column_id = ? and\n" +
            "time = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, columnId);
            statement.setString(2, time);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchTask(resultSet);
                }
            }
        }
        return null;
    }

    private Task fetchTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        return new Task(id, title, content);
    }
}
