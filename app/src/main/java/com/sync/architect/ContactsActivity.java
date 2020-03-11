package com.sync.architect;

import android.content.res.Resources;
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

import org.json.JSONException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

public class ContactsActivity extends AppCompatActivity {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> filteredList = new ArrayList<>();
    private ArrayAdapter adapter;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Log.d("ContactsActivity", "onCreate: Started.");
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        list = findViewById(R.id.listView);
        names.addAll(readUserContacts("mr864@bath.ac.uk"));

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


        //Button to add a new contact (for now by name but add verficiation and emails in the future
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                builder.setTitle("Enter user email address");
                final EditText input = new EditText(ContactsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        names.add(input.getText().toString());
                        adapter = new ArrayAdapter(ContactsActivity.this, android.R.layout.simple_list_item_1 , names);
                        list.setAdapter(adapter);
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

    private ArrayList<String> readUserContacts(String email) {
        try {
            ArrayList<String> contacts = new ArrayList<String>();
            JSONParser jsonParser = new JSONParser();
            InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.contactdata));
            JSONArray allContacts = (JSONArray) jsonParser.parse(reader);

            for (int i = 0; i < allContacts.size(); i++) {
                JSONObject currentOBJ = (JSONObject) allContacts.get(i);
                if (currentOBJ.get("email").equals(email)) {
                    Collection<String> userContacts = (Collection<String>) currentOBJ.get("contacts");
                    contacts.addAll(userContacts);
                    return contacts;
                }
            }

            Log.d("JSON contact List", "email does not exists");
            contacts.add("Mistake");
            return contacts;
        } catch (ParseException | IOException ex) {
            Log.d("Reading JSON error", "JSON is having issues");
            return null;
        }
    }

    private ArrayList<String> filterList(ArrayList<String> aList, CharSequence charSequence){
        filteredList.clear();
        for (int count = 0; count < aList.size(); count++){
            if (aList.get(count).contains(charSequence)){
                filteredList.add(aList.get(count));
            }
        }
        return filteredList;
    }

}
