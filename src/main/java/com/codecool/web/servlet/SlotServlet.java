package com.codecool.web.servlet;

import com.codecool.web.dao.SlotTaskDao;
import com.codecool.web.dao.database.DatabaseSlotTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.service.simple.SlotTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/slots")
public class SlotServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(SlotServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            SlotTaskDao slotTaskDao = new DatabaseSlotTaskDao(connection);
            SlotTaskService slotTaskService = new SlotTaskService(slotTaskDao);

            String idString = req.getParameter("cellId");
            int columnId = Integer.parseInt(idString.split(",")[0]);
            String time = idString.split(",")[1];
            logger.info("Task for slot requested. Slot id: " + idString);

            Task task = slotTaskService.getTaskFromSlot(columnId, time);
            logger.info("Tasks retrieved.");

            if (task == null) {
                sendMessage(resp, HttpServletResponse.SC_OK, null);
            } else {
                sendMessage(resp, HttpServletResponse.SC_OK, task);
            }

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            SlotTaskDao slotTaskDao = new DatabaseSlotTaskDao(connection);
            SlotTaskService slotTaskService = new SlotTaskService(slotTaskDao);

            String idString = req.getParameter("cellId");
            int columnId = Integer.parseInt(idString.split(",")[0]);
            String time = idString.split(",")[1];

            slotTaskService.removeTaskFromSlot(columnId, time);

            sendMessage(resp, HttpServletResponse.SC_OK, null);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("Exception occurred.", ex);
        }
    }

}
