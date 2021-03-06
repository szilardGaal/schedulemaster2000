package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/tasks")
public final class TaskServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

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
            logger.error("Exception occurred.", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            String title = req.getParameter("task-name");
            String content = req.getParameter("task-description");

            taskService.addTask(userId, title, content);

            doGet(req, resp);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(taskDao);

            int id = Integer.parseInt(req.getParameter("taskId"));
            logger.info("Task deletion requested:" + id);

            taskService.removeTask(id);
            logger.info("Task deletion successful.");

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(taskDao);

            String title = req.getParameter("new-title");
            String content = req.getParameter("new-content");
            int id = Integer.parseInt(req.getParameter("id"));

            taskService.updateTask(id, title, content);
            logger.info("Task updated:" + title + " - " + content);
            sendMessage(resp, HttpServletResponse.SC_OK, null);

            doGet(req, resp);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }
}
