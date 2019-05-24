package com.codecool.web.dao;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDisplayDao {

    List<Task> getAllTaskForSchedule(int schedule_id) throws SQLException;

    Schedule findByScheduleId(int schedule_id) throws SQLException;
}
