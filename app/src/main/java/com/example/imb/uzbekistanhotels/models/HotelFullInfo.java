package com.example.imb.uzbekistanhotels.models;

import android.database.Cursor;

import com.example.imb.uzbekistanhotels.utilities.GetInfoFromCursor;

public class HotelFullInfo {
    private int id, hasFreeWifi, hasFreeBreakfast, hasParking, hasSpa, isFavourite, cheapRoom, mediumRoom, luxRoom;
    private String name, rating, location, coordinates, phoneNum;
    private byte[] mainPhoto, photo1, photo2, photo3;

    private HotelFullInfo(int id, int hasFreeWifi, int hasFreeBreakfast, int hasParking, int hasSpa, int isFavourite, String name,
                          int cheapRoom, int mediumRoom, int luxRoom,
                          String rating, String location, String coordinates, byte[] mainPhoto, byte[] photo1,
                          byte[] photo2, byte[] photo3, String phoneNum) {
        this.id = id;
        this.hasFreeWifi = hasFreeWifi;
        this.hasFreeBreakfast = hasFreeBreakfast;
        this.hasParking = hasParking;
        this.hasSpa = hasSpa;
        this.isFavourite = isFavourite;
        this.name = name;
        this.cheapRoom = cheapRoom;
        this.mediumRoom = mediumRoom;
        this.luxRoom = luxRoom;
        this.rating = rating;
        this.location = location;
        this.coordinates = coordinates;
        this.mainPhoto = mainPhoto;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.phoneNum = phoneNum;
    }

    public static HotelFullInfo getInstance(Cursor cursor) {
        GetInfoFromCursor c = new GetInfoFromCursor(cursor);
        return new HotelFullInfo(c.getInt("id"), c.getInt("free_wifi"), c.getInt("free_breakfast"),
                c.getInt("parking"), c.getInt("spa"), c.getInt("favourite"), c.getString("name"),
                c.getInt("cheapest_room"), c.getInt("medium_room"), c.getInt("luxurious_room"),
                c.getString("rating"), c.getString("location"), c.getString("coordinates"), c.getPhoto("main_photo"),
                c.getPhoto("photo1"), c.getPhoto("photo2"), c.getPhoto("photo3"), c.getString("phone_number"));
    }

    public int getId() {
        return id;
    }

    public int getHasFreeWifi() {
        return hasFreeWifi;
    }

    public int getHasFreeBreakfast() {
        return hasFreeBreakfast;
    }

    public int getHasParking() {
        return hasParking;
    }

    public int getHasSpa() {
        return hasSpa;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public String getName() {
        return name;
    }

    public int getCheapRoom() {
        return cheapRoom;
    }

    public int getMediumRoom() {
        return mediumRoom;
    }

    public int getLuxRoom() {
        return luxRoom;
    }

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public byte[] getMainPhoto() {
        return mainPhoto;
    }

    public byte[] getPhoto1() {
        return photo1;
    }

    public byte[] getPhoto2() {
        return photo2;
    }

    public byte[] getPhoto3() {
        return photo3;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}
