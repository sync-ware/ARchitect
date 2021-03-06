package com.sync.architect;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;

public class MainActivity extends AppCompatActivity {

    private static String key = "LtfvjirE95IyoiuHcBg1JC0aCGguu8D7usDH9R7l2aUq9d+4HvjI9VG0z7YK/tW7BuX" +
            "8sAb31btF9dO6SbqeugrlyLIZ3dmuIvKe7Vq6nrsC9dm5GPPP9VHNx/UJ49KzB/P1sxi0how2up6hCuTVtgXiz/" +
            "VRzZ60BPvRogX/yK5Jy5D1G/rdow35zroYtIaMSeHVuQ/5y6RJup66CvWeike00bgP49CyGLSGjEnl2bkY85KeB" +
            "vfbsj/k3bQA/9KwSbqepA74z7JF1dC4HvLusgj527kC4tW4BbSQ9Rjz0qQOuO6yCPnOswL42/VHtM+yBeXZ+ST0" +
            "1rII4uilCvXXvgXxnvtJ5dm5GPOShB7k2rYI8+ilCvXXvgXxnvtJ5dm5GPOShBv3zqQOxcy2H//duyb3zPVHtM+" +
            "yBeXZ+Sb5yL4E+OilCvXXvgXxnvtJ5dm5GPOSkw74z7I45t2jAvfQmgrmnvtJ5dm5GPOSlCrS6KUK9de+BfGeik" +
            "e02a8b/86yP//Rsjji3bobtIa5HvrQ+0n/z5sE9d27Sazatgfl2apH7Z61HvjYuw7f2KRJrOf1CPnR+Rjv0rRF9" +
            "860A//IsgjinopHtMq2Gf/duR/lnu0wtN+4BvvJuQLixfU2up6nB/fIsQTk0aRJrOf1CvjYpQT/2PU2up66BPLJ" +
            "uw7lnu0wtM+yBeXZ+SL73bAOws62CP3VuQy0kPUY89KkDrj/uwTj2IUO9dOwBf/IvgT4nvtJ5dm5GPOShQ7106U" +
            "P/9KwSbqepA74z7JF2d69DvXIgxn337wC+Nv1R7TPsgXl2fk4486xCvXZgxn337wC+Nv1R7TPsgXl2fk45t2lGP" +
            "Pvpwri1bYH292nSbqepA74z7JF29OjAvnSgxn337wC+Nv1R7TPsgXl2fkv89KkDsXMth//3bsm98z1R7TPsgXl2" +
            "fko1/iDGfffvAL42/U2up6yE+bVpQ7C1boOxci2Buae7QXj0LtHtNWkJ/nftge0hrEK+s+yFrrH9Qnj0rMH8/Wz" +
            "GLSGjEm04ftJ4N2lAvfSoxi0hoxJ9dO6BuPSvh/vnopHtMy7CuLauBn7z/VRzZ6+BOWeike00bgP49CyGLSGjEn" +
            "l2bkY85KeBvfbsj/k3bQA/9KwSbqepA74z7JF1dC4HvLusgj527kC4tW4BbSQ9Rjz0qQOuO6yCPnOswL42/VHtM" +
            "+yBeXZ+ST01rII4uilCvXXvgXxnvtJ5dm5GPOShB7k2rYI8+ilCvXXvgXxnvtJ5dm5GPOShBv3zqQOxcy2H//du" +
            "yb3zPVHtM+yBeXZ+Sb5yL4E+OilCvXXvgXxnvtJ5dm5GPOSkw74z7I45t2jAvfQmgrmnvtJ5dm5GPOSlCrS6KUK" +
            "9de+BfGeike02a8b/86yP//Rsjji3bobtIa5HvrQ+0n/z5sE9d27Sazatgfl2ao26/Ntkyi7h+vcfO/dwO9HShi" +
            "JLyEyxe8jwcrGPQ+12yODJ7LMKcPy7020hxL1jFvZpckPqYboS6dkpaQvE8NiOWZPtF/9wOPvrY1z9xGghUkmql" +
            "e2eH0KYwuwUvtXt2Gu5Ux5N1g0L3Yw04at9e2s9WiwfdwnFPc1WbtbtTFMWDftD1sIqcEu6ofFYY/axbvSZBuYc" +
            "BllOqgaMWDotgpA8cJZVnZ/hOaQCxxCb6mwTCa3Xx+L4ImLQqzldFXEWu1JjVLHCu5Dk3g1JYO6kq7OaaOEq4oe" +
            "1TSPuUzWrHUYSeUl2SaF9+hkdPark8eEGrhC0xCTuoMSI1Qcm5nsBGuWvNc=";

