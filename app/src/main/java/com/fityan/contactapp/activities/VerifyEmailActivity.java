package com.fityan.contactapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fityan.contactapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailActivity extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button btnVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(view -> {
            FirebaseUser user = auth.getCurrentUser();

            try {
                if (user == null)
                    throw new FirebaseAuthInvalidUserException("404", "User not found");

                /* Check if email is verified. */
                user.reload();
                if (!user.isEmailVerified())
                    throw new FirebaseAuthEmailException("404", "Email is not verified");

                /* If email is verified, logout... */
                auth.signOut();

                /* ...and go to login page. */
                Toast.makeText(this, "Email is verified. You can login now.", Toast.LENGTH_SHORT)
                    .show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();

            } catch (FirebaseAuthEmailException e) {
                Toast.makeText(this, "Please verify your email first, then try again.",
                    Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.signOut();
    }
}