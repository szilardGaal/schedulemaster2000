package com.codecool.web.dao.database;

import com.codecool.web.dao.ColumnDao;
import com.codecool.web.model.Column;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseColumnDao extends AbstractDao implements ColumnDao {

    public DatabaseColumnDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Column> getScheduleColumns(int scheduleId) throws SQLException {
        List<Column> cols = new ArrayList<>();
        String sql = "SELECT * FROM schedule_columns WHERE schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cols.add(fetchColumn(resultSet));
                }
            }
        }
        return cols;
    }

    private Column fetchColumn(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int scheduleId = resultSet.getInt("schedule_id");
        String title = resultSet.getString("title");

        return new Column(id, scheduleId, title);
    }
}
