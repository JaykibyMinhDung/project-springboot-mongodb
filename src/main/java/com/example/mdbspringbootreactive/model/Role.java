package com.example.mdbspringbootreactive.model;

public class Role {
    private String name;
    private int level;

    public Role(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public Role() {}

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
