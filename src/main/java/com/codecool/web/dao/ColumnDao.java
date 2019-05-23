package com.codecool.web.dao;

import com.codecool.web.model.Column;

import java.sql.SQLException;
import java.util.List;

public interface ColumnDao {

    List<Column> getScheduleColumns(int scheduleId) throws SQLException;
}
