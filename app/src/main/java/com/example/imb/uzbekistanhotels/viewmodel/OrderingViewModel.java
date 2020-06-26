package com.example.imb.uzbekistanhotels.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.imb.uzbekistanhotels.activities.OrderingActivity;
import com.example.imb.uzbekistanhotels.room.Order;
import com.example.imb.uzbekistanhotels.room.OrderDatabase;

import java.util.List;

public class OrderingViewModel extends ViewModel {
    private OrderDatabase ordersDb = OrderDatabase.getDb();
    private static ViewModelListener listener;
    public int aNum = 0, cNum = 0, rNum = 1;
    public MutableLiveData<String> adultNum = new MutableLiveData<>();
    public MutableLiveData<String> childNum = new MutableLiveData<>();
    public MutableLiveData<String> roomNum = new MutableLiveData<>();
    public MutableLiveData<String> inDate = new MutableLiveData<>();
    public MutableLiveData<String> outDate = new MutableLiveData<>();
    public MutableLiveData<String> totalCost = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();

    public MutableLiveData<String> nameTextContent = new MutableLiveData<>();
    public MutableLiveData<String> emailTextContent = new MutableLiveData<>();
    public MutableLiveData<String> phoneTextContent = new MutableLiveData<>();

    public MutableLiveData<String> nameErrorMessage = new MutableLiveData<>();
    public MutableLiveData<String> emailErrorMessage = new MutableLiveData<>();
    public MutableLiveData<String> phoneErrorMessage = new MutableLiveData<>();

    public MutableLiveData<String> typeOfroom = new MutableLiveData<>();


    public static void setListener(ViewModelListener l) {
        listener = l;
    }

    public void setTypeOfRoom(String type) {
        typeOfroom.setValue(type);
    }

    public void setName(String n) {
        name.setValue(n);
    }

    public void setInDate(String date) {
        inDate.setValue(date);
    }

    public void setOutDate(String date) {
        outDate.setValue(date);
    }

    public void increaseNumOfAdults() {
        aNum++;
        adultNum.setValue(String.valueOf(aNum));
        OrderingActivity.price += OrderingActivity.hotel.getCheapRoom();
        setTotalCost(OrderingActivity.price);
    }

    public void increaseNumOfChildren() {
        cNum++;
        childNum.setValue(String.valueOf(cNum));
        OrderingActivity.price += OrderingActivity.hotel.getCheapRoom();
        setTotalCost(OrderingActivity.price);
    }

    public void increaseNumOfRooms() {
        rNum++;
        roomNum.setValue(String.valueOf(rNum));
        OrderingActivity.price += 50;
        setTotalCost(OrderingActivity.price);
    }

    public void decreaseNumOfAdults() {
        if (aNum > 0) {
            aNum--;
            adultNum.setValue(String.valueOf(aNum));
            OrderingActivity.price -= OrderingActivity.hotel.getCheapRoom();
            setTotalCost(OrderingActivity.price);
        }
    }

    public void decreaseNumOfChildren() {
        if (cNum > 0) {
            cNum--;
            childNum.setValue(String.valueOf(cNum));
            OrderingActivity.price -= OrderingActivity.hotel.getCheapRoom();
            setTotalCost(OrderingActivity.price);
        }
    }

    public void decreaseNumOfRooms() {
        if (rNum > 1) {
            rNum--;
            roomNum.setValue(String.valueOf(rNum));
            OrderingActivity.price -= 50;
            setTotalCost(OrderingActivity.price);
        }
    }

    public void setTotalCost(int cost) {
        totalCost.setValue("order now - $" + cost);
    }

    public void typeBtnPressed() {
        listener.typeBtnPressedListener();
    }

    public void orderBtnPressed() {
        if (nameTextContent.getValue() == null)
            nameErrorMessage.setValue("Name is Required!");
        if (emailTextContent.getValue() == null)
            emailErrorMessage.setValue("Email is Reruired!");
        if (phoneTextContent.getValue() == null)
            phoneErrorMessage.setValue("Phone is Required!");
        if (nameTextContent.getValue() != null && emailTextContent.getValue() != null && phoneTextContent.getValue() != null &&
                aNum > 0 && !inDate.getValue().equals("date") && !outDate.getValue().equals("date"))
            listener.orderBtnPressedListener();
    }

    public void inDatePressed() {
        listener.inDatePressed();
    }

    public void outDatePressed() {
        listener.outDatePressed();
    }

    public interface ViewModelListener {
        void typeBtnPressedListener();

        void orderBtnPressedListener();

        void inDatePressed();

        void outDatePressed();
    }

    public LiveData<List<Order>> getOrders() {
        return ordersDb.orderDaoAccess().getAllOrders();
    }

}
