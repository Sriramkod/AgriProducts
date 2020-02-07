package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class OrderNow extends AppCompatActivity {

    String TempPrice,TempFname,TempFaddress,TempId,TempActualQuantity,TempItem, TempFarmerPhone, TempCustomerAadhar,TempFarmerAadhar,TempFarmerAddress,TempQuantity;
    String ServerURL = "https://learnfriendly.000webhostapp.com/item.php";
    private ProgressDialog progressDialog;
    Button order;
    ImageView imageView;
    TextView itemName,Quantity,Price,FarmerName;
    EditText details;
    String item=null,quantity=null,price=null,farmerName=null,farmerPhone=null,farmerAadhar=null,customerAadhar=null;
    String id=null;
    String FarmerAddress=null;
    int carryPrice=0;int carryPrice1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_now);
        order = (Button)findViewById(R.id.order);
        imageView = (ImageView)findViewById(R.id.imageView);
        itemName = (TextView)findViewById(R.id.item);
        Quantity = (TextView)findViewById(R.id.Quantity);
        Price = (TextView)findViewById(R.id.Price);
        FarmerName = (TextView)findViewById(R.id.FarmerName);
        details = (EditText) findViewById(R.id.details);



        Intent intent = getIntent();

        item = intent.getStringExtra("item").toString();
        quantity = intent.getStringExtra("Quantity").toString();
        price = intent.getStringExtra("Price").toString();
        farmerName = intent.getStringExtra("FarmerName").toString();
        farmerPhone = intent.getStringExtra("FarmerPhone").toString();
        farmerAadhar = intent.getStringExtra("FarmerAadhar").toString();
        customerAadhar = intent.getStringExtra("CustomerAadhar").toString();
        FarmerAddress = intent.getStringExtra("FarmerAddress").toString();

        carryPrice = Integer.parseInt(price);
        id = intent.getStringExtra("itemid").toString();
        if(item.equalsIgnoreCase("onion")){
            itemName.setText("Onion");
            order.setText("Order Onion");
            imageView.setImageResource(R.drawable.onion);
        }
        else if(item.equalsIgnoreCase("wheat")){
            itemName.setText("Wheat");
            imageView.setImageResource(R.drawable.wheat);
            order.setText("Order Wheat");
        }
        else if(item.equalsIgnoreCase("turmeric")){
            itemName.setText("Turmeric");
            order.setText("Order Turmeric");
            imageView.setImageResource(R.drawable.turmeric);
        }
        else if(item.equalsIgnoreCase("rice")){
            itemName.setText("Rice");
            order.setText("Order Rice");
            imageView.setImageResource(R.drawable.rice);
        }
        else if(item.equalsIgnoreCase("ground")){
            itemName.setText("Ground_nut");
            order.setText("Order GroundNut");
            imageView.setImageResource(R.drawable.groundnut);
        }
        else if(item.equalsIgnoreCase("mirchi")){
            itemName.setText("Red Chilli");
            order.setText("Order red chilli");
            imageView.setImageResource(R.drawable.red_chilli);
        }
        else if(item.equalsIgnoreCase("maize")){
            itemName.setText("Maize");
            order.setText("Order Maize");
            imageView.setImageResource(R.drawable.maize);
        }
        Quantity.setText("Quantity Available: "+quantity+" Kg(s)");
        Price.setText("Price: "+price+" per Kg!...");
        FarmerName.setText("Farmer Name: "+farmerName);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              int requirement = Integer.parseInt(details.getText().toString());
              int Quant = Integer.parseInt(quantity);
              carryPrice1 = Integer.parseInt(price)*requirement;
              if(requirement>Quant || details.getText().toString().equalsIgnoreCase(null))
              {
                  AlertDialog.Builder builder = new AlertDialog.Builder(OrderNow.this);
                  builder.setTitle("Farmer_Customer Collaboration");
                  builder.setMessage("Your reuiremenet is greater than the quantity with the customer")
                          .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int kk) {
                                  details.setText("");
                                  Toast.makeText(OrderNow.this, "Please enter within Quantity...", Toast.LENGTH_SHORT).show();
                              }
                          });
                  AlertDialog alert = builder.create();
                  alert.show();
              }
              else if(requirement==0)
              {
                  AlertDialog.Builder builder = new AlertDialog.Builder(OrderNow.this);
                //  builder.setTitle("Farmer_Customer Collaboration");
                  builder.setMessage("Quantity cannot be zero bro...")
                          .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int kk) {
                                  details.setText("");
                                  Toast.makeText(OrderNow.this, "Please enter within Quantity...", Toast.LENGTH_SHORT).show();
                              }
                          });
                  AlertDialog alert = builder.create();
                  alert.show();
              }
              else
              {
                  GetData();

                  //InsertData(TempItem,TempQuantity,TempFarmerAadhar,TempCustomerAadhar,TempFarmerPhone,TempActualQuantity,TempId);
                  InsertData(TempItem,TempQuantity,TempFarmerAadhar,TempCustomerAadhar,TempFarmerPhone,TempActualQuantity,TempId,TempPrice,TempFname,TempFaddress);
              }

            }
        });
    }
    public void GetData(){
        TempItem = item;
       // TempQuantity = quantity;
        TempQuantity = details.getText().toString();
        TempFarmerAadhar = farmerAadhar;
        TempCustomerAadhar = customerAadhar;
        TempFarmerPhone = farmerPhone;
        TempActualQuantity = quantity;
        TempId = id;

        TempPrice = price;
        TempFname = farmerName;
        TempFaddress = FarmerAddress;
    }
    public void InsertData(final String name, final String phone, final  String aadhar,final String password,
                           final String address,final String actualQuantity,final String id,final String pp,final String pp1,final String pp2){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String ItemHolder = name ;
                String QuantityHolder = phone ;
                String PriceHolder = aadhar;
                String PassHolder = password;
                String AddressHolder  = address;
                String actual = actualQuantity;
                String idd = id;

String pk=pp;
String pk1=pp1;
String pk2 = pp2;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("item",ItemHolder));
                nameValuePairs.add(new BasicNameValuePair("quantity",QuantityHolder));
                nameValuePairs.add(new BasicNameValuePair("FarmerAadhar",PriceHolder));
                nameValuePairs.add(new BasicNameValuePair("CustomerAadhar",PassHolder));
                nameValuePairs.add(new BasicNameValuePair("FarmerPhone",AddressHolder));
                nameValuePairs.add(new BasicNameValuePair("actual",actual));
                nameValuePairs.add(new BasicNameValuePair("id",idd));
                nameValuePairs.add(new BasicNameValuePair("price",pk));
                nameValuePairs.add(new BasicNameValuePair("fname",pk1));
                nameValuePairs.add(new BasicNameValuePair("faddress",pk2));
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "ordered";
            }

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressDialog.show(OrderNow.this, "Just a minute","Wait....", true);
            }
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                progressDialog.dismiss();
                Toast.makeText(OrderNow.this, " Ordered Successfully ", Toast.LENGTH_LONG).show();
                if(result.equalsIgnoreCase("ordered")) {
                 Intent intent = new Intent(OrderNow.this, OrderSucessfully.class);
                 intent.putExtra("price",carryPrice1);
                  //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);
                }
                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name,phone,aadhar,password,address,actualQuantity,id,pp,pp1,pp2);
    }
}
