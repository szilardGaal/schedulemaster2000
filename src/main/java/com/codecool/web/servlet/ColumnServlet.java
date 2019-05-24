package com.codecool.web.servlet;

import com.codecool.web.dao.ColumnDao;
import com.codecool.web.dao.database.DatabaseColumnDao;
import com.codecool.web.model.Column;
import com.codecool.web.service.simple.ColumnService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/columns")
public class ColumnServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ColumnDao dao = new DatabaseColumnDao(connection);
            ColumnService service = new ColumnService(dao);

            int scheduleId = Integer.parseInt(req.getParameter("schedule-id"));

            List<Column> columns = service.getScheduleColumns(scheduleId);

            sendMessage(resp, HttpServletResponse.SC_OK, columns);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
