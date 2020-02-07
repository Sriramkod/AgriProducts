package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CustomerDisplay extends AppCompatActivity {
    ListView listView;
    String Customer = null;
    String email=null;
    String password = null;
    String[] heroes = new String[100];
    String[] Quantity = new String[100];
    String[] Price = new String[100];
    String[] FarmerName = new String[100];
    String[] FarmerPhone = new String[100];
    String[] FarmerAadhar = new String[100];
    String[] FarmerAddress = new String[100];
    String[] id = new String[100];
    String[] Total = new String[100];
    private ProgressDialog progressDialog;
String text=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_display);
        Intent intent1 = getIntent();
        email = intent1.getStringExtra("aadhar").toString();
        Customer = email;
        password = email;
        listView = (ListView) findViewById(R.id.listView);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
       emptyText.setVisibility(View.VISIBLE);
        listView.setEmptyView(emptyText);
        String ServerURL = "https://learnfriendly.000webhostapp.com/CustomerOnion.php";
        final Intent intent = getIntent();
         text = intent.getStringExtra("item").toString();
        if(text.equalsIgnoreCase("onion"))
            ServerURL = "https://learnfriendly.000webhostapp.com/CustomerOnion.php";
        else if(text.equalsIgnoreCase("wheat"))
            ServerURL = "https://learnfriendly.000webhostapp.com/CustomerWheat.php";
        else if(text.equalsIgnoreCase("turmeric"))
            ServerURL = "https://learnfriendly.000webhostapp.com/CustomerTurmeric.php";
        else if(text.equalsIgnoreCase("maize"))
            ServerURL = "https://learnfriendly.000webhostapp.com/CustomerMaize.php";
        else if(text.equalsIgnoreCase("rice"))
            ServerURL = "https://learnfriendly.000webhostapp.com/CustomerRice.php";
        else if(text.equalsIgnoreCase("mirchi"))
            ServerURL = "https://learnfriendly.000webhostapp.com/CustomerMirchi.php";
        else if(text.equalsIgnoreCase("ground"))
            ServerURL = "https://learnfriendly.000webhostapp.com/CustomerGround.php";

        getJSON(ServerURL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
              //  Toast.makeText(CustomerDisplay.this, "You clicked " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerDisplay.this, " "+id[index], Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(CustomerDisplay.this,OrderNow.class);
                intent2.putExtra("CustomerAadhar",Customer);
                intent2.putExtra("FarmerAadhar",FarmerAadhar[index]);
                intent2.putExtra("Quantity",Quantity[index]);
                intent2.putExtra("Price",Price[index]);
                intent2.putExtra("FarmerPhone",FarmerPhone[index]);
                intent2.putExtra("FarmerName",FarmerName[index]);
                intent2.putExtra("FarmerAddress",FarmerAddress[index]);
                intent2.putExtra("itemid",id[index]);
                intent2.putExtra("item",text);

                startActivity(intent2);
            }
        });
    }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(CustomerDisplay.this, "Loading Data","Please Wait....", true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
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

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        Quantity = new String[jsonArray.length()];
        Price = new String[jsonArray.length()];
        FarmerName = new String[jsonArray.length()];
        FarmerPhone = new String[jsonArray.length()];
        FarmerAadhar = new String[jsonArray.length()];
        FarmerAddress = new String[jsonArray.length()];
        id = new String[jsonArray.length()];
Total = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            FarmerName[i] = obj.getString("fname");
            FarmerAadhar[i] = obj.getString("aadhar");
            FarmerPhone[i] = obj.getString("fphone");
            Quantity[i] = obj.getString("quantity");
            Price[i] = obj.getString("price");
            FarmerAddress[i] = obj.getString("faddress");
            id[i] = obj.getString("id");
            Total[i] = "Farmer_Name:"+FarmerName[i]+"|| Quantity: "+Quantity[i]+" Kg(s) "+"|| Price: "+Price[i]+" per Kg.";
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Total);
        listView.setAdapter(arrayAdapter);
    }

}
