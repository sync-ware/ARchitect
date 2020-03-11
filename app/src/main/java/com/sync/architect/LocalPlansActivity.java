package com.sync.architect;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LocalPlansActivity extends AppCompatActivity {

    private List<Floorplan> flrplnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_plans);

        flrplnList = new ArrayList<>();
        //flrplnList.add(new Floorplan("floorplan 1"));


        try {
            String[] fileNames = getAssets().list("plans");
            for (String planName : fileNames) {

                InputStream ims = getAssets().open("plans/" + planName);
                Drawable drawable = Drawable.createFromStream(ims, null);
                Floorplan plan = new Floorplan(planName, drawable);
                ims.close();
                flrplnList.add(plan);


            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Couldn't generate plan", Toast.LENGTH_SHORT).show();

        }


        RecyclerView myRecView = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, flrplnList);
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
