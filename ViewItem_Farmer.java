package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ViewItem_Farmer extends AppCompatActivity {

    String aadhar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item__farmer);

Intent intent = getIntent();
aadhar = intent.getStringExtra("aadhar").toString();
    }

    public void ground(View view) {
        String ground="ground";
        Intent intent = new Intent(ViewItem_Farmer.this,FarmerDisplay.class);
        intent.putExtra("item",ground);
        intent.putExtra("aadhar",aadhar);
        startActivity(intent);
    }

    public void maize(View view) {
        String ground="maize";
        Intent intent = new Intent(ViewItem_Farmer.this,FarmerDisplay.class);
        intent.putExtra("item",ground);
        intent.putExtra("aadhar",aadhar);
        startActivity(intent);
    }

    public void wheat(View view) {
        String ground="wheat";
        Intent intent = new Intent(ViewItem_Farmer.this,FarmerDisplay.class);
        intent.putExtra("item",ground);
        intent.putExtra("aadhar",aadhar);
        startActivity(intent);
    }

    public void rice(View view) {
        String ground="rice";
        Intent intent = new Intent(ViewItem_Farmer.this,FarmerDisplay.class);
        intent.putExtra("item",ground);
        intent.putExtra("aadhar",aadhar);
        startActivity(intent);
    }

    public void turmeric(View view) {
        String ground="turmeric";
        Intent intent = new Intent(ViewItem_Farmer.this,FarmerDisplay.class);
        intent.putExtra("item",ground);
        intent.putExtra("aadhar",aadhar);
        startActivity(intent);
    }

    public void onion(View view) {
        String ground="onion";
        Intent intent = new Intent(ViewItem_Farmer.this,FarmerDisplay.class);
        intent.putExtra("item",ground);
        intent.putExtra("aadhar",aadhar);
        startActivity(intent);
    }

    public void red_chilli(View view) {
        String ground="mirchi";
        Intent intent = new Intent(ViewItem_Farmer.this,FarmerDisplay.class);
        intent.putExtra("item",ground);
        intent.putExtra("aadhar",aadhar);
        startActivity(intent);
    }
}
