package com.example.imb.uzbekistanhotels.adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.fragments.FavouriteHotelsListFragment;
import com.example.imb.uzbekistanhotels.models.Hotel;
import com.example.imb.uzbekistanhotels.viewmodel.HotelViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavHotelsListAdapter extends RecyclerView.Adapter<FavHotelsListAdapter.ViewHolder> {
    private List<Hotel> favHotelsData;
    private FavouriteHotelsListFragment instance;
    private String region;
    private static ChangeContainer changeListener;

    public static void setChangeListener(ChangeContainer chListener) {
        changeListener = chListener;
    }

    public FavHotelsListAdapter(List<Hotel> favHotelsData, FavouriteHotelsListFragment instance, String region) {
        this.favHotelsData = favHotelsData;
        this.instance = instance;
        this.region = region;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelViewModel viewModel = ViewModelProviders.of(instance.getActivity()).get(HotelViewModel.class);
        Glide.with(holder.itemView).load(favHotelsData.get(position).getImage()).into(holder.hImage);
        if (favHotelsData.get(position).getIsFavorite() == 1)
            Glide.with(holder.itemView).load(R.drawable.ic_favorite_red_24dp).into(holder.favIcon);
        else
            Glide.with(holder.itemView).load(R.drawable.ic_favorite_black_24dp).into(holder.favIcon);
        holder.rating.setText(favHotelsData.get(position).getRating());
        holder.location.setText(favHotelsData.get(position).getAddress());
        holder.hName.setText(favHotelsData.get(position).getName());
        holder.location.setOnClickListener(v -> {
            Uri uri = Uri.parse("geo:" + favHotelsData.get(position).getCoordinate());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            holder.itemView.getContext().startActivity(intent);
        });

        if (Double.parseDouble(favHotelsData.get(position).getRating()) > 4d) {
            holder.condition.setText("Very Good");
            for (int i = 1; i < 5; i++)
                setStarImage(holder, holder.stars[i - 1], R.drawable.ic_star_black_24dp);
            setStarImage(holder, holder.stars[4], R.drawable.ic_star_grey_24dp);
        } else if (Double.parseDouble(favHotelsData.get(position).getRating()) > 3d) {
            holder.condition.setText("Good");
            for (int i = 1; i < 4; i++)
                setStarImage(holder, holder.stars[i - 1], R.drawable.ic_star_black_24dp);
            setStarImage(holder, holder.stars[3], R.drawable.ic_star_grey_24dp);
            setStarImage(holder, holder.stars[4], R.drawable.ic_star_grey_24dp);
        }

        holder.favIcon.setOnClickListener(v -> {
            if (favHotelsData.get(position).getIsFavorite() == 1) {
                Glide.with(holder.itemView).load(R.drawable.ic_favorite_black_24dp).into(holder.favIcon);
                viewModel.updateFavourite(region, favHotelsData.get(position).getName(), 0);
                favHotelsData.get(position).setIsFavorite(0);
                notifyItemChanged(position);
            } else {
                Glide.with(holder.itemView).load(R.drawable.ic_favorite_red_24dp).into(holder.favIcon);
                viewModel.updateFavourite(region, favHotelsData.get(position).getName(), 1);
                favHotelsData.get(position).setIsFavorite(1);
                notifyItemChanged(position);
            }

        });

        holder.itemView.setOnClickListener(v -> {
            viewModel.setSelectHotel(region, favHotelsData.get(position).getName());
            changeListener.changeContainerListener();
        });

    }

    @Override
    public int getItemCount() {
        return favHotelsData.size();
    }

    private void setStarImage(FavHotelsListAdapter.ViewHolder holder, ImageView v, int d) {
        Glide.with(holder.itemView).load(d).into((v));
    }

    public interface ChangeContainer {
        void changeContainerListener();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hImage)
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

        public ViewHolder(View itemView) {
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
