package com.example.kuzuiauhalifu.citizen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.util.PrefManager;
import com.example.kuzuiauhalifu.util.Util;

public class CitizenViewBulletinActivity extends AppCompatActivity {

    String php_file = "get_single_bulletin.php";
    Util util;
    PrefManager prefManager;
    String network_address;
    TextView category, description, location, date, status;
    EditText editText;
    String id;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_tip_bulletin:
                    giveTip();
                    return true;
                case R.id.action_share_bulletin:
                    shareEntry();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_view_bulletin);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_dashboard_bulletin);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        category = (TextView)findViewById(R.id.categoryCitizenBulletin);
        description = (TextView)findViewById(R.id.descriptionCitizenBulletin);
        location = (TextView)findViewById(R.id.locationCitizenBulletin);
        date = (TextView)findViewById(R.id.dateCitizenBulletin);
        status = (TextView)findViewById(R.id.statusCitizenBulletin);

        //Receiving the id value send by previous activity.
        id = getIntent().getStringExtra("entry_id");

        util = new Util();
        prefManager = new PrefManager(getApplicationContext());

        //call function to get strings and display
        getNetworkString(php_file, id, "1");
        getNetworkString(php_file, id, "2");
        getNetworkString(php_file, id, "3");
        getNetworkString(php_file, id, "4");
        getNetworkString(php_file, id, "5");

        network_address = util.getIpAddress() + php_file + "?id=" + id;
    }

    public void getNetworkString(String url, String id, final String cat){

        String network_address = util.getIpAddress() + url + "?id=" + id + "&type=" + cat;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(CitizenViewBulletinActivity.this);

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (cat){
                            case "1":
                                category.setText(response);
                                break;
                            case "2":
                                date.setText(response);
                                break;
                            case "3":
                                status.setText(response);
                                break;
                            case "4":
                                location.setText(response);
                                break;
                            case "5":
                                description.setText(response);
                                break;
                        }
                        //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(CitizenViewBulletinActivity.this,"Connection Error. Please check your connection",Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(CitizenViewBulletinActivity.this,"Authentication error",Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(CitizenViewBulletinActivity.this,"Server error",Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CitizenViewBulletinActivity.this,"Network error",Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(CitizenViewBulletinActivity.this,"Data from server not Available",Toast.LENGTH_LONG).show();
                }
            }
        });

        queue.add(request);
    }

    public void giveTip(){
        //fire up the alert text box
        LayoutInflater inflater1 = LayoutInflater.from(this);
        View view1 = inflater1.inflate(R.layout.dialog_submit_tip, null);

        editText = (EditText)view1.findViewById(R.id.tip);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Submit a Tip");
        alertDialog.setView(view1);
        alertDialog.setCancelable(true);
        alertDialog.setIcon(R.drawable.tip);

        alertDialog.setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                sendTip(editText.getText().toString());
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog1 = alertDialog.create();

        alertDialog1.show();
    }

    public void shareEntry(){
        String text = "Hello from the Wulinzi app. Please note that a crime of type "
                + category.getText().toString() + " occurred in " + location.getText().toString()
                + " described as " + description.getText().toString() + ". Please be alerted";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void sendTip(String tip){
        String user_id = prefManager.getUserId();
        String network_address = util.getIpAddress() + "record_tip.php?id=" + id + "&tip=" + tip + "&user_id=" + user_id;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(CitizenViewBulletinActivity.this);

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CitizenViewBulletinActivity.this, "Your tip has been recorded", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });

        queue.add(request);
    }
}
