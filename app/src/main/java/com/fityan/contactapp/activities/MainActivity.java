package com.fityan.contactapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fityan.contactapp.adapters.ContactAdapter;
import com.fityan.contactapp.helpers.DBHelper;
import com.fityan.contactapp.R;
import com.fityan.contactapp.models.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ContactAdapter.OnItemListener {
    /**
     * List of contact.
     */
    private final ArrayList<Contact> contacts = new ArrayList<>();

    /**
     * Helper to interact with database.
     */
    private final DBHelper dbHelper = new DBHelper(this);

    /**
     * View element to displaying contact list.
     */
    private RecyclerView rvContact;

    /**
     * View element to navigate to add contact page.
     */
    private FloatingActionButton btnAddContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize view elements. */
        rvContact = findViewById(R.id.rvContact);

        /* Retreive contacts from database then displaying it. */
        loadContactsFromDB();

        btnAddContact = findViewById(R.id.btnAdd);

        /* When Add Button is clicked, */
        btnAddContact.setOnClickListener(view -> {
            /* go to add contact page. */
            startActivity(new Intent(getApplicationContext(), ContactNewActivity.class));
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        removeContacts();
        loadContactsFromDB();
    }


    @Override
    public void onItemClick(int position) {
        /* Go to contact details page with bring contact id. */
        Intent intent = new Intent(getApplicationContext(), ContactDetailsActivity.class);
        intent.putExtra("id", contacts.get(position).getId());
        startActivity(intent);
    }


    @Override
    public void onDeleteItem(int position) {
        /* Show confirmation dialog. */
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete confirmation")
                .setMessage("Are you sure to delete this contact?")
                .setPositiveButton("Delete Contact", null)
                .setNegativeButton("Cancel", null)
                .show();

        /* Execute deletion after confirmation. */
        Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveBtn.setOnClickListener(view -> {
            /* Delete contact */
            if (dbHelper.deleteContact(contacts.get(position).getId())) {
                /* Refresh the contact list view */
                onRestart();
                Toast.makeText(getApplicationContext(), "One contact has been deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to delete contact", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });
    }


    @Override
    public void onEditItem(int position) {
        /* Go to contact edit page with bring contact id. */
        Intent intent = new Intent(getApplicationContext(), ContactEditActivity.class);
        intent.putExtra("id", contacts.get(position).getId());
        startActivity(intent);
    }


    /**
     * Load contact list with contact data from database then displaying it.
     */
    private void loadContactsFromDB() {
        /* Retreive contact data from database. */
        Cursor cursor = dbHelper.getContacts();
        cursor.moveToFirst();

        /* Add contact data to contact list */
        try {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String email = cursor.getString(3);
                String address = cursor.getString(4);

                /* Add to contact list. */
                contacts.add(new Contact(id, name, phone, email, address));
            }
        } finally {
            cursor.close();
        }

        /* Set the adapter to displaying contact list. */
        rvContact.setAdapter(new ContactAdapter(contacts, this));
        rvContact.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvContact.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * Clear all contact in the list.
     */
    private void removeContacts() {
        contacts.clear();
    }
}
