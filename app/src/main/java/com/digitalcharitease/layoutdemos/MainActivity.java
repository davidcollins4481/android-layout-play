package com.digitalcharitease.layoutdemos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button listViewButtonOne;
    Button listViewButtonTwo;
    Button listViewButtonThree;
    Button listViewButtonFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewButtonOne = (Button) findViewById(R.id.list_view_button_1);
        listViewButtonTwo = (Button) findViewById(R.id.list_view_button_2);
        listViewButtonThree = (Button) findViewById(R.id.list_view_button_3);
        listViewButtonFour = (Button) findViewById(R.id.simple_async_list_button);

        listViewButtonOne.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), ListTesting.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        });

        listViewButtonTwo.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), ListTesting.class);
            intent.putExtra("type", 2);
            startActivity(intent);
        });

        listViewButtonThree.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), ListTesting.class);
            intent.putExtra("type", 3);
            startActivity(intent);
        });

        listViewButtonFour.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), ListTesting.class);
            intent.putExtra("type", 4);
            startActivity(intent);
        });
    }
}
