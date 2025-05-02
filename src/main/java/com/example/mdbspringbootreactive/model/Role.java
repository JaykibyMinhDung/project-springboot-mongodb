package com.example.mdbspringbootreactive.model;


import lombok.Getter;
import lombok.Lombok;
import lombok.Setter;

@Getter
@Setter
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
}
