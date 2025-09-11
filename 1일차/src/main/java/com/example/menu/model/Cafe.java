package com.example.menu.model;

public class Cafe extends Place {
    public enum CafeCat { BAKERY, DESSERT, COFFEE, TEA }
    public enum PriceRange { UNDER_10000, FROM_10000_TO_15000, OVER_15000 }

    private CafeCat category;
    private PriceRange price;

    public Cafe(String id, String name, String tags, CafeCat category, PriceRange price) {
        super(id, name, tags);
        this.category = category; this.price = price;
    }

    public CafeCat getCategory() { return category; }
    public PriceRange getPrice() { return price; }
}
