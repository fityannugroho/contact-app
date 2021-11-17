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


    /**
     * Get a contact with specific id.
     * @param id The contact id.
     * @return A contact with specific id.
     */
    public Contact getContact(int id) {
        String name = "", phone = "", email = "", address = "";
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String query = "SELECT * FROM Contacts WHERE `id`=" + id;

        try (Cursor cursor = dbRead.rawQuery(query, null)) {
            cursor.moveToFirst();

            do {
                if (cursor.getCount() == 1) {
                    name = cursor.getString(1);
                    phone = cursor.getString(2);
                    email = cursor.getString(3);
                    address = cursor.getString(4);
                }
            } while (cursor.moveToNext());
        }

        return new Contact(id, name, phone, email, address);
    }


    /**
     * Get all contacts.
     * @return The contacts in the table.
     */
    public Cursor getContacts() {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        return dbRead.rawQuery("SELECT * FROM Contacts", null);
    }


    /**
     * Insert a contact to the table.
     * @param name The contact name.
     * @param phone The contact phone number.
     * @param email The contact email.
     * @param address The contact address.
     * @return True if success, or otherwise.
     */
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


    /**
     * Update a contact data with specific id.
     * @param id The contact id.
     * @param name The new contact name.
     * @param phone The new contact phone number.
     * @param email The new contact email.
     * @param address The new contact address.
     * @return True if success, or otherwise.
     */
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


    /**
     * Delete a contact with specific id.
     * @param id The contact id.
     * @return True if success, or otherwise.
     */
    public boolean deleteContact(int id) {
        SQLiteDatabase dbWrite = this.getWritableDatabase();

        int result = dbWrite.delete("Contacts",
                "id=?", new String[] {String.valueOf(id)});
        return result != -1;
    }
}
