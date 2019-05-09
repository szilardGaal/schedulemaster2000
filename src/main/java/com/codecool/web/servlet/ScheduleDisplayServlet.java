package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDisplayDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDisplayDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.dto.ScheduleDisplayDto;
import com.codecool.web.service.simple.ScheduleService;
import com.codecool.web.service.simple.SimpleScheduleDisplayService;
import com.codecool.web.service.simple.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;


@WebServlet("/protected/schedule-display")
public class ScheduleDisplayServlet extends AbstractServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())){
            DatabaseScheduleDisplayDao databaseScheduleDisplayDao = new DatabaseScheduleDisplayDao(connection);
            SimpleScheduleDisplayService scheduleDisplayService = new SimpleScheduleDisplayService(databaseScheduleDisplayDao);

            int schedule_id = parseInt(req.getParameter("id"));

            ScheduleDisplayDto scheduleDisplayDto = scheduleDisplayService.createScheduleDisplay(schedule_id);

            sendMessage(resp, HttpServletResponse.SC_OK, scheduleDisplayDto);
        } catch (SQLException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }
}
