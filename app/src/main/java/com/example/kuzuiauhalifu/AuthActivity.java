package com.example.kuzuiauhalifu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kuzuiauhalifu.auth.CitizenLogInFragment;
import com.example.kuzuiauhalifu.auth.CitizenSignUpFragment;
import com.example.kuzuiauhalifu.auth.ContinueCitizenSignUpFragment;
import com.example.kuzuiauhalifu.auth.ContinuePoliceSignUpFragment;
import com.example.kuzuiauhalifu.auth.PoliceLogInFragment;
import com.example.kuzuiauhalifu.auth.PoliceSignUpFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        CitizenLogInFragment fragment = new CitizenLogInFragment();
        displayFragment(fragment);
    }

    public void registerPolice(View view){
        PoliceSignUpFragment fragment = new PoliceSignUpFragment();
        displayFragment(fragment);
    }

    public void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.auth_frame,
                fragment).addToBackStack(null).commit();
    }

    public void PoliceLogIn(View view){
        PoliceLogInFragment fragment = new PoliceLogInFragment();
        displayFragment(fragment);
    }

    public void registerCitizen(View view){
        CitizenSignUpFragment fragment = new CitizenSignUpFragment();
        displayFragment(fragment);
    }

    public void citizenLogIn(View view){
        CitizenLogInFragment fragment = new CitizenLogInFragment();
        displayFragment(fragment);
    }

    public void continueCitizenSignUp(){
        ContinueCitizenSignUpFragment fragment = new ContinueCitizenSignUpFragment();
        displayFragment(fragment);
    }

    public void continuePoliceSignUp(){
        ContinuePoliceSignUpFragment fragment = new ContinuePoliceSignUpFragment();
        displayFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this, "Action is not allowed", Toast.LENGTH_LONG).show();
    }
}
