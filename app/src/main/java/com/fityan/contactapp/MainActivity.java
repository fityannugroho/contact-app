package com.fityan.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private final ArrayList<Contact> contacts = new ArrayList<>();
    private RecyclerView rvContact;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvContact = findViewById(R.id.rvContact);
        dbHelper = new DBHelper(getApplicationContext());
        FloatingActionButton btnAddContact = findViewById(R.id.btnAdd);

        getContactsFromDB();
        setAdapter();

        btnAddContact.setOnClickListener(view -> {
            /* Go to add new contact page */
            startActivity(new Intent(getApplicationContext(), ContactNewActivity.class));
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        removeContacts();
        getContactsFromDB();
        setAdapter();
    }


    private void setAdapter() {
        rvContact.setAdapter(new ContactAdapter(contacts));
        rvContact.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvContact.setItemAnimator(new DefaultItemAnimator());
    }


    private void getContactsFromDB() {
        Cursor cursor = dbHelper.getContacts();
        cursor.moveToFirst();

        /* Inserting data contact to arraylist */
        try {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String email = cursor.getString(3);
                String address = cursor.getString(4);

                contacts.add(new Contact(id, name, phone, email, address));
            }
        } finally {
            cursor.close();
        }
    }


    private void removeContacts() {
        contacts.clear();
    }
}