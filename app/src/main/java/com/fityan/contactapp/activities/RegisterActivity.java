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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    /**
     * Firebase authentication.
     */
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    /* View elements. */
    private EditText inputEmail, inputPassword, inputCPassword;
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
            /* Validate inputs, */
            try {
                String email = getTextFromInput(inputEmail, true);
                String password = getTextFromInput(inputPassword, true);
                String cPassword = getTextFromInput(inputCPassword, true);

                if (!password.equals(cPassword)) {
                    inputCPassword.setError("Confirm password doesn't match.");
                    throw new Exception("Confirm password doesn't match.");
                }

                /* Start registration. */
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        /* If success. */
                        FirebaseUser user = authResult.getUser();

                        if (user != null) {
                            /* Send email verification. */
                            user.sendEmailVerification().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    /* Go to verify email. */
                                    startActivity(new Intent(this, VerifyEmailActivity.class));
                                    finish();
                                } else {
                                    Log.e("sendEmailVerif", "Email verification failed",
                                        task.getException());
                                    Toast.makeText(this, "Failed to send email verification",
                                        Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(
                        /* If failed. */
                        e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                Log.w("InvalidInput", "Some input is invalid.");
            }
        });

        /* Event handler when Login Button is clicked. */
        btnLogin.setOnClickListener(view -> {
            /* Go to register activity. */
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
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
            throw new NullPointerException("Field " + input.getHint() + " is required.");
        }

        return value;
    }
}
