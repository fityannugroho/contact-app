package com.fityan.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS Contacts(`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL, `phone` TEXT NOT NULL, `email` TEXT, `address` TEXT)";
        sqLiteDatabase.execSQL(sql);
        System.out.println("Table created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    public Cursor getContacts() {
        SQLiteDatabase dbWrite = this.getReadableDatabase();
        return dbWrite.rawQuery("SELECT * FROM Contacts", null);
    }


    public boolean insertContact(String name, String phone, String email, String address) {
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("phone", phone);
        values.put("email", email);
        values.put("address", address);

        long result = dbWrite.insert("Contacts", null, values);
        return result != -1;
    }


    public boolean updateContact(int id, String name, String phone, String email, String address) {
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("phone", phone);
        values.put("email", email);
        values.put("address", address);

        int result = dbWrite.update("Contacts", values,
                "id=?", new String[] {String.valueOf(id)});
        return result != -1;
    }


    public boolean deleteContact(int id) {
        SQLiteDatabase dbWrite = this.getWritableDatabase();

        int result = dbWrite.delete("Contacts",
                "id=?", new String[] {String.valueOf(id)});
        return result != -1;
    }
}
