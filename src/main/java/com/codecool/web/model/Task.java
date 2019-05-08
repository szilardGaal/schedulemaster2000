package com.codecool.web.model;

public final class Task extends AbstractModel {

    private final String title;
    private String content;

    public Task(int id, String title, String content) {
        super(id);
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }
}
