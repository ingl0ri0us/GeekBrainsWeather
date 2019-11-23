package com.example.geekbrainsweather.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CityWithCurrentTempTable {
    private final static String TABLE_NAME = "CityWithCurrentTempTable";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_CITY_NAME = "city_name";
    private final static String COLUMN_THUMBNAIL_ID = "thumbnail_id";
    private final static String COLUMN_TEMPERATURE = "temperature";
    private final static String COLUMN_TIME_UPDATED = "time_updated";

    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CITY_NAME + " TEXT," +
                COLUMN_THUMBNAIL_ID + " INTEGER," +
                COLUMN_TEMPERATURE + " INTEGER," +
                COLUMN_TIME_UPDATED + " INTEGER" +
                ");");
    }

    public static void addCityWithCurrentTempAndTimeToDataBase(
            String cityName,
            int thumbnailId,
            int temperature,
            long timeUpdated,
            SQLiteDatabase database) {
        if (dataBaseContainsCity(cityName, database)) {
            String[] cityNameToUpdate = new String[]{cityName};
            ContentValues values = new ContentValues();
            values.put(COLUMN_THUMBNAIL_ID, thumbnailId);
            values.put(COLUMN_TEMPERATURE, temperature);
            values.put(COLUMN_TIME_UPDATED, timeUpdated);

            database.update(TABLE_NAME, values, COLUMN_CITY_NAME + " = ?", cityNameToUpdate);
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CITY_NAME, cityName);
            values.put(COLUMN_THUMBNAIL_ID, thumbnailId);
            values.put(COLUMN_TEMPERATURE, temperature);
            values.put(COLUMN_TIME_UPDATED, timeUpdated);

            database.insert(TABLE_NAME, null, values);
        }
    }

    private static boolean dataBaseContainsCity(String cityName, SQLiteDatabase database) {
        String[] cityNameToCheck = new String[]{cityName};
        Cursor cursor = database.rawQuery(
                "SELECT " + COLUMN_CITY_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_CITY_NAME + " = ?",
                cityNameToCheck);
        if (cursor != null && cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    public static void deleteAll(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
    }
}
