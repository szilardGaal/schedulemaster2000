package com.codecool.web.model;

public class Column extends AbstractModel {

    private int scheduleId;
    private String title;

    public Column(int id, int scheduleId, String title) {
        super(id);
        this.scheduleId = scheduleId;
        this.title = title;
    }

    public int getScheduleId() {
        return this.scheduleId;
    }

    public String getTitle() {
        return this.title;
    }
}
