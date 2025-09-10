package com.example.menu.model;

import java.util.Objects;

public class Restaurant {
    private final String id;
    private final String name;
    private int seats; // 좌석 수 (BStage에서 사용, AStage에서는 무시 가능)

    // 기본 생성자 (좌석 없는 경우)
    public Restaurant(String id, String name) {
        this(id, name, 0);
    }

    // 좌석까지 지정하는 생성자
    public Restaurant(String id, String name, int seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    @Override
    public String toString() {
        return name + (seats > 0 ? " (seats=" + seats + ")" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant r)) return false;
        return Objects.equals(id, r.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
