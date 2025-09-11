package com.example.menu.model;

public class FranchiseRestaurant extends Restaurant {
    private String brandName;
    private String branchCode;

    public FranchiseRestaurant(String id, String name, String tags,
                               Cuisine cuisine, PriceRange price,
                               String brandName, String branchCode) {
        super(id, name, tags, cuisine, price);
        this.brandName = brandName; this.branchCode = branchCode;
    }

    public String getBrandName() { return brandName; }
    public String getBranchCode() { return branchCode; }
}
