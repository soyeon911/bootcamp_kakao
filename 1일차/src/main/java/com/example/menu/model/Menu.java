package com.example.menu.model;

import java.util.List;

public class Menu {
    private final String name;
    private final Restaurant.Cuisine cuisine;
    private final Restaurant.PriceRange price;
    private final List<String> restaurantIds;

    public Menu(String name, Restaurant.Cuisine cuisine, Restaurant.PriceRange price, List<String> restaurantIds) {
        this.name = name;
        this.cuisine = cuisine;
        this.price = price;
        this.restaurantIds = restaurantIds;
    }

    public String getName() { return name; }
    public Restaurant.Cuisine getCuisine() { return cuisine; }
    public Restaurant.PriceRange getPrice() { return price; }
    public List<String> getRestaurantIds() { return restaurantIds; }
}
