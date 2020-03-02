package com.sync.architect;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LocalPlansActivity extends AppCompatActivity {

    List<Floorplan> flrplnList;

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
    }

}
