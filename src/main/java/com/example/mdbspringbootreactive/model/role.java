package com.example.mdbspringbootreactive.model;

public class role {
    private String name;
    private int level;

    public role(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public role() {}

    @Override
    public String toString() {
        return "role{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
