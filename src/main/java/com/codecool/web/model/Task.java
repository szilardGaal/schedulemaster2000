package com.codecool.web.model;

import java.util.List;

public final class Task extends AbstractModel {

    private final String title;
    private String content;

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

    public void setColumns(List<Integer> columns) {
        this.columns = columns;
    }

    public List<Integer> getColumns() {
        return columns;
    }
}
