package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface SchedulesDao {

    List<Schedule> findByUserId(int user_id) throws SQLException;
}
