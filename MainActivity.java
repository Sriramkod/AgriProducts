package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Customer(View view) {
        Intent intent = new Intent(MainActivity.this,CustomerRL.class);
        startActivity(intent);
       /* Intent intent = new Intent(MainActivity.this,OrderItem.class);
        startActivity(intent);*/
    }

    public void Farmer(View view) {
        Intent intent = new Intent(MainActivity.this,FarmerRL.class);
        startActivity(intent);
    }
}
