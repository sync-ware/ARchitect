package com.sync.architect;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String name = "database";
    private static int version = 1;

    private String createAccountsTable = "CREATE TABLE if not exists \"Accounts\" (\n" +
            "\t\"user_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"username\"\tTEXT,\n" +
            "\t\"first_name\"\tTEXT,\n" +
            "\t\"last_name\"\tTEXT,\n" +
            "\t\"email\"\tTEXT,\n" +
            "\t\"password\"\tTEXT\n" +
            ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createAccountsTable);
    }

    public void createAccount(ContentValues values) {
        getWritableDatabase().insert("Accounts", "", values);
    }

    public Boolean isLoginValid(String username, String password) {
        String query = "SELECT count(*) FROM Accounts WHERE username = '" + username + "' AND password = '"
                + password + "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(query);
        long l = statement.simpleQueryForLong();
        statement.close();

        if (l == 1) {
            return true;
        }

        else {
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}