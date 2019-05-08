package com.codecool.web.servlet;

import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dto.ScheduleDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.simple.ScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.parseInt;

@WebServlet("/schedule")
public class ScheduleServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())){
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            int user_id = parseInt(req.getParameter("user_id"));

            List<Schedule> mySchedules = scheduleService.getAllByUserId(user_id);
            List<Schedule> publicSchedules = scheduleService.getAllPublicNotOwned(user_id);
            ScheduleDto scheduleDto = new ScheduleDto(mySchedules, publicSchedules);


            sendMessage(resp, HttpServletResponse.SC_OK, scheduleDto);
        }catch (SQLException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }
}
