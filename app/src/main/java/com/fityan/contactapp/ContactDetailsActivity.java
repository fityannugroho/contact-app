package com.fityan.contactapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailsActivity extends AppCompatActivity {
    /**
     * Helper to interact with database.
     */
    private final DBHelper dbHelper = new DBHelper(this);

    /* View elements. */
    private TextView tvName, tvPhone, tvEmail, tvAddress;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        btnBack = findViewById(R.id.btnBack);

        /* Get the contact. */
        Contact contact = dbHelper.getContact(
                getIntent().getIntExtra("id", 0));

        /* Display the contact data. */
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());
        tvEmail.setText(contact.getEmail());
        tvAddress.setText(contact.getAddress());

        /* When Back Button is clicked, back to previous activity. */
        btnBack.setOnClickListener(view -> onBackPressed());
    }
}
