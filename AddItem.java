package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddItem extends AppCompatActivity {
    String aadhar = null;
    String text = null;
    Spinner spinner;
    Spinner item = null;
    Button save;
    String ServerURL = "https://learnfriendly.000webhostapp.com/onion.php";
    EditText quantity=null, price=null;
    Button button;
    Button login;
    String TempQuantity, TempPrice, TempItem;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        login = findViewById(R.id.login);
        save = findViewById(R.id.save);
        quantity = findViewById(R.id.q);
        price =  findViewById(R.id.p);
        spinner = (Spinner) findViewById(R.id.planets_spinner);
        item = (Spinner) findViewById(R.id.planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        aadhar = intent.getStringExtra("aadhar").toString();
        Toast.makeText(this, " "+aadhar, Toast.LENGTH_SHORT).show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = spinner.getSelectedItem().toString();

                if (text.equalsIgnoreCase("Select any item") || quantity.getText().toString().equals(null) || price.getText().toString().equals(null)) {
                    Toast.makeText(AddItem.this, "Enter All Details", Toast.LENGTH_LONG).show();
                } else {
                    //       Toast.makeText(MainActivity.this, "" + text, Toast.LENGTH_LONG).show();

                    GetData();

                    InsertData(TempItem,TempQuantity,TempPrice);

                }
            }
        });
    }
    public void GetData(){

        TempQuantity = quantity.getText().toString();

        TempItem = item.getSelectedItem().toString();

        TempPrice = price.getText().toString();

       /* Temptime = time.getText().toString();

        Temppeople = people.getSelectedItem().toString();;
*/
    }
    public void InsertData(final String item, final String quantity, final  String price){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String ItemHolder = item ;
                String QuantityHolder = quantity ;
                String PriceHolder = price;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("item",ItemHolder));
                nameValuePairs.add(new BasicNameValuePair("quantity",QuantityHolder));
                nameValuePairs.add(new BasicNameValuePair("price",PriceHolder));
                nameValuePairs.add(new BasicNameValuePair("aadhar",aadhar));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    if(text.equalsIgnoreCase("Onion"))
                        ServerURL = "https://learnfriendly.000webhostapp.com/onion.php";
                    else if(text.equalsIgnoreCase("Wheat"))
                        ServerURL = "https://learnfriendly.000webhostapp.com/wheat.php";
                    else if(text.equalsIgnoreCase("Turmeric"))
                        ServerURL = "https://learnfriendly.000webhostapp.com/turmeric.php";
                    else if(text.equalsIgnoreCase("Maize"))
                        ServerURL = "https://learnfriendly.000webhostapp.com/maize.php";
                    else if(text.equalsIgnoreCase("Rice"))
                        ServerURL = "https://learnfriendly.000webhostapp.com/rice.php";
                    else if(text.equalsIgnoreCase("Mirchi"))
                        ServerURL = "https://learnfriendly.000webhostapp.com/mirchi.php";
                    else if(text.equalsIgnoreCase("Ground_nut"))
                        ServerURL = "https://learnfriendly.000webhostapp.com/ground.php";

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Item Inserted Successfully";
            }

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressDialog.show(AddItem.this, "Just a minute","Wait....", true);
            }
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                progressDialog.dismiss();
                Toast.makeText(AddItem.this, "Item Inserted Successfully", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(order1.this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(item,quantity,price);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
