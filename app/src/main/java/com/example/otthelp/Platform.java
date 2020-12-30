package com.example.otthelp;

import java.util.List;

public class Platform {
    private String name;
    private List<String> titles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public Platform(String name, List<String> titles) {
        this.name = name;
        this.titles = titles;
    }
}
