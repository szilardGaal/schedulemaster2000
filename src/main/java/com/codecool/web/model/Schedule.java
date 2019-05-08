package com.codecool.web.model;

public final class Schedule extends AbstractModel {

    private final String name;
    private final int user_id;
    private int cols;
    private boolean isPublic;

    public Schedule(int id, int user_id, String name, int cols, boolean isPublic) {
        super(id);
        this.user_id = user_id;
        this.name = name;
        this.cols = cols;
        this.isPublic = isPublic;

    }

    public String getName() {
        return name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setPublic(boolean isPublic) {
        isPublic = isPublic;
    }

}
