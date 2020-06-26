package com.example.imb.uzbekistanhotels.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.async.ClientSide;
import com.example.imb.uzbekistanhotels.databinding.ActivityOrderingBinding;
import com.example.imb.uzbekistanhotels.dialogs.DateDialog;
import com.example.imb.uzbekistanhotels.models.HotelFullInfo;
import com.example.imb.uzbekistanhotels.room.Order;
import com.example.imb.uzbekistanhotels.room.OrderDatabase;
import com.example.imb.uzbekistanhotels.viewmodel.HotelViewModel;
import com.example.imb.uzbekistanhotels.viewmodel.OrderingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderingActivity extends AppCompatActivity implements OrderingViewModel.ViewModelListener, DateDialog.OnDateSetListener {
    private OrderingViewModel viewModel;
    private HotelViewModel hotelViewModel;
    public static HotelFullInfo hotel;
    @BindView(R.id.type)
    TextView type;
    public static int price = 0;
    private DateDialog dialog, dialog1;
    private int inDay = 0, outDay = 0;
    private int inMonth = 0, outMonth = 0, totalDays;
    private ActivityOrderingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        ButterKnife.bind(this);

        price = 0;

        Intent intent = getIntent();
        String region = intent.getStringExtra("region");
        String name = intent.getStringExtra("name");

        OrderingViewModel.setListener(this);

        hotelViewModel = ViewModelProviders.of(this).get(HotelViewModel.class);
        hotel = hotelViewModel.getHotelInfo(name, region);

        viewModel = ViewModelProviders.of(this).get(OrderingViewModel.class);

        viewModel.adultNum.setValue("" + 0);
        viewModel.childNum.setValue("" + 0);
        viewModel.roomNum.setValue("" + 1);
        viewModel.inDate.setValue("date");
        viewModel.outDate.setValue("date");
        viewModel.totalCost.setValue("order now - $0");
        viewModel.typeOfroom.setValue("Type");
        viewModel.setName(hotel.getName());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ordering);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);

        dialog = new DateDialog();
        dialog1 = new DateDialog();
        dialog.setOnDateSetListener(this);
        dialog1.setOnDateSetListener((year, month, day) -> {
            outDay = day;
            outMonth = month;
            viewModel.setOutDate(year + "/" + (month + 1) + "/" + day);
            if (inDay != 0) {
                if (inMonth == outMonth)
                    totalDays = outDay - inDay + 1;
                viewModel.setTotalCost(price *= totalDays);
            }
        });

    }

    @Override
    public void typeBtnPressedListener() {
        PopupMenu menu = new PopupMenu(this, binding.type);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.type_of_rooms, menu.getMenu());
        menu.show();
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.cheap) {
                price = hotel.getCheapRoom();
                calculateTotalCost(price);
                viewModel.setTypeOfRoom("Cheapest");
            } else if (item.getItemId() == R.id.medium) {
                price = hotel.getMediumRoom();
                calculateTotalCost(price);
                viewModel.setTypeOfRoom("Medium");
            } else if (item.getItemId() == R.id.lux) {
                price = hotel.getLuxRoom();
                calculateTotalCost(price);
                viewModel.setTypeOfRoom("Luxurious");
            } else price = 0;
            return true;
        });
    }

    @Override
    public void orderBtnPressedListener() {
        Order order = new Order(hotel.getName(), viewModel.aNum, viewModel.cNum, viewModel.rNum, viewModel.typeOfroom.getValue(), viewModel.inDate.getValue(),
                viewModel.outDate.getValue(), viewModel.nameTextContent.getValue(),
                viewModel.emailTextContent.getValue(), viewModel.phoneTextContent.getValue(), "" + price, hotel.getMainPhoto());
        OrderDatabase db = OrderDatabase.getDb();
        long orderId = db.orderDaoAccess().addNewOrder(order);
        //Log.d("OrderId: ", "" + orderId);
        Snackbar snackbar = Snackbar.make(getCurrentFocus(), "Ordered Successfully!", Snackbar.LENGTH_SHORT);
        snackbar.show();
        Handler mHandler = new Handler();
        mHandler.postDelayed(this::finish, 2000);
        ClientSide client = new ClientSide();
        client.execute(hotel.getName() + "," + viewModel.aNum + "," + viewModel.cNum + "," + viewModel.rNum + "," +
                viewModel.typeOfroom.getValue() + "," + viewModel.inDate.getValue() + "," + viewModel.outDate.getValue() + "," + viewModel.nameTextContent.getValue()
                + "," + viewModel.emailTextContent.getValue() + "," + viewModel.phoneTextContent.getValue() +
                "," + price + "," + orderId);
    }

    @Override
    public void inDatePressed() {
        dialog.show(getSupportFragmentManager(), "inDatePicker");
    }

    @Override
    public void outDatePressed() {
        dialog1.show(getSupportFragmentManager(), "outDatePicker");
    }

    private void calculateTotalCost(int price) {
        int aNum = Integer.parseInt(viewModel.adultNum.getValue());
        int cNum = Integer.parseInt(viewModel.childNum.getValue());
        int rNum = Integer.parseInt(viewModel.roomNum.getValue());
        for (int i = 0; i < aNum; i++) {
            price += price;
        }
        for (int i = 0; i < cNum; i++) {
            price += price;
        }
        for (int i = 0; i < rNum; i++) {
            price += 50;
        }
        viewModel.setTotalCost(price);
    }

    @Override
    public void onChangeInView(int year, int month, int day) {
        inDay = day;
        inMonth = month;
        viewModel.setInDate(year + "/" + (month + 1) + "/" + day);
    }

}
