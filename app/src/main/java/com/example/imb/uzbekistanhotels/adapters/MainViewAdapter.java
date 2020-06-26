package com.example.imb.uzbekistanhotels.adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.fragments.HotelsListFragment;
import com.example.imb.uzbekistanhotels.models.Hotel;
import com.example.imb.uzbekistanhotels.viewmodel.HotelViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.ViewHolder> {
    private List<Hotel> hotels;
    private HotelsListFragment instance;
    private static ChangeContainer changeListener;
    private String region;

    public static void setChangeListener(ChangeContainer chListener) {
        changeListener = chListener;
    }

    public MainViewAdapter(List<Hotel> hotels, HotelsListFragment instance, String region) {
        this.instance = instance;
        this.hotels = hotels;
        this.region = region;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelViewModel model = ViewModelProviders.of(instance.getActivity()).get(HotelViewModel.class);
        holder.hName.setText(hotels.get(position).getName());
        holder.location.setText(hotels.get(position).getAddress());
        holder.price.setText("$" + String.valueOf(hotels.get(position).getPrice()));
        holder.rating.setText(hotels.get(position).getRating());
        Glide.with(holder.itemView).load(hotels.get(position).getImage()).into((holder.hImage));

        holder.location.setOnClickListener(v -> {
            Uri uri = Uri.parse("geo:" + hotels.get(position).getCoordinate());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            holder.itemView.getContext().startActivity(intent);
        });

        if (Double.parseDouble(hotels.get(position).getRating()) > 4d) {
            holder.condition.setText("Very Good");
            for (int i = 1; i < 5; i++)
                setStartImage(holder, holder.stars[i - 1], R.drawable.ic_star_black_24dp);
            setStartImage(holder, holder.stars[4], R.drawable.ic_star_grey_24dp);
        } else if (Double.parseDouble(hotels.get(position).getRating()) > 3d) {
            holder.condition.setText("Good");
            for (int i = 1; i < 4; i++)
                setStartImage(holder, holder.stars[i - 1], R.drawable.ic_star_black_24dp);
            setStartImage(holder, holder.stars[3], R.drawable.ic_star_grey_24dp);
            setStartImage(holder, holder.stars[4], R.drawable.ic_star_grey_24dp);
        }

        if (hotels.get(position).getIsFavorite() == 1)
            Glide.with(holder.itemView).load(R.drawable.ic_favorite_red_24dp).into((holder.favIcon));
        else
            Glide.with(holder.itemView).load(R.drawable.ic_favorite_black_24dp).into((holder.favIcon));
        holder.favIcon.setOnClickListener(view -> {
            if (hotels.get(position).getIsFavorite() == 1) {
                Glide.with(holder.itemView).load(R.drawable.ic_favorite_black_24dp).into((holder.favIcon));
                model.updateFavourite(region, hotels.get(position).getName(), 0);
                hotels.get(position).setIsFavorite(0);
                notifyItemChanged(position);
            } else {
                Glide.with(holder.itemView).load(R.drawable.ic_favorite_red_24dp).into((holder.favIcon));
                model.updateFavourite(region, hotels.get(position).getName(), 1);
                hotels.get(position).setIsFavorite(1);
                notifyItemChanged(position);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            model.setSelectHotel(region, hotels.get(position).getName());
            changeListener.changeContainerListener();
        });
    }

    private void setStartImage(ViewHolder holder, ImageView v, int d) {
        Glide.with(holder.itemView).load(d).into((v));
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public interface ChangeContainer {
        void changeContainerListener();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hotelImage)
        ImageView hImage;
        @BindView(R.id.favIcon)
        ImageView favIcon;
        @BindView(R.id.rating)
        TextView rating;
        @BindView(R.id.condition)
        TextView condition;
        @BindView(R.id.hName)
        TextView hName;
        @BindView(R.id.hLocation)
        TextView location;
        @BindView(R.id.hPrice)
        TextView price;
        @BindView(R.id.hView)
        ImageView viewDeal;
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
        ImageView stars[] = new ImageView[5];

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            stars[0] = star1;
            stars[1] = star2;
            stars[2] = star3;
            stars[3] = star4;
            stars[4] = star5;
        }
    }
}
