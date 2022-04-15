package com.example.e_commerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegistrationActivity extends AppCompatActivity {
EditText name, email, password;
private FirebaseAuth auth;
SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


       auth = FirebaseAuth.getInstance();
       if(auth.getCurrentUser()!= null){

          startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
          finish();
       }
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

sharedPreferences = getSharedPreferences("OnBoardingScreen", MODE_PRIVATE);
boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

if(isFirstTime){
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("firstTime", false);
    editor.commit();
    Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
    startActivity(intent);
    finish();
}


    }

    public void SignUp(View view) {

        String userName = name.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword =password.getText().toString().trim();
        if(userName.isEmpty()){
            name.setError("Name Required");
        }
        if(userEmail.isEmpty()){
           email.setError("Email Required");
        }
        if(userPassword.isEmpty() ){
           password.setError("Password Required ");
        }
        if(userPassword.length()<6){
            Toast.makeText(this, "Password Length is too Short, Enter Minimum 6 Characters", Toast.LENGTH_SHORT).show();
            return;
        }
   auth.createUserWithEmailAndPassword(userEmail, userPassword)
           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()) {

                           Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                       }
if(!task.isSuccessful()){

    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

    switch (errorCode) {

        case "ERROR_INVALID_CUSTOM_TOKEN":
            Toast.makeText(RegistrationActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_CUSTOM_TOKEN_MISMATCH":
            Toast.makeText(RegistrationActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_INVALID_CREDENTIAL":
            Toast.makeText(RegistrationActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_INVALID_EMAIL":
         //   Toast.makeText(RegistrationActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
            email.setError("The email address is badly formatted.");
            email.requestFocus();
            break;

        case "ERROR_WRONG_PASSWORD":
         //   Toast.makeText(RegistrationActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
           password.setError("password is incorrect ");
            password.requestFocus();
            password.setText("");
            break;

        case "ERROR_USER_MISMATCH":
            Toast.makeText(RegistrationActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_REQUIRES_RECENT_LOGIN":
            Toast.makeText(RegistrationActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
            Toast.makeText(RegistrationActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_EMAIL_ALREADY_IN_USE":
          //  Toast.makeText(RegistrationActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
            email.setError("The email address is already in use by another account.");
            email.requestFocus();
            break;

        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
            Toast.makeText(RegistrationActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_USER_DISABLED":
            Toast.makeText(RegistrationActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_USER_TOKEN_EXPIRED":
            Toast.makeText(RegistrationActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_USER_NOT_FOUND":
            Toast.makeText(RegistrationActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_INVALID_USER_TOKEN":
            Toast.makeText(RegistrationActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_OPERATION_NOT_ALLOWED":
            Toast.makeText(RegistrationActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
            break;

        case "ERROR_WEAK_PASSWORD":
            Toast.makeText(RegistrationActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
           password.setError("The password is invalid it must 6 characters at least");
            password.requestFocus();
            break;

    }
}
               }


           });

    }

    public void SignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}