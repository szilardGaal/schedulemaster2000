package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.PasswordHashService;
import com.codecool.web.service.simple.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

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
                logger.info("User registration initiated. " + userName);

                us.registerUser(userName, password, isAdmin);
                logger.info("Registration successful.");

                User user = userDao.findByUserName(userName);
                req.getSession().setAttribute("user", user);
                logger.info("User assigned to session.");

                sendMessage(resp, HttpServletResponse.SC_OK, user);

            } catch (NoSuchAlgorithmException ex) {
                ex.getMessage();
                logger.error("Exception occurred.", ex);
            } catch (InvalidKeySpecException ex) {
                ex.printStackTrace();
                logger.error("Exception occurred.", ex);
            }

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }
}
