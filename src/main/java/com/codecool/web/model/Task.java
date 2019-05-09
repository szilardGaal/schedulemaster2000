package com.codecool.web.model;

public final class Task extends AbstractModel {

    private final String title;
    private String content;

    private int begins;
    private int duration;

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
}
