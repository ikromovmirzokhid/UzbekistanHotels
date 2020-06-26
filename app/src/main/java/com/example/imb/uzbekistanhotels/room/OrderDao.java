package com.example.imb.uzbekistanhotels.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface OrderDao {
    
    @Insert
    long addNewOrder(Order order);

    @Delete
    int deleteOrder(Order order);

    @Query("Select * from `orders`")
    LiveData<List<Order>> getAllOrders();

    @Query("Select * from orders where id=:id")
    Order getOrder(long id);

    @Update
    int updateOrder(Order order);
}
