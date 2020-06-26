package com.example.imb.uzbekistanhotels.models;

public class HotelName {
    private String region;
    private String name;

    public HotelName(String region, String name) {
        this.region = region;
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }
}
