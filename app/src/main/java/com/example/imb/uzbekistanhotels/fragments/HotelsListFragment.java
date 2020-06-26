package com.example.imb.uzbekistanhotels.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.activities.MainActivity;
import com.example.imb.uzbekistanhotels.adapters.MainViewAdapter;
import com.example.imb.uzbekistanhotels.viewmodel.HotelViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class HotelsListFragment extends Fragment {
    private MainViewAdapter adapter;
    @BindView(R.id.rView)
    RecyclerView listView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private HotelViewModel viewModel;
    private String region;

    private HotelsListFragment(String region) {
        this.region = region;
    }

    public static HotelsListFragment getInstance(String region) {
        return new HotelsListFragment(region);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(HotelViewModel.class);
        viewModel.getAllHotels(region).observe(this, hotels -> {
            adapter = new MainViewAdapter(hotels, this, region);
            listView.setAdapter(adapter);
            listView.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotels_list, container, false);
        ButterKnife.bind(this, v);

        ((MainActivity) getActivity()).setToolbar(toolbar);

        return v;
    }
}
