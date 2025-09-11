package com.example.menu.model;

public class Place {
    protected String id;
    protected String name;
    protected String tags;

    public Place(String id, String name, String tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTags() { return tags; }
}