package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.PasswordHashService;
import com.codecool.web.service.simple.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public final class RegisterServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService us = new UserService(userDao);
            PasswordHashService pwh = new PasswordHashService();

            String userName = req.getParameter("username");
            String password;
            String role = req.getParameter("role");
            boolean isAdmin = false;

            if (role.equalsIgnoreCase("Admin")) {
                isAdmin = true;
            }

            try {
                password = pwh.getHashedPassword(req.getParameter("password"));

                us.registerUser(userName, password, isAdmin);
                User user = userDao.findByUserName(userName);
                req.getSession().setAttribute("user", user);
                sendMessage(resp, HttpServletResponse.SC_OK, user);

            } catch (NoSuchAlgorithmException ex) {
                ex.getMessage();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
