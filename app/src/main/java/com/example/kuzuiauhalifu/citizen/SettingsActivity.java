package com.example.kuzuiauhalifu.citizen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.util.PrefManager;
import com.example.kuzuiauhalifu.util.Util;

public class SettingsActivity extends AppCompatActivity {

    ListView listView;
    Util util;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.settings_list_citizens);
        util = new Util();
        prefManager = new PrefManager(getApplicationContext());

        // Defined Array values to show in ListView
        String[] values = new String[] { "Log out","Clear my history"};

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0){
                    prefManager.deleteUserId();
                    SettingsActivity.this.finishAffinity();
                }
                else if (position == 1){
                    deleteHistory();
                }
            }

        });
    }

    public void deleteHistory(){
        String user_id = prefManager.getUserId();

        String network_address = util.getIpAddress() + "delete_history.php?id=" + user_id;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SettingsActivity.this, "Your history has been deleted", Toast.LENGTH_LONG).show();
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
