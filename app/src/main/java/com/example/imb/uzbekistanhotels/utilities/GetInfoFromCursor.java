package com.example.imb.uzbekistanhotels.utilities;

import android.database.Cursor;

public class GetInfoFromCursor {
    private Cursor cursor;

    public GetInfoFromCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public int getInt(String index) {
        return cursor.getInt(cursor.getColumnIndex(index));
    }

    public String getString(String index) {
        return cursor.getString(cursor.getColumnIndex(index));
    }

    public byte[] getPhoto(String index) {
        return cursor.getBlob(cursor.getColumnIndex(index));
    }
}
