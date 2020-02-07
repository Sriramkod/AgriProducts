package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ViewCustomerItemFull extends AppCompatActivity {

    Button cancel;
    ImageView imageView;
    TextView itemName,Quantity,FarmerPhone;
    EditText details;
    String farmerAddress=null,item=null,quantity=null,price=null,farmerName=null,farmerPhone=null,farmerAadhar=null,customerAadhar=null;
    private ProgressDialog progressDialog;
    String id=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_item_full);
      final String ServerURL = "https://learnfriendly.000webhostapp.com/cancel.php";
        imageView = (ImageView)findViewById(R.id.imageView);
        itemName = (TextView)findViewById(R.id.item);
        Quantity = (TextView)findViewById(R.id.Quantity);
        FarmerPhone = (TextView)findViewById(R.id.FarmerPhone);
        cancel = findViewById(R.id.cancel);
        Intent intent = getIntent();

        item = intent.getStringExtra("item").toString();
        quantity = intent.getStringExtra("Quantity").toString();
        farmerPhone = intent.getStringExtra("FarmerPhone").toString();
        farmerAadhar = intent.getStringExtra("FarmerAadhar").toString();
        customerAadhar = intent.getStringExtra("CustomerAadhar").toString();
        price = intent.getStringExtra("price").toString();
        farmerName = intent.getStringExtra("fname").toString();
        farmerAddress = intent.getStringExtra("faddress").toString();
        id  = intent.getStringExtra("id").toString();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNetworkConnected()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewCustomerItemFull.this);
                    //  builder.setTitle("Farmer_Customer Collaboration");
                    builder.setMessage("You don't have an active internet connection...")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int kk) {
                                    Toast.makeText(ViewCustomerItemFull.this, "Please check your connection...", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }else {
                    getJSON(ServerURL, item, quantity, farmerPhone, farmerAadhar, customerAadhar, price, farmerName, farmerAddress, id);
                }
            }
        });
//super.onBackPressed();

        if(item.equalsIgnoreCase("onion")){
            itemName.setText("Onion");
            imageView.setImageResource(R.drawable.onion);
            Quantity.setText("Quantity orderd: "+quantity+" Kg(s)");
            FarmerPhone.setText("Farmer Phone: "+farmerPhone);
        }
        else if(item.equalsIgnoreCase("wheat")){
            itemName.setText("Wheat");
            imageView.setImageResource(R.drawable.wheat);
            Quantity.setText("Quantity orderd: "+quantity+" Kg(s)");
            FarmerPhone.setText("Farmer Phone: "+farmerPhone);
        }
        else if(item.equalsIgnoreCase("turmeric")){
            itemName.setText("Turmeric");

            imageView.setImageResource(R.drawable.turmeric);
            Quantity.setText("Quantity orderd: "+quantity+" Kg(s)");
            FarmerPhone.setText("Farmer Phone: "+farmerPhone);
        }
        else if(item.equalsIgnoreCase("rice")){
            itemName.setText("Rice");

            imageView.setImageResource(R.drawable.rice);
            Quantity.setText("Quantity orderd: "+quantity+" Kg(s)");
            FarmerPhone.setText("Farmer Phone: "+farmerPhone);
        }
        else if(item.equalsIgnoreCase("ground")){
            itemName.setText("Ground_nut");

            imageView.setImageResource(R.drawable.groundnut);
            Quantity.setText("Quantity orderd: "+quantity+" Kg(s)");
            FarmerPhone.setText("Farmer Phone: "+farmerPhone);
        }
        else if(item.equalsIgnoreCase("mirchi")){
            itemName.setText("Red Chilli");

            imageView.setImageResource(R.drawable.red_chilli);
            Quantity.setText("Quantity orderd: "+quantity+" Kg(s)");
            FarmerPhone.setText("Farmer Phone: "+farmerPhone);
        }
        else if(item.equalsIgnoreCase("maize")){
            itemName.setText("Maize");

            imageView.setImageResource(R.drawable.maize);
            Quantity.setText("Quantity orderd: "+quantity+" Kg(s)");
            FarmerPhone.setText("Farmer Phone: "+farmerPhone);
        }

    }
    private void getJSON(final String urlWebService,final String item,final String cquantity,final String fphone,final String faadhar,
                         final String caadhar,final String price,final String fname,final String faddress,final String id) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(ViewCustomerItemFull.this, "Loading Data","Please Wait....", true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                try {
                    Toast.makeText(ViewCustomerItemFull.this, "Cancellation sucessful"+s, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewCustomerItemFull.this, Cancellation.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

//start
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("item","UTF-8")+"="+URLEncoder.encode(item,"UTF-8")+"&"
                            +URLEncoder.encode("cquantity","UTF-8")+"="+URLEncoder.encode(cquantity,"UTF-8")+"&"
                            +URLEncoder.encode("fphone","UTF-8")+"="+URLEncoder.encode(fphone,"UTF-8")+"&"
                            +URLEncoder.encode("faadhar","UTF-8")+"="+URLEncoder.encode(faadhar,"UTF-8")+"&"
                            +URLEncoder.encode("caadhar","UTF-8")+"="+URLEncoder.encode(caadhar,"UTF-8")+"&"
                            +URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8")+"&"
                            +URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")+"&"
                            +URLEncoder.encode("faddress","UTF-8")+"="+URLEncoder.encode(faddress,"UTF-8")+"&"
                            +URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
//end
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(ViewCustomerItemFull.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        ViewCustomerItemFull.super.onBackPressed();
                    }
                }).create().show();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
