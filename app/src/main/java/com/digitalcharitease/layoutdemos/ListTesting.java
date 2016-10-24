package com.digitalcharitease.layoutdemos;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.id.list;

public class ListTesting extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1;
    ListView events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_testing);

        Bundle b = getIntent().getExtras();
        int type = b.getInt("type");

        switch (type) {
            case 1:
                this.listItemsSimpleOne();
                break;
            case 2:
                this.listItemsSimpleTwo();
                break;
            case 3:
                this.listItemsCustom();
                break;
            default:
                this.listItemsSimpleOne();
        }
    }

    private void listItemsSimpleOne() {
        events = (ListView) findViewById(R.id.mylist);
        String[] items = new String[] {"one", "two", "three"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        events.setAdapter(adapter);
        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ListTesting", "clicked");

            }
        });
    }

    private void listItemsSimpleTwo() {
        events = (ListView) findViewById(R.id.mylist);
        String[] items = new String[] {"one", "two", "three"};
        String[] subItems = new String[] {"under one", "under two", "under three"};
        String[] from = { "top", "bottom" };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        ArrayList<Map<String, String>> adapterItems;

        try {
            adapterItems = listTwoItems(items, subItems);
            SimpleAdapter adapter = new SimpleAdapter(this, adapterItems, android.R.layout.simple_list_item_2, from, to);
            events.setAdapter(adapter);

        } catch (Exception e) {
            // do nothing
            Log.d("ListTesting", e.getMessage());
        }
    }

    private void listItemsCustom() {
        events = (ListView) findViewById(R.id.mylist);
        String[] items = new String[] {"one", "two", "three"};
        String[] subItems = new String[] {"under one lorem ips", "under two", "under three"};
        String[] from = { "title", "description" };
        int[] to = { R.id.title, R.id.description };

        ArrayList<Map<String, String>> adapterItems;

        try {
            adapterItems = listCustomItems(items, subItems);
            SimpleAdapter adapter = new SimpleAdapter(this, adapterItems, R.layout.row, from, to);
            events.setAdapter(adapter);

        } catch (Exception e) {
            // do nothing
            Log.d("ListTesting", e.getMessage());
        }
    }

    private ArrayList<Map<String, String>> listCustomItems(String[] top, String[] bottom) throws Exception {
        if (top.length != bottom.length) {
            throw new Exception("List need to be the same size for simple list two");
        }

        ArrayList<Map<String, String>> list = new ArrayList<>();

        int i = 0;
        while (i < top.length) {
            Map<String, String> map = new HashMap<>();
            map.put("title", top[i]);
            map.put("description", bottom[i]);

            list.add(map);
            i++;
        }

        return list;
    }

    private ArrayList<Map<String, String>> listTwoItems(String[] top, String[] bottom) throws Exception {
        if (top.length != bottom.length) {
            throw new Exception("List need to be the same size for simple list two");
        }

        ArrayList<Map<String, String>> list = new ArrayList<>();

        int i = 0;
        while (i < top.length) {
            Map<String, String> map = new HashMap<>();
            map.put("top", top[i]);
            map.put("bottom", bottom[i]);

            list.add(map);
            i++;
        }

        return list;
    }


}
