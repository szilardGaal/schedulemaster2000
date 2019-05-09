package com.codecool.web.model;

import java.util.List;

public final class Task extends AbstractModel {

    private final String title;
    private String content;

    private int begins;
    private int duration;
    private List<Integer> columns;

    public Task(int id, String title, String content) {
        super(id);
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return content;
    }

    public void setBegins(int begins) {
        this.begins = begins;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBegins() {
        return begins;
    }

    public int getDuration() {
        return duration;
    }

    public void setColumns(List<Integer> columns) {
        this.columns = columns;
    }

    public List<Integer> getColumns() {
        return columns;
    }
}
