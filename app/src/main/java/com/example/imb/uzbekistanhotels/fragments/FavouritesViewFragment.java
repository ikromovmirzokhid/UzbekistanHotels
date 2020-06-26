package com.example.imb.uzbekistanhotels.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.adapters.FavHotelsListAdapter;


@SuppressLint("ValidFragment")
public class FavouritesViewFragment extends Fragment implements FavHotelsListAdapter.ChangeContainer {
    private String region;
    private FragmentTransaction transaction;
    private FavouriteHotelsListFragment favHotels;
    private HotelInfoViewFragment hotelInfo;

    private FavouritesViewFragment(String region) {
        this.region = region;
    }

    public static FavouritesViewFragment getInstance(String region) {
        return new FavouritesViewFragment(region);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_view, container, false);
        favHotels = FavouriteHotelsListFragment.getInstance(region);
        FavHotelsListAdapter.setChangeListener(this);
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
        }
        transaction.replace(R.id.fragmentContainer, favHotels);
        transaction.commit();

        return v;
    }

    @Override
    public void changeContainerListener() {
        hotelInfo = new HotelInfoViewFragment();
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
        }
        transaction.replace(R.id.fragmentContainer, hotelInfo).addToBackStack(null);
        transaction.commit();

    }
}
