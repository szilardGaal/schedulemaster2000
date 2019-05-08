package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.TaskService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/tasks")
public final class TaskServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Task> tasks = taskDao.findAllByUserId(userId);

            sendMessage(resp, HttpServletResponse.SC_OK, tasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(taskDao);

            String title = req.getParameter("title");
            String content = req.getParameter("content");

            taskService.addTask(title, content);

            doGet(req, resp);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
