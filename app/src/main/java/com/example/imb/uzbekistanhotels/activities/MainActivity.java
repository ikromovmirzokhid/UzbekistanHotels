package com.example.imb.uzbekistanhotels.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.databases.Database;
import com.example.imb.uzbekistanhotels.fragments.FavouritesViewFragment;
import com.example.imb.uzbekistanhotels.fragments.MainViewFragment;
import com.example.imb.uzbekistanhotels.fragments.OrdersViewFragment;
import com.example.imb.uzbekistanhotels.room.OrderDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bNView)
    BottomNavigationView nView;
    private MainViewFragment mainViewFragment;
    private FavouritesViewFragment favouritesViewFragment;
    private OrdersViewFragment ordersViewFragment;
    private android.support.v4.app.FragmentTransaction transaction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change) {
            Intent intent = new Intent(this, ChooseRegionActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Database.init(this);
        OrderDatabase.init(this);

        SharedPreferences sPref = getSharedPreferences("region", MODE_PRIVATE);
        String region = sPref.getString("region", "");
        if (region.equals("")) {
            Intent intent = new Intent(this, ChooseRegionActivity.class);
            startActivity(intent);
        } else {
            transaction = getSupportFragmentManager().beginTransaction();
            mainViewFragment = MainViewFragment.getInstance(region);
            transaction.replace(R.id.container, mainViewFragment);
            favouritesViewFragment = FavouritesViewFragment.getInstance(region);
            ordersViewFragment = new OrdersViewFragment();
            nView.getMenu().getItem(0).setChecked(true);
            transaction.commit();
        }

        nView.setOnNavigationItemSelectedListener(item -> {
            transaction = getSupportFragmentManager().beginTransaction();
            if (item.getItemId() == R.id.search)
                transaction.replace(R.id.container, mainViewFragment);
            else if (item.getItemId() == R.id.favourites)
                transaction.replace(R.id.container, favouritesViewFragment);
            else if (item.getItemId() == R.id.ordered)
                transaction.replace(R.id.container, ordersViewFragment);
            transaction.commit();
            return true;
        });


    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
