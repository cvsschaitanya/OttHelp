package com.example.otthelp;

import java.util.List;

public class Title {
    private String name;
    private List<String> availableOn;

    public Title(String name, List<String> availableOn) {
        this.name = name;
        this.availableOn = availableOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAvailableOn() {
        return availableOn;
    }

    public void setAvailableOn(List<String> availableOn) {
        this.availableOn = availableOn;
    }
}
