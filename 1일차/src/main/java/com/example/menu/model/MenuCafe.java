package com.example.menu.model;

import java.util.List;

public class MenuCafe {
    private final String name;
    private final Cafe.CafeCat category;
    private final Cafe.PriceRange price;
    private final List<String> cafeIds;

    public MenuCafe(String name, Cafe.CafeCat category, Cafe.PriceRange price, List<String> cafeIds) {
        this.name = name; this.category = category; this.price = price; this.cafeIds = cafeIds;
    }

    public String getName() { return name; }
    public Cafe.CafeCat getCategory() { return category; }
    public Cafe.PriceRange getPrice() { return price; }
    public List<String> getCafeIds() { return cafeIds; }
}
