package com.example.menu.model;

public class Restaurant extends Place {
    public enum Cuisine { KOREAN, CHINESE, JAPANESE, WESTERN, ASIAN, SNACK_BURGER, SALAD_SANDWICH, DESSERT }
    public enum PriceRange { UNDER_10000, FROM_10000_TO_15000, OVER_15000 }

    protected Cuisine cuisine;
    protected PriceRange price;

    public Restaurant(String id, String name, String tags, Cuisine cuisine, PriceRange price) {
        super(id, name, tags);
        this.cuisine = cuisine; this.price = price;
    }

    public Cuisine getCuisine() { return cuisine; }
    public PriceRange getPrice() { return price; }
}
