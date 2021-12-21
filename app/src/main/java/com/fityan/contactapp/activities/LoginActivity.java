package com.fityan.contactapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fityan.contactapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    /**
     * Firebase authentication.
     */
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

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
            /* Input validation */
            try {
                String email = getTextFromInput(inputEmail, true);
                String password = getTextFromInput(inputPassword, true);

                /* start authentication. */
                auth.signInWithEmailAndPassword(email, password)
                    /* If sign in success, reload this activity. */
                    .addOnSuccessListener(authResult -> {
                        if (authResult.getUser() != null) goToMainActivity();
                        else onRestart();
                    })
                    /* If failed, show alert message */
                    .addOnFailureListener(e -> Toast.makeText(this,
                        "Login failed! Wrong Email or Password.",
                        Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                Log.w("InvalidInput", "Input is invalid", e);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        /* Check if user is authorized (non-null). */
        if (auth.getCurrentUser() != null) {
            goToMainActivity();
        }
    }


    /**
     * Retreive data from input, and validate the requirement.
     *
     * @param input    The input element.
     * @param required Is value required?
     * @return The input value.
     * @throws NullPointerException If validation failed.
     */
    private String getTextFromInput(EditText input, boolean required) {
        String value = input.getText().toString();

        if (value.isEmpty() && required) {
            input.setError("This input is required");
            throw new NullPointerException(
                "Field " + input.getHint() + " is required.");
        }

        return value;
    }


    /**
     * Go to main activity.
     */
    private void goToMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();    /* Stop current activity. */
    }
}
