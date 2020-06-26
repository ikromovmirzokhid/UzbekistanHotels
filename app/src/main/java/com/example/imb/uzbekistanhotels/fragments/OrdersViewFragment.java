package com.example.imb.uzbekistanhotels.fragments;


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
import com.example.imb.uzbekistanhotels.adapters.OrdersListAdapter;
import com.example.imb.uzbekistanhotels.viewmodel.OrderingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersViewFragment extends Fragment {
    @BindView(R.id.rView)
    RecyclerView ordersList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private OrdersListAdapter adapter;
    private OrderingViewModel viewModel;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.hotels_list, container, false);
        ButterKnife.bind(this, v);
        ((MainActivity) getActivity()).setToolbar(toolbar);

        viewModel = ViewModelProviders.of(getActivity()).get(OrderingViewModel.class);
        viewModel.getOrders().observe(this, orders -> {
            adapter = new OrdersListAdapter(orders);
            ordersList.setAdapter(adapter);
            ordersList.setLayoutManager(new LinearLayoutManager(getContext()));
        });
//        if (size == 0) {
//            v = inflater.inflate(R.layout.no_orders_view, container, false);
//            ImageView background = v.findViewById(R.id.background);
//            Glide.with(getContext()).load(R.drawable.norder).into(background);
//        }
        return v;
    }

}
