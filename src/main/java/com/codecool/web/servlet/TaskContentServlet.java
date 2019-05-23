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

import static java.lang.Integer.parseInt;

@WebServlet("/protected/taskContent")
public final class TaskContentServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(taskDao);

            int task_id = parseInt(req.getParameter("task-id"));

            Task task = taskService.findTaskById(task_id);

            sendMessage(resp, HttpServletResponse.SC_OK, task);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(taskDao);

            int userId = ((User)req.getSession().getAttribute("user")).getId();
            
            int scheduleId = Integer.parseInt(req.getParameter("schedule-id"));
            int columnId = Integer.parseInt(req.getParameter("column-id"));
            String time = req.getParameter("time");

            List<Task> tasks = taskService.getTasksForSlot(scheduleId, columnId, userId, time);

            sendMessage(resp, HttpServletResponse.SC_OK, tasks);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
