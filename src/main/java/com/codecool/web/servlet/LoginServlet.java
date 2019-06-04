package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.PasswordHashService;
import com.codecool.web.service.simple.SimpleLoginService;
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

@WebServlet("/login")
public final class LoginServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);
            PasswordHashService pwh = new PasswordHashService();

            String userName = req.getParameter("username");
            String password = req.getParameter("password");
            String hashPassword = userDao.findByUserName(userName).getPassword();
            try {
                if (pwh.validatePassword(password, hashPassword)) {
                    logger.info("Login initiated.");
                    loginService.loginUser(userName);
                    logger.info("Login successful.");
                } else {
                    logger.error("Exception occurred during login.");
                    throw new ServiceException("Bad login");
                }
            } catch (NoSuchAlgorithmException ex) {
                ex.getMessage();
                logger.error("Exception occurred.", ex);
            } catch (InvalidKeySpecException ex) {
                ex.getMessage();
                logger.error("Exception occurred.", ex);
            }

            User user = loginService.loginUser(userName);
            req.getSession().setAttribute("user", user);

            sendMessage(resp, HttpServletResponse.SC_OK, user);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            logger.error("Exception occurred.", ex);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }
}
