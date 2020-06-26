package com.example.imb.uzbekistanhotels.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.imb.uzbekistanhotels.databases.Database;
import com.example.imb.uzbekistanhotels.models.Hotel;
import com.example.imb.uzbekistanhotels.models.HotelFullInfo;
import com.example.imb.uzbekistanhotels.models.HotelName;
import com.example.imb.uzbekistanhotels.room.Order;
import com.example.imb.uzbekistanhotels.room.OrderDatabase;

import java.util.List;

public class HotelViewModel extends ViewModel {
    private Database db = Database.getInstance();
    private MutableLiveData<HotelName> selectedHotel = new MutableLiveData<>();

    public void setSelectHotel(String region, String name) {
        HotelName hotel = new HotelName(region, name);
        selectedHotel.setValue(hotel);
    }


    public LiveData<HotelName> getSelectedHotel() {
        return selectedHotel;
    }


    public LiveData<List<Hotel>> getAllHotels(String region) {
        return db.getAllHotels(region);
    }

    public HotelFullInfo getHotelInfo(String name, String region) {
        return db.getHotelInfo(name, region);
    }

    public LiveData<List<Hotel>> getFavouriteHotels(String region) {
        return db.getFavouriteHotels(region);
    }


    public void updateFavourite(String name, String region, int val) {
        db.updateFavourite(region, name, val);
    }

}
