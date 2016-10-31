package com.digitalcharitease.layoutdemos;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListTesting extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1;
    private ListView events;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        int type = b.getInt("type");

        setContentView(type == 4 ? R.layout.swipe_update_list : R.layout.activity_list_testing);
        swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_update_list);

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
            case 4:
                this.asyncSimpleList();
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
        String[] subItems = new String[] {"under one lorem ipsum here is some extra long text blah blah blah", "under two", "under three"};
        String[] from = { "title", "description" };
        int[] to = { R.id.title, R.id.description };

        ArrayList<Map<String, String>> adapterItems;

        try {
            adapterItems = listCustomItems(items, subItems);
            SimpleAdapter adapter = new SimpleAdapter(this, adapterItems, R.layout.row, from, to);

            events.setOnItemClickListener((parent, view, p, id) -> {
                Toast.makeText(getApplicationContext(), "On click", Toast.LENGTH_LONG).show();

            });

            events.setOnItemLongClickListener((parent, view, p, id) -> {
                Toast.makeText(getApplicationContext(), "On long click", Toast.LENGTH_LONG).show();
                return true;
            });

            events.setAdapter(adapter);
        } catch (Exception e) {
            // do nothing
            Log.d("ListTesting", e.getMessage());
        }
    }

    private void asyncSimpleList() {
        updateStockQuotes();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateStockQuotes();
            }
        });
    }

    private void updateStockQuotes() {
        final Context current = this;
        requestQueue = Volley.newRequestQueue(this);
        String jsonURL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20('AAPL'%2C'GOOG'%2C'MSFT')&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, jsonURL,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject obj = response
                                .getJSONObject("query")
                                .getJSONObject("results");

                        JSONArray results = obj.getJSONArray("quote");

                        ArrayList<String> quotes = new ArrayList<>();
                        ArrayList<String> subText = new ArrayList<>();

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject symbolDetail = (JSONObject) results.get(i);
                            quotes.add(symbolDetail.getString("Symbol"));
                            subText.add(symbolDetail.getString("Change"));
                        }

                        events = (ListView) findViewById(R.id.refreshlist);
                        String[] from = { "top", "bottom" };
                        int[] to = { android.R.id.text1, android.R.id.text2 };

                        ArrayList<Map<String, String>> adapterItems;

                        try {
                            adapterItems = listTwoItems(
                                    quotes.toArray(new String[quotes.size()]),
                                    subText.toArray(new String[subText.size()])
                            );

                            SimpleAdapter adapter = new SimpleAdapter(current, adapterItems, android.R.layout.simple_list_item_2, from, to);
                            events.setAdapter(adapter);
                            if (swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (Exception e) {
                            // do nothing
                            Log.d("ListTesting", e.getMessage());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT);
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    Log.e("Volley", "Error");
                }
            }
        );

        requestQueue.add(request);

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
