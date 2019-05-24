package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;

import java.sql.SQLException;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String userName, String password, boolean isAdmin) throws SQLException {
        userDao.addUser(userName, password, isAdmin);
    }
}
