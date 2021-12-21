package com.fityan.contactapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fityan.contactapp.R;

public class LoginActivity extends AppCompatActivity {
    /* View Elements */
    private EditText inputEmail, inputPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Get View Elements by its id */
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        /* Event handler when Login Button is clicked */
        btnLogin.setOnClickListener(view -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            /* Input validation */
            if (validateInputs()) {
                /* Login auth */
                if (this.auth(email, password)) {
                    /* Go to home page */
                    Intent homeIntent = new Intent(LoginActivity.this,
                        MainActivity.class);

                    homeIntent.putExtra("email",
                        email);    /* Bring email value */
                    startActivity(homeIntent);    /* Move to home page */
                    finish();    /* Stop the login activity */
                } else {
                    /* Show alert message */
                    String message = "Login failed! Email or Password is wrong.";
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * Validate the inputs.
     *
     * @return true if all field input is valid.
     */
    private Boolean validateInputs() {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

        if (!inputEmail.getText().toString().matches(emailRegex)) {
            inputEmail.setError("Invalid email format!");
            return false;
        }

        if (inputPassword.getText().toString().isEmpty()) {
            inputPassword.setError("Password is required!");
            return false;
        }

        return true;
    }


    /**
     * Check the user credential.
     *
     * @param email    The user email
     * @param password The user password
     * @return true if the credentials is found, false otherwise.
     */
    private Boolean auth(String email, String password) {
        /* TODO: Needs verify to databases */
        return !email.isEmpty() && password.equals("12345");
    }
}