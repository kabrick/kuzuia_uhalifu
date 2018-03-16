package com.example.kuzuiauhalifu.police;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.util.PrefManager;
import com.example.kuzuiauhalifu.util.Util;

public class PoliceSettingsActivity extends AppCompatActivity {

    ListView listView;
    Util util;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_settings);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.settings_list_police);

        util = new Util();
        prefManager = new PrefManager(getApplicationContext());

        // Defined Array values to show in ListView
        String[] values = new String[] { "Log out"};

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
                    PoliceSettingsActivity.this.finishAffinity();
                }
            }

        });
    }
}
