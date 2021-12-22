package com.fityan.contactapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fityan.contactapp.R;
import com.fityan.contactapp.helpers.ContactsCollection;
import com.fityan.contactapp.models.Contact;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ContactEditActivity extends AppCompatActivity {
    /**
     * Helper to interact with database.
     */
    private final ContactsCollection contactsCollection = new ContactsCollection();

    /**
     * The contact to be changed.
     */
    private final Contact contact = new Contact();

    /* View elements. */
    private Button btnBack, btnEdit;
    private TextInputEditText inputName, inputPhone, inputEmail, inputAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        /* Initialize view elements. */
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        inputAddress = findViewById(R.id.inputAddress);
        inputEmail = findViewById(R.id.inputEmail);
        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);

        /* Get the contact to be changed. */
        contact.setId(getIntent().getStringExtra("id"));

        /* Get the contact. */
        contactsCollection.findOne(contact.getId()).addOnSuccessListener(documentSnapshot -> {
            /* Display the contact data. */
            inputName.setText(documentSnapshot.getString("name"));
            inputPhone.setText(documentSnapshot.getString("phone"));
            inputEmail.setText(documentSnapshot.getString("email"));
            inputAddress.setText(documentSnapshot.getString("address"));
        });

        /* When Edit Button is clicked, */
        btnEdit.setOnClickListener(view -> {
            /* Validate input then execute the query */
            try {
                /* Retreive new data from input. */
                contact.setName(getTextFromInput(inputName, true));
                contact.setPhone(getTextFromInput(inputPhone, true));
                contact.setEmail(getTextFromInput(inputEmail, false));
                contact.setAddress(getTextFromInput(inputAddress, false));

                /* Execute update query, then give a feedback. */
                contactsCollection.update(contact)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Contact successfully updated", Toast.LENGTH_SHORT)
                                .show();
                            finish();    /* Finish the activity and back to previous activity automatically. */
                        }
                    })
                    .addOnFailureListener(
                        e -> Toast.makeText(this, "Failed to update contact", Toast.LENGTH_SHORT)
                            .show());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });

        /* When Back Button is clicked, back to previous activity. */
        btnBack.setOnClickListener(view -> onBackPressed());
    }


    /**
     * Retreive data from input, and validate the requirement.
     *
     * @param input    The input element.
     * @param required Is value required?
     * @return The input value.
     * @throws NullPointerException If validation failed.
     */
    private String getTextFromInput(TextInputEditText input, boolean required) {
        String value = Objects.requireNonNull(input.getText()).toString();

        if (value.isEmpty() && required) {
            input.setError("This input is required");
            throw new NullPointerException("Field " + input.getHint() + " is required.");
        }

        return value;
    }
}
