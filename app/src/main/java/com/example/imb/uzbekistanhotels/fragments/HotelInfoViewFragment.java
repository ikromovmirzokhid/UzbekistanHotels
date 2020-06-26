package com.example.imb.uzbekistanhotels.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.activities.MainActivity;
import com.example.imb.uzbekistanhotels.activities.OrderingActivity;
import com.example.imb.uzbekistanhotels.adapters.SlideShowImageAdapter;
import com.example.imb.uzbekistanhotels.models.HotelFullInfo;
import com.example.imb.uzbekistanhotels.viewmodel.HotelViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


@SuppressLint("ValidFragment")
public class HotelInfoViewFragment extends Fragment {
    @BindView(R.id.view_pager)
    ViewPager vPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.fav)
    ImageView favIcon;
    @BindView(R.id.hName)
    TextView hName;
    @BindView(R.id.condition)
    TextView condition;
    @BindView(R.id.tRating)
    TextView tRating;
    @BindView(R.id.freeWifi)
    TextView freeWifi;
    @BindView(R.id.parking)
    TextView parking;
    @BindView(R.id.spa)
    TextView spa;
    @BindView(R.id.fBreakfast)
    TextView freeBreakfast;
    @BindView(R.id.order)
    Button order;
    @BindView(R.id.phoneNum)
    TextView phoneNum;
    @BindView(R.id.hLocation)
    TextView address;
    @BindView(R.id.star1)
    ImageView star1;
    @BindView(R.id.star2)
    ImageView star2;
    @BindView(R.id.star3)
    ImageView star3;
    @BindView(R.id.star4)
    ImageView star4;
    @BindView(R.id.star5)
    ImageView star5;
    @BindView(R.id.hPrice1)
    TextView hPrice1;
    @BindView(R.id.hPrice2)
    TextView hPrice2;
    @BindView(R.id.hPrice3)
    TextView hPrice3;
    private TextView price[] = new TextView[3];
    private ImageView stars[] = new ImageView[5];
    private HotelViewModel viewModel;
    private String name, region;
    private SlideShowImageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_info_view, container, false);
        ButterKnife.bind(this, v);

        android.support.v7.widget.Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setToolbar(toolbar);
        toolbar.setNavigationOnClickListener(v1 -> {
            getActivity().onBackPressed();
        });

        stars[0] = star1;
        stars[1] = star2;
        stars[2] = star3;
        stars[3] = star4;
        stars[4] = star5;

        price[0] = hPrice1;
        price[1] = hPrice2;
        price[2] = hPrice3;


        viewModel = ViewModelProviders.of(getActivity()).get(HotelViewModel.class);
        viewModel.getSelectedHotel().observe(this, hotel -> {
            if (hotel != null) {
                name = hotel.getName();
                region = hotel.getRegion();
                dataInitialize(viewModel.getHotelInfo(hotel.getName(), hotel.getRegion()));
            }
        });
        return v;
    }

    private void dataInitialize(HotelFullInfo hotelFullInfo) {
        hName.setText(hotelFullInfo.getName());
        tRating.setText(hotelFullInfo.getRating());
        phoneNum.setText(hotelFullInfo.getPhoneNum());
        address.setText(hotelFullInfo.getLocation());
        address.setOnClickListener(v -> {
            Uri uri = Uri.parse("geo:" + hotelFullInfo.getCoordinates());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        });

        phoneNum.setOnClickListener(v -> {
            Uri uri = Uri.parse("tel:" + hotelFullInfo.getPhoneNum());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        });

        initializeStars(hotelFullInfo);

        initializePrices(hotelFullInfo);

        initializeAmenities(hotelFullInfo);

        initializeIsFavourite(hotelFullInfo);

        initializeViewPager(hotelFullInfo);


    }

    private void initializeViewPager(HotelFullInfo hotelFullInfo) {
        List<byte[]> images = new ArrayList<>();
        images.add(hotelFullInfo.getMainPhoto());
        images.add(hotelFullInfo.getPhoto1());
        images.add(hotelFullInfo.getPhoto2());
        images.add(hotelFullInfo.getPhoto3());

        adapter = new SlideShowImageAdapter(images, getContext());
        vPager.setAdapter(adapter);

        indicator.setViewPager(vPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());

    }

    private void initializeIsFavourite(HotelFullInfo hotelFullInfo) {
        if (hotelFullInfo.getIsFavourite() == 1) {
            favIcon.setImageResource(R.drawable.ic_favorite_red_24dp);
        } else favIcon.setImageResource(R.drawable.ic_favorite_black_24dp);
        favIcon.setOnClickListener(v -> {
            if (hotelFullInfo.getIsFavourite() == 1) {
                favIcon.setImageResource(R.drawable.ic_favorite_black_24dp);
                viewModel.updateFavourite(region, name, 0);
            } else {
                favIcon.setImageResource(R.drawable.ic_favorite_red_24dp);
                viewModel.updateFavourite(region, name, 1);
            }
        });
    }

    private void initializeAmenities(HotelFullInfo hotelFullInfo) {
        if (hotelFullInfo.getHasFreeWifi() != 1) {
            freeWifi.setTextColor(Color.GRAY);
        }
        if (hotelFullInfo.getHasParking() != 1) {
            parking.setTextColor(Color.GRAY);
        }
        if (hotelFullInfo.getHasFreeBreakfast() != 1) {
            freeBreakfast.setTextColor(Color.GRAY);
        }
        if (hotelFullInfo.getHasSpa() != 1) {
            spa.setTextColor(Color.GRAY);
        }

        order.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), OrderingActivity.class);
            intent.putExtra("region", region);
            intent.putExtra("name", name);
            getContext().startActivity(intent);
        });
    }

    private void initializePrices(HotelFullInfo hotelFullInfo) {
        price[0].setText("$" + hotelFullInfo.getLuxRoom());
        price[1].setText("$" + hotelFullInfo.getMediumRoom());
        price[2].setText("$" + hotelFullInfo.getCheapRoom());
    }

    private void initializeStars(HotelFullInfo hotelFullInfo) {
        if (Double.parseDouble(hotelFullInfo.getRating()) > 4d) {
            condition.setText("Very Good");
            for (int i = 0; i < 4; i++) {
                stars[i].setImageResource(R.drawable.ic_star_black_24dp);
            }
            stars[4].setImageResource(R.drawable.ic_star_grey_24dp);
        } else {
            condition.setText("Good");
            for (int i = 0; i < 3; i++) {
                stars[i].setImageResource(R.drawable.ic_star_black_24dp);
            }
            stars[3].setImageResource(R.drawable.ic_star_grey_24dp);
            stars[4].setImageResource(R.drawable.ic_star_grey_24dp);
        }
    }
}
