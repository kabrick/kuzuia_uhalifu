package com.example.kuzuiauhalifu.citizen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.kuzuiauhalifu.R;

public class CitizenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen);
    }

    public void reportCrime(final View view){

        //fire up the alert text box
        LayoutInflater inflater1 = LayoutInflater.from(getApplicationContext());
        View view1 = inflater1.inflate(R.layout.dialog_report_crime, null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.setTitle("Quick Crime Report");
        alertDialog.setView(view1);

        alertDialog.setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //get all user inputs plus location
                                //confirm that they exist
                                //if ready submit and close the dialog with snackbar
                                dialog.cancel();
                                Snackbar.make(view, "", Snackbar.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("More Details",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //head to the more details page
                                Intent intent = new Intent(CitizenActivity.this, MoreCrimeDetailsActivity.class);
                                startActivity(intent);
                            }
                        });


        AlertDialog alertDialog1 = alertDialog.create();

        alertDialog1.show();
    }

    public void viewBulletin(View view){}

    public void viewSettings(View view){}

    public void viewHistory(View view){}
}
