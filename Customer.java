package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Customer extends AppCompatActivity {
String aadhar =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Intent intent = getIntent();
        aadhar = intent.getStringExtra("id").toString();
    }


    public void Order_an_item(View view) {
        if (!isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Customer.this);
            //  builder.setTitle("Farmer_Customer Collaboration");
            builder.setMessage("You don't have an active internet connection...")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int kk) {
                            Toast.makeText(Customer.this, "Please check your connection...", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Intent intent = new Intent(Customer.this, OrderItem.class);
            intent.putExtra("aadhar", aadhar);
            startActivity(intent);
        }
    }
    public void View_My_Orders(View view) {
        if (!isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Customer.this);
            //  builder.setTitle("Farmer_Customer Collaboration");
            builder.setMessage("You don't have an active internet connection...")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int kk) {
                            Toast.makeText(Customer.this, "Please check your connection...", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Intent intent = new Intent(Customer.this, ViewCustomerItem.class);
            intent.putExtra("aadhar", aadhar);
            startActivity(intent);
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}