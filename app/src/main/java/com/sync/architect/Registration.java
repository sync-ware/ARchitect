package com.sync.architect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends AppCompatActivity {
    EditText rEmail, rUsername, rPassword, rConfirmPassword, rFirstName, rLastName;
    Button register;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        rEmail = findViewById(R.id.rEmail);
        rFirstName = findViewById(R.id.rFirstName);
        rLastName = findViewById(R.id.rLastName);
        rUsername = findViewById(R.id.rUsername);
        rPassword = findViewById(R.id.rPassword);
        rConfirmPassword = findViewById(R.id.rConfirmPassword);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailValue = rEmail.getText().toString();
                String firstNameValue = rEmail.getText().toString();
                String lastNameValue = rEmail.getText().toString();
                String usernameValue = rUsername.getText().toString();
                String passwordValue = rPassword.getText().toString();
                String confirmPasswordValue = rConfirmPassword.getText().toString();
            }
        });
    }
}
