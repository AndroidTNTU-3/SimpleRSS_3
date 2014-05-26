package com.example.simplerss;

import android.database.Cursor;

public final class Utils {
	
    public static String getStringColumn(Cursor cursor, String column) {
        try {
            int index = cursor.getColumnIndex(column);
            return cursor.getString(index);
        } catch (Exception e) {
            return "";
        }
    }

}
