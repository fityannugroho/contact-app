package com.fityan.contactapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ContactNewActivity extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(this);

    Button btnBack, btnAdd;
    TextInputEditText inputName, inputPhone, inputEmail, inputAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_new);

        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        inputAddress = findViewById(R.id.inputAddress);
        inputEmail = findViewById(R.id.inputEmail);
        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);

        btnAdd.setOnClickListener(view -> {
            try {
                String name, phone, email, address;

                name = getTextFromInput(inputName, true);
                phone = getTextFromInput(inputPhone, true);
                email = getTextFromInput(inputEmail, false);
                address = getTextFromInput(inputAddress, false);

                if (dbHelper.insertContact(name, phone, email, address)) {
                    Toast.makeText(this,
                            "Contact added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this,
                            "Failed to add contact", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });

        btnBack.setOnClickListener(view -> onBackPressed());
    }


    private String getTextFromInput(TextInputEditText input, boolean required) {
        String value = Objects.requireNonNull(input.getText()).toString();

        if (value.isEmpty() && required) {
            input.setError("This input is required");
            throw new NullPointerException("Field " + input.getHint() + " is required.");
        }

        return value;
    }
}