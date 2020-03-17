package com.sync.architect;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_FILE = "user.txt";

    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        final EditText userNameText = findViewById(R.id.rUsername_login);
        final EditText passWordText = findViewById(R.id.rPassword_login);

        Button signUpButton = findViewById(R.id.rButton_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Registration.class));
            }
        });

        Button logInButton = findViewById(R.id.rButton_login);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor table = mDb.rawQuery("SELECT username FROM Accounts WHERE username = '" + userNameText.getText()
                        + "' AND password = '" + passWordText.getText() + "'", null);

                if (table.getCount() == 0){
                    Toast.makeText(v.getContext(), "Username and Password does not match", Toast.LENGTH_SHORT).show();
                } else{
                    table.moveToPosition(0);
                    String userName = table.getString(table.getColumnIndex("username"));
                    saveUser(userName);
                    Toast.makeText(v.getContext(), userName + " Logged In", Toast.LENGTH_SHORT).show();
                    mDb.close();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });





    }

    public void saveUser(String userName) {

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(USER_FILE, MODE_PRIVATE);
            fos.write(userName.getBytes());
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
