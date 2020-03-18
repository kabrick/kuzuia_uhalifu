package com.example.kuzuiauhalifu.police;

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
import android.widget.Spinner;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PoliceViewBulletinActivity extends AppCompatActivity {

    String php_file = "get_single_bulletin.php";
    Util util;
    TextView category, description, location, date, status;
    Spinner spinner;
    JsonArrayRequest jsonArrayRequest ;
    List<String> tips = new ArrayList<>();
    RequestQueue requestQueue ;
    String id;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_tip_bulletin_police:
                    viewTips();
                    return true;
                case R.id.action_update_bulletin_police:
                    updateStatus();
                    return true;
                case R.id.action_delete_bulletin_police:
                    deleteEntry();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_view_bulletin);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_dashboard_bulletin_police);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        category = (TextView)findViewById(R.id.categoryPoliceBulletin);
        description = (TextView)findViewById(R.id.descriptionPoliceBulletin);
        location = (TextView)findViewById(R.id.locationPoliceBulletin);
        date = (TextView)findViewById(R.id.datePoliceBulletin);
        status = (TextView)findViewById(R.id.statusPoliceBulletin);

        //Receiving the id value send by previous activity.
        id = getIntent().getStringExtra("entry_id");

        util = new Util();

        //call function to get strings and display
        getNetworkString(php_file, id, "1");
        getNetworkString(php_file, id, "2");
        getNetworkString(php_file, id, "3");
        getNetworkString(php_file, id, "4");
        getNetworkString(php_file, id, "5");
    }

    public void getNetworkString(String url, String id, final String cat){

        String network_address = util.getIpAddress() + url + "?id=" + id + "&type=" + cat;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(PoliceViewBulletinActivity.this);

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
                    Toast.makeText(PoliceViewBulletinActivity.this,"Connection Error. Please check your connection",Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(PoliceViewBulletinActivity.this,"Authentication error",Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(PoliceViewBulletinActivity.this,"Server error",Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(PoliceViewBulletinActivity.this,"Network error",Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(PoliceViewBulletinActivity.this,"Data from server not Available",Toast.LENGTH_LONG).show();
                }
            }
        });

        queue.add(request);
    }

    public void viewTips(){

        String HTTP_SERVER_URL = util.getIpAddress() + "get_tips.php?id=" + id;

        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        addItemToList(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void addItemToList(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            JSONObject json;
            try {
                json = array.getJSONObject(i);
                tips.add(json.getString("tip"));
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        String[] simpleArray = new String[ tips.size() ];
        tips.toArray(simpleArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(PoliceViewBulletinActivity.this);
        builder.setTitle("Tips for this crime");

        builder.setItems(simpleArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteEntry(){
        String network_address = util.getIpAddress() + "delete_bulletin.php?id=" + id;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(PoliceViewBulletinActivity.this);

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PoliceViewBulletinActivity.this, "Entry has been deleted", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PoliceViewBulletinActivity.this, PoliceActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });

        queue.add(request);
    }

    public void updateStatus(){
        //fire up the alert text box
        LayoutInflater inflater1 = LayoutInflater.from(this);
        View view1 = inflater1.inflate(R.layout.dialog_update_status, null);

        spinner = (Spinner) view1.findViewById(R.id.spinner_update);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Update Status");
        alertDialog.setView(view1);
        alertDialog.setCancelable(true);
        alertDialog.setIcon(R.drawable.tip);

        alertDialog.setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                sendStatusUpdate(spinner.getSelectedItem().toString());
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

    public void sendStatusUpdate(String status){
        String network_address = util.getIpAddress() + "update_status.php?id=" + id + "&status=" + status;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(PoliceViewBulletinActivity.this);

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PoliceViewBulletinActivity.this, "Entry has been updated", Toast.LENGTH_LONG).show();
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
