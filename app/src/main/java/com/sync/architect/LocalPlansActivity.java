package com.sync.architect;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LocalPlansActivity extends AppCompatActivity {

    private List<Floorplan> flrplnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_plans);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        flrplnList = new ArrayList<>();
        flrplnList.add(new Floorplan("floorplan 1"));
        flrplnList.add(new Floorplan("floorplan 2"));
        flrplnList.add(new Floorplan("floorplan 3"));
        flrplnList.add(new Floorplan("floorplan 4"));
        flrplnList.add(new Floorplan("floorplan 5"));
        flrplnList.add(new Floorplan("floorplan 6"));
        flrplnList.add(new Floorplan("floorplan 7"));
        flrplnList.add(new Floorplan("floorplan 8"));
        flrplnList.add(new Floorplan("floorplan 9"));
        flrplnList.add(new Floorplan("floorplan 10"));

        RecyclerView myRecView = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,flrplnList);
        myRecView.setLayoutManager(new GridLayoutManager(this,2));
        myRecView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LocalPlansActivity.this);
                builder.setTitle("Options Menu");

                builder.setPositiveButton("Share with a contact", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO Share currently selected floor plan with a known contact
                    }
                });

                builder.setNegativeButton("Remove from storage", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO remove the currently selected floor plan from local storage
                    }
                });

                builder.setNeutralButton("Upload to Drive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO upload the currently selected floor plan to their google drive
                    }
                });

                builder.show();
            }
        });
    }

}
