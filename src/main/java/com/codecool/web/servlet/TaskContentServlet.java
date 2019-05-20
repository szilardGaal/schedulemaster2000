package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;

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

            User user = (User) req.getSession().getAttribute("user");
            Task current = null;
            int userId = user.getId();
            int task_id = parseInt(req.getParameter("task-id"));

            List<Task> tasks = taskDao.findAllByUserId(userId);

            for (Task t : tasks) {
                if(t.getId() == task_id) {
                    current = t;
                }
            }

            sendMessage(resp, HttpServletResponse.SC_OK, current);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
