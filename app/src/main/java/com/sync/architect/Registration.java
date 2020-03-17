package com.sync.architect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Registration extends AppCompatActivity {
    EditText rUsername, rFirstName, rLastName, rEmail, rPassword, rConfirmPassword;
    Button register, logInButton;


    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;

    private static final String USER_FILE = "user.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();

        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

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
                String firstNameValue = rFirstName.getText().toString();
                String lastNameValue = rLastName.getText().toString();
                String emailValue = rEmail.getText().toString();
                String passwordValue = rPassword.getText().toString();
                String confirmPasswordValue = rConfirmPassword.getText().toString();

                if (passwordValue.equals(confirmPasswordValue)) {
                    ContentValues values = new ContentValues();
                    values.put("username", usernameValue);
                    values.put("first_name", firstNameValue);
                    values.put("last_name", lastNameValue);
                    values.put("email", emailValue);
                    values.put("password", passwordValue);
                    mDb.insert("Accounts","",values);
                    Toast.makeText(getApplicationContext(), "Registered Successfully :)", Toast.LENGTH_LONG).show();
                    mDb.close();
                    saveUser();
                    startActivity(new Intent(Registration.this, MainActivity.class));
                    finish();

                }

                else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match :(", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void saveUser() {

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(USER_FILE, MODE_PRIVATE);
            fos.write(rUsername.getText().toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
