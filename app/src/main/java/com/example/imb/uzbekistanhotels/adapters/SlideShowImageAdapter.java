package com.example.imb.uzbekistanhotels.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.imb.uzbekistanhotels.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideShowImageAdapter extends PagerAdapter {
    private List<byte[]> images;
    private Context ctx;
    @BindView(R.id.image)
    ImageView img;

    public SlideShowImageAdapter(List<byte[]> images, Context context) {
        this.images = images;
        ctx = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpager_image, container, false);
        ButterKnife.bind(this, v);
        Glide.with(ctx).load(images.get(position)).into(img);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.refreshDrawableState();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
