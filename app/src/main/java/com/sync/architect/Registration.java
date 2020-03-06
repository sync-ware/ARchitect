package com.sync.architect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    EditText rUsername, rFirstName, rLastName, rEmail, rPassword, rConfirmPassword;
    Button register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        rUsername = findViewById(R.id.rUsername);
        rFirstName = findViewById(R.id.rFirstName);
        rLastName = findViewById(R.id.rLastName);
        rEmail = findViewById(R.id.rEmail);
        rPassword = findViewById(R.id.rPassword);
        rConfirmPassword = findViewById(R.id.rConfirmPassword);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = rUsername.getText().toString();
                String firstNameValue = rEmail.getText().toString();
                String lastNameValue = rEmail.getText().toString();
                String emailValue = rEmail.getText().toString();
                String passwordValue = rPassword.getText().toString();
                String confirmPasswordValue = rConfirmPassword.getText().toString();

                if (passwordValue.equals(confirmPasswordValue)) {
                    insertData(usernameValue, firstNameValue, lastNameValue, emailValue, passwordValue);
                    Toast.makeText(getApplicationContext(), "Registered Successfully :)", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void insertData(String username, String firstName, String lastName, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.col2, username);
        contentValues.put(DatabaseHelper.col3, firstName);
        contentValues.put(DatabaseHelper.col4, lastName);
        contentValues.put(DatabaseHelper.col5, email);
        contentValues.put(DatabaseHelper.col6, password);

        db.createAccount(contentValues);
    }
}
