package com.digitalcharitease.layoutdemos;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ListTesting extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_testing);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALENDAR)) {
                Toast.makeText(this, "one", Toast.LENGTH_SHORT).show();
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALENDAR},
                        MY_PERMISSIONS_REQUEST_READ_CALENDAR);
                Toast.makeText(this, "two", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        this.populateEvents();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    this.populateEvents();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.MY_PERMISSIONS_REQUEST_READ_CALENDAR
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void populateEvents() {
        Toast.makeText(this, "populate", Toast.LENGTH_SHORT).show();
    }
}
