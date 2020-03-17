package com.sync.architect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class Registration extends AppCompatActivity {
    EditText rUsername, rFirstName, rLastName, rEmail, rPassword, rConfirmPassword;
    Button register;


    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;

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
                }

                else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    public void insertData(String username, String firstName, String lastName, String email, String password) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(db.col2, username);
//        contentValues.put(db.col3, firstName);
//        contentValues.put(db.col4, lastName);
//        contentValues.put(db.col5, email);
//        contentValues.put(db.col6, password);
//
//        db.createAccount(contentValues);
//    }
}
