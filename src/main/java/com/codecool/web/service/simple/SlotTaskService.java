package com.codecool.web.service.simple;

import com.codecool.web.dao.SlotTaskDao;
import com.codecool.web.model.Task;

import java.sql.SQLException;

public class SlotTaskService {

    SlotTaskDao dao;

    public SlotTaskService(SlotTaskDao dao) {
        this.dao = dao;
    }

    public Task getTaskFromSlot(int columnId, String time) throws SQLException {
        return dao.getTaskFromSlot(columnId, time);
    }
}
