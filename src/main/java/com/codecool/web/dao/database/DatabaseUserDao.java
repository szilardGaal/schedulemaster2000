package com.codecool.web.dao.database;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.*;

public final class DatabaseUserDao extends AbstractDao implements UserDao {

    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User findByUserName(String userName) throws SQLException {
        if (userName == null || "".equals(userName)) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public void addUser(String userName, String email, boolean isAdmin) throws SQLException {
        String sql = "INSERT INTO users(name, password, isAdmin) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, email);
            statement.setBoolean(3, isAdmin);
            executeInsert(statement);
        }
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        return new User(id, name, password, isAdmin);
    }
}
