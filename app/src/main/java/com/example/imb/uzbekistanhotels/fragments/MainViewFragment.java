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
import android.widget.Toast;

import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.adapters.MainViewAdapter;


@SuppressLint("ValidFragment")
public class MainViewFragment extends Fragment implements MainViewAdapter.ChangeContainer {
    private FragmentTransaction transaction;
    private HotelInfoViewFragment hotelInfoFragment;
    private HotelsListFragment hotelList;
    private String region;

    private MainViewFragment(String region) {
        this.region = region;
    }

    public static MainViewFragment getInstance(String region) {
        return new MainViewFragment(region);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewAdapter.setChangeListener(this);

        hotelList = HotelsListFragment.getInstance(region);
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
        } else
            Toast.makeText(getContext(), "transaction is not working", Toast.LENGTH_SHORT).show();
        transaction.replace(R.id.fragmentContainer, hotelList);
        transaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_view, container, false);
    }

    @Override
    public void changeContainerListener() {
        hotelInfoFragment = new HotelInfoViewFragment();
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, hotelInfoFragment).addToBackStack(null);
            transaction.commit();
        } else
            Toast.makeText(getContext(), "transaction is not working", Toast.LENGTH_SHORT).show();
    }
}
