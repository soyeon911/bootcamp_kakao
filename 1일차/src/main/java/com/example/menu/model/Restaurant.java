package com.example.menu.model;

import java.util.Objects;

public class Restaurant {
    public enum Cuisine {
        KOREAN, CHINESE, JAPANESE, WESTERN, ASIAN, SNACK_BURGER, SALAD_SANDWICH, DESSERT
    }
    public enum PriceRange {
        UNDER_10000, FROM_10000_TO_15000, OVER_15000
    }

    private final String id;
    private final String name;
    private final Cuisine cuisine;
    private final PriceRange price;

    public Restaurant(String id, String name, Cuisine cuisine, PriceRange price) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Cuisine getCuisine() { return cuisine; }
    public PriceRange getPrice() { return price; }

    @Override
    public String toString() {
        return name + " [" + cuisine + ", " + price + "]";
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
