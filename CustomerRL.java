package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerRL extends AppCompatActivity {
    EditText UsernameEt,PasswordEt;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rl);
        UsernameEt = findViewById(R.id.user);
        PasswordEt = findViewById(R.id.pwd);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = UsernameEt.getText().toString();

                String password = PasswordEt.getText().toString();

                String type = "login";
                if(!isNetworkConnected())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRL.this);
                    //  builder.setTitle("Farmer_Customer Collaboration");
                    builder.setMessage("You don't have an active internet connection...")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int kk) {
                                    Toast.makeText(CustomerRL.this, "Please check your connection...", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    BackgroundWorker backgroundWorker = new BackgroundWorker(CustomerRL.this);
                    backgroundWorker.execute(type, username, password);
                }
            }
        });

    }

    public void newUser(View view) {
        Intent intent = new Intent(CustomerRL.this,CustomerRegistration.class);
        startActivity(intent);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
