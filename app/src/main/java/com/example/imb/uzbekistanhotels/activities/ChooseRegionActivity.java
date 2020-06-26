package com.example.imb.uzbekistanhotels.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.imb.uzbekistanhotels.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseRegionActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.region1)
    Button tashkent;
    @BindView(R.id.region2)
    Button bukhara;
    @BindView(R.id.region3)
    Button samarkand;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_region);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        sPref = getSharedPreferences("region", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        tashkent.setOnClickListener(v -> {
            editor.putString("region", "tashkentHotels");
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        bukhara.setOnClickListener(v -> {
            editor.putString("region", "bukharaHotels");
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        samarkand.setOnClickListener(v -> {
            editor.putString("region", "samarkandHotels");
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
