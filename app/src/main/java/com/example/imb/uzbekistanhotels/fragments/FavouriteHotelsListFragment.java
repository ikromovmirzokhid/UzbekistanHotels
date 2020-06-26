package com.example.imb.uzbekistanhotels.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.activities.MainActivity;
import com.example.imb.uzbekistanhotels.adapters.FavHotelsListAdapter;
import com.example.imb.uzbekistanhotels.models.Hotel;
import com.example.imb.uzbekistanhotels.viewmodel.HotelViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class FavouriteHotelsListFragment extends Fragment {

    @BindView(R.id.rView)
    RecyclerView hotelsList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String region;
    private FavHotelsListAdapter adapter;
    private HotelViewModel viewModel;
    private int size;
    private View v;

    private FavouriteHotelsListFragment(String region) {
        this.region = region;
    }

    public static FavouriteHotelsListFragment getInstance(String region) {
        return new FavouriteHotelsListFragment(region);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getFavouriteHotels(region).observe(this, hotels -> {
            adapter = new FavHotelsListAdapter(hotels, this, region);
            hotelsList.setAdapter(adapter);
            hotelsList.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.hotels_list, container, false);
        ButterKnife.bind(this, v);
        ((MainActivity) getActivity()).setToolbar(toolbar);
        viewModel = ViewModelProviders.of(getActivity()).get(HotelViewModel.class);
        size = viewModel.getFavouriteHotels(region).getValue().size();
        if (size == 0) {
            v = inflater.inflate(R.layout.fav_no_list, container, false);
            ImageView background = v.findViewById(R.id.background);
            Toolbar toolbar1 = v.findViewById(R.id.toolbar);
            ((MainActivity) getActivity()).setToolbar(toolbar);
            Glide.with(getContext()).load(R.drawable.favnolist).into(background);
        }
        return v;
    }
}
