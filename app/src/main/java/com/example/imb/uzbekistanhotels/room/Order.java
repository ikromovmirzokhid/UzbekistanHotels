package com.example.imb.uzbekistanhotels.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int adultsNum;
    private int childNum;
    private int roomNum;
    private String type;
    private String inDate;
    private String outDate;
    private String hotelName;
    private String email;
    private String phoneNum;
    private String totalCost;
    private String personName;
    private byte[] image;

    public Order(String hotelName, int adultsNum, int childNum, int roomNum, String type, String inDate, String outDate, String personName, String email, String phoneNum, String totalCost, byte[] image) {
        this.adultsNum = adultsNum;
        this.childNum = childNum;
        this.roomNum = roomNum;
        this.type = type;
        this.inDate = inDate;
        this.outDate = outDate;
        this.hotelName = hotelName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.totalCost = totalCost;
        this.personName = personName;
        this.image = image;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAdultsNum(int adultsNum) {
        this.adultsNum = adultsNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getPersonName() {
        return personName;
    }

    public byte[] getImage() {
        return image;
    }

    public long getId() {
        return id;
    }

    public int getAdultsNum() {
        return adultsNum;
    }

    public int getChildNum() {
        return childNum;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public String getType() {
        return type;
    }

    public String getInDate() {
        return inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getTotalCost() {
        return totalCost;
    }
}
