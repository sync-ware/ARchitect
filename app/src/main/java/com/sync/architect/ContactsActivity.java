package com.sync.architect;

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

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ListView list = findViewById(R.id.listView);
        Log.d("ContactsActivity", "onCreate: Started.");
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);

        names.add("Mitch");
        names.add("Blake");
        names.add("Shelly");
        names.add("Jess");
        names.add("Steve");
        names.add("Mohammed");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , names);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                builder.setTitle("Option Menu");

                //Remove contact
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        names.remove(i);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }});
                //
                builder.setNeutralButton("Share Plan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO Sharing plans with other users
                    }
                });

                builder.setNegativeButton("Other Button", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO any other contacts features
                    }
                });
                builder.show();
            }
        });


        //Button to add a new contact
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                builder.setTitle("Enter name");
                final EditText input = new EditText(ContactsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        names.add(input.getText().toString());
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }});
                builder.show();

            }
        });

        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });





    }

}
