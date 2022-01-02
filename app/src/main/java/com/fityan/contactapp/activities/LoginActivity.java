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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
  /**
   * Firebase authentication.
   */
  private final FirebaseAuth auth = FirebaseAuth.getInstance();
  private FirebaseUser user = auth.getCurrentUser();

  /* View Elements */
  private EditText inputEmail, inputPassword;
  private Button btnLogin, btnRegister;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    /* Get View Elements by its id */
    inputEmail = findViewById(R.id.inputEmail);
    inputPassword = findViewById(R.id.inputPassword);
    btnLogin = findViewById(R.id.btnLogin);
    btnRegister = findViewById(R.id.btnRegister);

    /* Event handler when Login Button is clicked */
    btnLogin.setOnClickListener(view -> {
      /* Input validation */
      try {
        String email = getTextFromInput(inputEmail, true);
        String password = getTextFromInput(inputPassword, true);

        /* start authentication. */
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
          try {
            if (!task.isSuccessful())
              throw Objects.requireNonNull(task.getException());

            /* If sign in success, verify the email. */
            user = task.getResult().getUser();

            if (user == null)
              recreate();
            if (user.isEmailVerified())
              goToMainActivity();
            else {
              auth.signOut();
              Toast.makeText(this, "Please verify your email first, then try again.",
                  Toast.LENGTH_SHORT).show();
            }
          } catch (Exception e) {
            /* If sign in failed, show alert message. */
            Toast.makeText(this, "Login failed! Wrong Email or Password.", Toast.LENGTH_SHORT)
                .show();
          }
        });
      } catch (Exception e) {
        Log.w("InvalidInput", "Input is invalid", e);
      }
    });

    /* Event handler when Login Button is clicked */
    btnRegister.setOnClickListener(view -> {
      /* Go to register activity. */
      startActivity(new Intent(this, RegisterActivity.class));
      finish();
    });
  }


  @Override
  protected void onStart() {
    super.onStart();

    /* Check if user is authorized (non-null). */
    try {
      if (user == null)
        throw new FirebaseAuthInvalidUserException("404", "User not found.");
      if (!user.isEmailVerified()) {
        auth.signOut();
        throw new FirebaseAuthEmailException("404", "Email is not verified.");
      }
      goToMainActivity();
    } catch (FirebaseAuthEmailException e) {
      Toast.makeText(this, "Please verify your email first then try again.", Toast.LENGTH_LONG)
          .show();
    } catch (Exception e) {
      Log.w("login", "User not found", e);
    }
  }


  /**
   * Retreive data from input, and validate the requirement.
   *
   * @param input The input element.
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


  /**
   * Go to main activity.
   */
  private void goToMainActivity() {
    startActivity(new Intent(LoginActivity.this, MainActivity.class));
    finish();    // Stop current activity.
  }
}