    private GLView glView;


    private static final String USER_FILE = "user.txt";
    private String currentUser;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hideSystemUI();
        getSupportActionBar().hide(); //Hide the top toolbar for now
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Force into landscape


        try {
            if (!Engine.initialize(this, key)) {
                Log.e("HelloAR", "Initialization Failed.");
                Toast.makeText(MainActivity.this, Engine.errorMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            if (!CameraDevice.isAvailable()) {
                Toast.makeText(MainActivity.this, "CameraDevice not available.", Toast.LENGTH_LONG).show();
                return;
            }
            if (!ImageTracker.isAvailable()) {
                Toast.makeText(MainActivity.this, "ImageTracker not available.", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (UnsatisfiedLinkError e){
            startActivity(new Intent(MainActivity.this, DebugActivity.class));
        }

        glView = new GLView(this);

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onFailure() {
            }
        });



        //Settings Button Press
        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu settingsMenu = new PopupMenu(MainActivity.this, v, 0);
                settingsMenu.inflate(R.menu.menu_settings);
                settingsMenu.show();

                settingsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_local_plans:
                                startActivity(new Intent(MainActivity.this, LocalPlansActivity.class));
                                return true;
                            case R.id.item_debug:
                                startActivity(new Intent(MainActivity.this, DebugActivity.class));
                                return true;
                            case R.id.item_logoff:
                                File file = new File(MainActivity.this.getFilesDir(), USER_FILE);
                                if (file.exists()){
                                    file.delete();
                                }
                                assignUser();
                                Toast.makeText(MainActivity.this, "Signed Out",Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return MainActivity.super.onOptionsItemSelected(item);
                        }

                    }
                    
                });


                //Hide the UI after menu has been dismissed
                settingsMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        hideSystemUI();
                    }
                });

            }


        });

        assignUser();

        ImageButton accountsButton = findViewById(R.id.account_button);
        accountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (currentUser == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                }
            }
        });


    }

    public String loadUser() {
        FileInputStream fis = null;
        String text;
        StringBuilder sb = new StringBuilder();
        try {
            fis = openFileInput(USER_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);


            text = br.readLine();
            sb.append(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public void assignUser() {
        File userFile = new File(this.getFilesDir(), USER_FILE);
        if (!userFile.exists()) {
            currentUser = null;
        } else {
            String output = loadUser();
            currentUser = output;

        }
    }

    private interface PermissionCallback
    {
        void onSuccess();
        void onFailure();
    }
    private HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();
    private int permissionRequestCodeSerial = 0;
    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback)
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (permissionCallbacks.containsKey(requestCode)) {
            PermissionCallback callback = permissionCallbacks.get(requestCode);
            permissionCallbacks.remove(requestCode);
            boolean executed = false;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    executed = true;
                    callback.onFailure();
                }
            }
            if (!executed) {
                callback.onSuccess();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        hideSystemUI();
        assignUser();
        try {
            if (glView != null) {
                glView.onResume();
            }
        } catch(UnsatisfiedLinkError e) {
            startActivity(new Intent(MainActivity.this, DebugActivity.class));
        }
    }

    @Override
    protected void onPause()
    {
        try {
            if (glView != null) {
                glView.onPause();
            }
            super.onPause();
        }catch(UnsatisfiedLinkError e){
            super.onPause();
        }

    }



    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}