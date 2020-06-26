package com.example.imb.uzbekistanhotels.models;

import android.database.Cursor;

import com.example.imb.uzbekistanhotels.utilities.GetInfoFromCursor;

public class Hotel {
    private int id, isFavorite, price;
    private String name, rating, address, coordinate;
    private byte[] image;

    private Hotel(int id, int isFavorite, String name, int price, String rating, String address, String coordinate, byte[] image) {
        this.id = id;
        this.isFavorite = isFavorite;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.address = address;
        this.coordinate = coordinate;
        this.image = image;
    }

    public static Hotel getInstance(Cursor cursor) {
        GetInfoFromCursor c = new GetInfoFromCursor(cursor);
        return new Hotel(c.getInt("id"), c.getInt("favourite"), c.getString("name"), c.getInt("cheapest_room"),
                c.getString("rating"), c.getString("location"), c.getString("coordinates"), c.getPhoto("main_photo"));
    }

    public byte[] getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
