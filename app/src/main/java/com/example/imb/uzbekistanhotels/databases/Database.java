package com.example.imb.uzbekistanhotels.databases;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.imb.uzbekistanhotels.models.Hotel;
import com.example.imb.uzbekistanhotels.models.HotelFullInfo;

import java.util.ArrayList;
import java.util.List;

public class Database extends DBHelper {

    @SuppressLint("StaticFieldLeak")
    private static Database database;

    public static void init(Context context) {
        if (database == null)
            database = new Database(context, "hotels");
    }

    public static Database getInstance() {
        return database;
    }

    private Database(Context context, String mDataBaseName) {
        super(context, mDataBaseName);
    }

    public MutableLiveData<List<Hotel>> getAllHotels(String region) {
        MutableLiveData<List<Hotel>> hotelsList = new MutableLiveData<>();
        List<Hotel> hotels = new ArrayList<>();
        mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + region, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            do {
                hotels.add(Hotel.getInstance(cursor));
            }
            while (cursor.moveToNext());
        hotelsList.setValue(hotels);
        return hotelsList;
    }

    public HotelFullInfo getHotelInfo(String name, String region) {
        HotelFullInfo hotel;
        mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("Select * from " + region + " where name=" + "\"" + name + "\"", null);
        cursor.moveToFirst();
        hotel = HotelFullInfo.getInstance(cursor);
        return hotel;
    }

    public void updateFavourite(String name, String region, int val) {
        mDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favourite", val);
        mDatabase.update(region, values, "name=" + "\"" + name + "\"", null);
    }

    public MutableLiveData<List<Hotel>> getFavouriteHotels(String region) {
        MutableLiveData<List<Hotel>> hotelsList = new MutableLiveData<>();
        List<Hotel> hotels = new ArrayList<>();
        mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + region + " where  favourite=1", null);
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0)
            do {
                hotels.add(Hotel.getInstance(cursor));
            }
            while (cursor.moveToNext());
        hotelsList.setValue(hotels);
        return hotelsList;
    }
}
