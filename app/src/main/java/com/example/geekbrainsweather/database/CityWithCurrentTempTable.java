package com.example.geekbrainsweather.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.geekbrainsweather.CityWithCurrentTemperatureItem;

import java.util.ArrayList;
import java.util.List;

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

    public static CityWithCurrentTemperatureItem getItemFromDataBase(String cityName, SQLiteDatabase database) {
        String[] cityNameToLoad = new String[]{cityName};
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CITY_NAME + " = ?", cityNameToLoad);
        if(cursor != null && cursor.moveToFirst()) {
            int thumbnailId = cursor.getInt(cursor.getColumnIndex(COLUMN_THUMBNAIL_ID));
            int temperature = cursor.getInt(cursor.getColumnIndex(COLUMN_TEMPERATURE));
            long timeUpdated = cursor.getLong(cursor.getColumnIndex(COLUMN_TIME_UPDATED));
            cursor.close();
            return new CityWithCurrentTemperatureItem(thumbnailId,cityName,temperature,timeUpdated);
        } else {
            return null;
        }
    }

    static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_TEMPERATURE +
                " TEXT DEFAULT 'Defautl title'");
    }

    static void addNote(int note, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_NAME, note);

        database.insert(TABLE_NAME, null, values);
    }

    static void editNote(int noteToEdit, int newNote, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_NAME, newNote);

        database.execSQL("UPDATE " + TABLE_NAME + " set " + COLUMN_CITY_NAME + " = " + newNote + " WHERE " +
                COLUMN_CITY_NAME + " = " + noteToEdit + ";");
    }

    static void deleteNote(int note, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_CITY_NAME + " = " + note, null);
    }

    public static void deleteAll(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
    }

    static List<Integer> getAllNotes(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null, null, null,
                null, null, null);
        return getResultsFromCursor(cursor);
    }

    private static List<Integer> getResultsFromCursor(Cursor cursor) {
        List<Integer> result = null;

        if (cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());

            int noteIdx = cursor.getColumnIndex(COLUMN_CITY_NAME);
            do {
                result.add(cursor.getInt(noteIdx));
            } while (cursor.moveToNext());
        }
        try {
            cursor.close();
        } catch (Exception ignored) {

        }
        return result == null ? new ArrayList<Integer>(0) : result;
    }
}
