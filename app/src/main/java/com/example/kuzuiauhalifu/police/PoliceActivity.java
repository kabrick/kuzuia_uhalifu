package com.example.kuzuiauhalifu.police;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kuzuiauhalifu.R;

public class PoliceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
    }

    public void viewIncomingReports(View view){
        Intent intent = new Intent(PoliceActivity.this, IncomingReportsActivity.class);
        startActivity(intent);
    }

    public void viewBulletin(View view){
        Intent intent = new Intent(PoliceActivity.this, PoliceBulletinActivity.class);
        startActivity(intent);
    }

    public void viewSettings(View view){
        Intent intent = new Intent(PoliceActivity.this, PoliceSettingsActivity.class);
        startActivity(intent);
    }
}
