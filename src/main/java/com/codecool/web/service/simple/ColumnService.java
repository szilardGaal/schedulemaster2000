package com.codecool.web.service.simple;

import com.codecool.web.model.Column;
import com.codecool.web.dao.ColumnDao;

import java.sql.SQLException;
import java.util.List;

public class ColumnService {

    private ColumnDao dao;

    public ColumnService(ColumnDao dao) {
        this.dao = dao;
    }

    public List<Column> getScheduleColumns(int scheduleId) throws SQLException {
        return dao.getScheduleColumns(scheduleId);
    }
}
