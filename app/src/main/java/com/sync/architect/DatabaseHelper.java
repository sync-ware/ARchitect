package com.sync.architect;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "ARchitectDB";
    public static final String tableName = "Accounts";
    public static final String col2 = "username";
    public static final String col3 = "first_name";
    public static final String col4 = "last_name";
    public static final String col5 = "email";
    public static final String col6 = "password";
    public static int version = 1;

    private String createAccountsTable = "CREATE TABLE if not exists \"Accounts\" (\n" +
            "\t\"user_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"username\"\tTEXT,\n" +
            "\t\"first_name\"\tTEXT,\n" +
            "\t\"last_name\"\tTEXT,\n" +
            "\t\"email\"\tTEXT,\n" +
            "\t\"password\"\tTEXT\n" +
            ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, version);
        getWritableDatabase().execSQL(createAccountsTable);
    }

    public void createAccount(ContentValues values) {
        getWritableDatabase().insert(tableName, "", values);
    }

//    public Boolean isLoginValid(String username, String password) {
//        String query = "SELECT count(*) FROM Accounts WHERE username = '" + username + "' AND password = '"
//                + password + "'";
//        SQLiteStatement statement = getReadableDatabase().compileStatement(query);
//        long l = statement.simpleQueryForLong();
//        statement.close();
//
//        return (l == 1);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createAccountsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
