package com.sync.architect;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.DialogInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.io.IOException;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> filteredList = new ArrayList<>();
    private ArrayAdapter adapter;
    private ListView list;
    private static final String Contacts_File = "contacts.json";
    DatabaseHelper cDBHelper;
    SQLiteDatabase cDB;
    private String currentUser = "pm3001"; //TODO read username from file


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Log.d("ContactsActivity", "onCreate: Started.");
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);

        cDBHelper = new DatabaseHelper(this);

        try {
            cDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            cDB = cDBHelper.getWritableDatabase();

        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        createTestContactsTable();

        list = findViewById(R.id.listView);
        checkNamesEmpty();
        names.addAll(readUserContacts());
        checkNamesEmpty();

        //default list of names for testing

        adapter = new ArrayAdapter(ContactsActivity.this, android.R.layout.simple_list_item_1 , names);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                builder.setTitle("Options");

                //Removes the contact that was selected
                builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        names.remove(i);
                        String newString = stringToSql(names);
                        ContentValues values = new ContentValues();
                        values.put("friends",newString);
                        cDB.update("contacts",values,"username = '" + currentUser + "'",null);
                        checkNamesEmpty();
                        adapter = new ArrayAdapter(ContactsActivity.this, android.R.layout.simple_list_item_1 , names);
                        list.setAdapter(adapter);
                        dialog.cancel();
                    }});
                //
                builder.setPositiveButton("Share Plan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //sharing plans with other users (involves sending and receiving and databases)
                    }
                });

                /*builder.setPositiveButton("View Profile", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Load user details from database and display them
                    }
                });*/
                builder.show();
            }
        });


        //Button to add a new contact (for now by name but add verification and emails in the future
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                builder.setTitle("Enter username");
                final EditText input = new EditText(ContactsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        if (checkUserExists(input.getText().toString())){
                            String newUserName = input.getText().toString();
                            String newFirstName = usernameToName(newUserName);
                            names.add(newFirstName);
                            checkNamesEmpty();
                            addUserContact(newFirstName);
                            adapter = new ArrayAdapter(ContactsActivity.this, android.R.layout.simple_list_item_1 , names);
                            list.setAdapter(adapter);
                        }
                        dialog.cancel();
                    }});
                builder.show();
            }
        });

        //Adds a listener to the filter that will react whenever the search bar is changed
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //Changes the listView to only display the contacts matching the filter
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter = new ArrayAdapter(ContactsActivity.this, android.R.layout.simple_list_item_1 , filterList(names, charSequence));
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkNamesEmpty() {
        String testString = "Your contact list is empty. Try adding a user with the button below";
        if (names.contains(testString)){
            names.remove(testString);
        }
        if (names.size() == 0 || names == null) {
            names.add("Your contact list is empty. Try adding a user with the button below");

        }

    }

    //Takes the username of the user and returns an array list of their added contacts
    private ArrayList<String> readUserContacts() {
        Cursor userContacts = cDB.rawQuery("SELECT friends from contacts where username = '" + currentUser + "'" ,null);
        userContacts.moveToPosition(0);
        String friendList = userContacts.getString(userContacts.getColumnIndex("friends"));
        String[] friendList2 = sqlToString(friendList);
        ArrayList<String> contactList = new ArrayList<String>();

        for (int i = 0; i < friendList2.length; i++){
            contactList.add(friendList2[i]);
        }
        userContacts.close();
        if (contactList.contains("")){
            contactList.remove("");
        }
        if (contactList.isEmpty()){
            contactList.add("Your contact list is empty. Try adding a user with the button below");
        }
        return contactList;
    }

    //adds a new contacts to the relevant user in the database
    private void addUserContact(String newContact){
        Cursor userContacts = cDB.rawQuery("SELECT friends from contacts where username = '" + currentUser + "'" ,null);
        userContacts.moveToPosition(0);
        String friendList = userContacts.getString(userContacts.getColumnIndex("friends"));
        String toConcat = ","+newContact;
        friendList = friendList.concat(toConcat);
        ContentValues values = new ContentValues();
        values.put("friends",friendList);
        cDB.update("contacts",values,"username = '" + currentUser + "'",null);
        userContacts.close();
    }

    //Filters the listview by a given character sequence
    private ArrayList<String> filterList(ArrayList<String> aList, CharSequence charSequence){
        filteredList.clear();
        for (int count = 0; count < aList.size(); count++){
            if (aList.get(count).contains(charSequence)){
                filteredList.add(aList.get(count));
            }
        }
        return filteredList;
    }

    //Creates a test table if there currently is not one
    private void createTestContactsTable(){
        //cDB.execSQL("drop table contacts");
        cDB.execSQL("create table if not exists contacts(" +
           "username text primary key," +
           "friends text)");
        Cursor contactExists = cDB.rawQuery("SELECT * FROM contacts where username = 'mr864'", null);
        if (contactExists.getCount() == 0) {
            Log.d("SQL database","Inserting test values");
            ContentValues values = new ContentValues();
            values.put("username","mr864");
            values.put("friends","Dan,James,Alex,Ryan,Ben");
            //values.put("username","jp221");
            //values.put("friends","Jasmine,Sophie,Kate,Abi,Issy,Gaby");
            cDB.insert("contacts","",values);
        }
        contactExists.close();
    }

    //converts the comma separated string from the database into a string array
    private String[] sqlToString(String sqlValue){
        String[] contactList = sqlValue.split( "," );
        return contactList;
    }

    //Converts a string array into a comma separated string to be inserted into the sql database
    private String stringToSql(ArrayList<String> contactList){
        if (names.size() ==0){
            return "";
        }
        String sqlString = "";
        for (int i = 0; i < contactList.size() - 1; i++){
            sqlString = sqlString.concat(contactList.get(i));
            sqlString = sqlString.concat(",");
        }
        sqlString = sqlString.concat(contactList.get(contactList.size()-1));
        return sqlString;
    }

    private Boolean checkUserExists(String user){
        Cursor userContacts = cDB.rawQuery("SELECT username from Accounts where username = '" + user + "'" ,null);
        userContacts.moveToPosition(0);
        if (userContacts.getCount() == 0){
            userContacts.close();
            return false;
        }
        userContacts.close();
        return true;
    }

    private String usernameToName(String username){
        Cursor userContacts = cDB.rawQuery("SELECT first_name from Accounts where username = '" + username + "'" ,null);
        userContacts.moveToPosition(0);
        String newFriend = userContacts.getString(userContacts.getColumnIndex("first_name"));
        userContacts.close();
        return newFriend;
    }

}
