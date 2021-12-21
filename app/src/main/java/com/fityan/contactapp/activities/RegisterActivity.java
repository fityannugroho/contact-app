package com.fityan.contactapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fityan.contactapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    /* View elements. */
    private TextInputEditText inputEmail, inputPassword, inputCPassword;
    private Button btnRegister, btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* Initialize view elements. */
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputCPassword = findViewById(R.id.inputCPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        /* Event handler when Register Button is clicked. */
        btnRegister.setOnClickListener(view -> {
        });

        /* Event handler when Login Button is clicked. */
        btnLogin.setOnClickListener(view -> {
            /* Go to register activity. */
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
