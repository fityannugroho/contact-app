package com.fityan.contactapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fityan.contactapp.R;
import com.fityan.contactapp.helpers.ContactsCollection;

public class ContactDetailsActivity extends AppCompatActivity {
    /**
     * Helper to interact with database.
     */
    private final ContactsCollection contactsCollection = new ContactsCollection();

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

        String contactId = getIntent().getStringExtra("id");

        /* Get the contact. */
        contactsCollection.findOne(contactId)
            .addOnSuccessListener(documentSnapshot -> {
                /* Display the contact data. */
                tvName.setText(documentSnapshot.getString("name"));
                tvPhone.setText(documentSnapshot.getString("phone"));
                tvEmail.setText(documentSnapshot.getString("email"));
                tvAddress.setText(documentSnapshot.getString("address"));
            });

        /* When Back Button is clicked, back to previous activity. */
        btnBack.setOnClickListener(view -> onBackPressed());
    }
}
