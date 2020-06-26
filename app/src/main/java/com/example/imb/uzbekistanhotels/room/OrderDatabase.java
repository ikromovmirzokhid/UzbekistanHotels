package com.example.imb.uzbekistanhotels.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Order.class}, version = 1)
public abstract class OrderDatabase extends RoomDatabase {

    private static OrderDatabase db;

    public static void init(Context context) {
        if (db == null)
            db = Room.databaseBuilder(context, OrderDatabase.class, "orders").allowMainThreadQueries().build();
    }

    public static OrderDatabase getDb() {
        return db;
    }

    public abstract OrderDao orderDaoAccess();
}
