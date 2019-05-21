package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Task;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

@WebServlet("/protected/taskContent")
public final class TaskContentServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);

            int task_id = parseInt(req.getParameter("task-id"));

            Task task = taskDao.findTaskById(task_id);

            sendMessage(resp, HttpServletResponse.SC_OK, task);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
