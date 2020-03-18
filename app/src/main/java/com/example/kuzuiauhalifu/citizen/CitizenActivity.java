package com.example.kuzuiauhalifu.citizen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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

public class CitizenActivity extends AppCompatActivity {

    Util util;
    PrefManager prefManager;
    Spinner category;
    EditText location, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen);

        util = new Util();
        prefManager = new PrefManager(getApplicationContext());
    }

    public void reportCrime(final View view){

        //fire up the alert text box
        LayoutInflater inflater1 = LayoutInflater.from(this);
        View view1 = inflater1.inflate(R.layout.dialog_report_crime, null);

        category = (Spinner) view1.findViewById(R.id.spinner);
        location = (EditText) view1.findViewById(R.id.location_short);
        description = (EditText) view1.findViewById(R.id.crime_short_description);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Quick Crime Report");
        alertDialog.setView(view1);
        alertDialog.setCancelable(true);
        alertDialog.setIcon(R.drawable.warning_icon);

        alertDialog.setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                submitReport(category.getSelectedItem().toString(), location.getText().toString(), description.getText().toString());
                                dialog.cancel();
                                Snackbar.make(view, "Your report has been submitted", Snackbar.LENGTH_LONG).show();
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

    public void viewBulletin(View view){
        Intent intent = new Intent(CitizenActivity.this, CitizenBulletinActivity.class);
        startActivity(intent);
    }

    public void viewSettings(View view){
        Intent intent = new Intent(CitizenActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void viewHistory(View view){
        Intent intent = new Intent(CitizenActivity.this, CitizenHistoryActivity.class);
        startActivity(intent);
    }

    public void submitReport(String category, String location, String description){
        String user_id = prefManager.getUserId();
        String network_address = util.getIpAddress() +
                "record_new_report.php?category=" + category +
                "&location=" + location +
                "&description=" + description +
                "&user_id=" + user_id;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(CitizenActivity.this);

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CitizenActivity.this, "Thank you for your vigilance", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });

        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this, "Action is not allowed", Toast.LENGTH_LONG).show();
    }
}
