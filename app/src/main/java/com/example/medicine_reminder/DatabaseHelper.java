package com.example.medicine_reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "medicine.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT)");

        db.execSQL("CREATE TABLE medicines (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "name TEXT," +
                "dosage TEXT," +
                "time TEXT)");

        String CREATE_TABLE = "CREATE TABLE medicines (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "name TEXT, " +
                "dosage TEXT, " +
                "description TEXT, " +
                "reminder_time TEXT)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS medicines");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS medicines");
        onCreate(db);
    }

    public long addMedicine(int userId, String name, String dosage, String desc){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("user_id", userId);
        values.put("name", name);
        values.put("dosage", dosage);
        values.put("description", desc);

        return db.insert("medicines", null, values);
    }

    public void saveReminderTime(int medId, int hour, int minute){

        SQLiteDatabase db = this.getWritableDatabase();

        String time = hour + ":" + minute;

        ContentValues values = new ContentValues();
        values.put("reminder_time", time);

        db.update("medicines", values, "id=?",
                new String[]{String.valueOf(medId)});
    }

    public String getMedicineName(int medId){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT name FROM medicines WHERE id=?",
                new String[]{String.valueOf(medId)}
        );

        if(cursor.moveToFirst()){
            String name = cursor.getString(0);
            cursor.close();
            return name;
        }

        cursor.close();
        return "";
    }

}