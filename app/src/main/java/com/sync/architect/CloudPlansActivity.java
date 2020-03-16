package com.sync.architect;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CloudPlansActivity extends AppCompatActivity {

    private List<Floorplan> flrplnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_plans);

        flrplnList = new ArrayList<>();
        flrplnList.add(new Floorplan("floorplan 1", null));
        flrplnList.add(new Floorplan("floorplan 2", null));

        RecyclerView myRecView = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter_Cloud myAdapter = new RecyclerViewAdapter_Cloud(this, flrplnList);
        myRecView.setLayoutManager(new GridLayoutManager(this, 2));
        myRecView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new RecyclerViewAdapter_Cloud.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CloudPlansActivity.this);
                builder.setTitle("Options Menu");

                builder.setPositiveButton("Share with a contact", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO Share currently selected floor plan with a known contact
                    }
                });

                builder.setNegativeButton("Remove from Drive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO remove the currently selected floor plan from cloud storage
                    }
                });

                builder.setNeutralButton("Download to local storage", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO download the currently selected floor plan to their local storage
                    }
                });

                builder.show();
            }
        });
    }

}
