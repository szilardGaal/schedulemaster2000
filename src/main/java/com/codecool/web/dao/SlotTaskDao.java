package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;

public interface SlotTaskDao {

    Task getTaskFromSlot(int columnId, String time) throws SQLException;

}
