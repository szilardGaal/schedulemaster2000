package com.codecool.web.servlet;

import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dto.ScheduleDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.parseInt;

@WebServlet("/protected/schedule")
public class ScheduleServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Schedule> mySchedules = scheduleService.getAllByUserId(userId);
            List<Schedule> publicSchedules = scheduleService.getAllPublicNotOwned(userId);
            ScheduleDto scheduleDto = new ScheduleDto(mySchedules, publicSchedules);

            sendMessage(resp, HttpServletResponse.SC_OK, scheduleDto);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();


            String title = req.getParameter("schedule-name");
            int cols = parseInt(req.getParameter("schedule-cols"));
            String isPublicString = req.getParameter("is-public");
            boolean isPublic = Boolean.valueOf(isPublicString);

            scheduleService.addNewSchedule(userId, title, cols, isPublic);

            doGet(req, resp);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            int schedule_id = Integer.parseInt(req.getParameter("scheduleId"));

            scheduleService.deleteSchedule(schedule_id);

            doGet(req, resp);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            String newTitle = req.getParameter("new-title");
            int newColCount = Integer.parseInt(req.getParameter("new-cols"));
            boolean newVisibility = Boolean.valueOf(req.getParameter("new-is-public"));
            int id = Integer.parseInt(req.getParameter("id"));
            logger.info("Schedule update requested:" + id);

            scheduleService.updateSchedule(newTitle, newColCount, newVisibility, id);
            logger.info("Schedule update successful.");

            sendMessage(resp, HttpServletResponse.SC_OK, null);
            doGet(req, resp);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }
}
