package com.e.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class FarmerRegistration extends AppCompatActivity {

    Button save;
    String ServerURL = "https://learnfriendly.000webhostapp.com/farmer.php";
    EditText name=null, phone=null,aadhar=null,password=null,address=null;
    Button button;
    Button register;
    String TempName, TempPhone, TempAadhar,TempPassword,TempAddress;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_registration);
        register = findViewById(R.id.register);
        name = findViewById(R.id.name);
        phone  = findViewById(R.id.phone);
        aadhar = findViewById(R.id.aadhar);
        password = findViewById(R.id.pwd);
        address = findViewById(R.id.address);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();

                InsertData(TempName,TempPhone,TempAadhar,TempPassword,TempAddress);

            }
        });
    }
    public void GetData(){

        TempName = name.getText().toString();
        TempPhone = phone.getText().toString();
        TempAadhar = aadhar.getText().toString();
        TempPassword = password.getText().toString();
        TempAddress = address.getText().toString();

          }
    public void InsertData(final String name, final String phone, final  String aadhar,final String password,final String address){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String ItemHolder = name ;
                String QuantityHolder = phone ;
                String PriceHolder = aadhar;
                String PassHolder = password;
                String AddressHolder  = address;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name",ItemHolder));
                nameValuePairs.add(new BasicNameValuePair("phone",QuantityHolder));
                nameValuePairs.add(new BasicNameValuePair("aadhar",PriceHolder));
                nameValuePairs.add(new BasicNameValuePair("password",PassHolder));
                nameValuePairs.add(new BasicNameValuePair("address",AddressHolder));
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressDialog.show(FarmerRegistration.this, "Just a minute","Wait....", true);
            }
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                progressDialog.dismiss();
                Toast.makeText(FarmerRegistration.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(order1.this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name,phone,aadhar,password,address);
    }
}
