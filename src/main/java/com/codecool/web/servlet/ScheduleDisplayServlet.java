package com.codecool.web.servlet;

import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDisplayDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.dto.ScheduleDisplayDto;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.ScheduleService;
import com.codecool.web.service.simple.SimpleScheduleDisplayService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;


@WebServlet("/protected/schedule-display")
public class ScheduleDisplayServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseScheduleDisplayDao databaseScheduleDisplayDao = new DatabaseScheduleDisplayDao(connection);
            DatabaseTaskDao databaseTaskDao = new DatabaseTaskDao(connection);
            SimpleScheduleDisplayService scheduleDisplayService = new SimpleScheduleDisplayService(databaseScheduleDisplayDao, databaseTaskDao);

            int schedule_id = parseInt(req.getParameter("id"));
            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            ScheduleDisplayDto scheduleDisplayDto = scheduleDisplayService.createScheduleDisplay(schedule_id, userId);

            sendMessage(resp, HttpServletResponse.SC_OK, scheduleDisplayDto);
        } catch (SQLException ex) {
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(scheduleDao);

            int scheduleId = Integer.parseInt(req.getParameter("schedule-id"));
            int taskId = Integer.parseInt(req.getParameter("task-id"));
            int columnId = Integer.parseInt(req.getParameter("column-id"));
            String time = req.getParameter("time");

            scheduleService.addTaskToSchedule(scheduleId, taskId, columnId, time);

            sendMessage(resp, HttpServletResponse.SC_OK, null);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
