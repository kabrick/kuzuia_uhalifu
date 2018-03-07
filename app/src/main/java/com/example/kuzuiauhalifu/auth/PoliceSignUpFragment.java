package com.example.kuzuiauhalifu.auth;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuzuiauhalifu.AuthActivity;
import com.example.kuzuiauhalifu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoliceSignUpFragment extends Fragment {


    public PoliceSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_police_sign_up, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_police_sign_up_continue);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the phone number entered
                //verify it in database
                //save phone to a pref function
                //go to next page
                ((AuthActivity) getActivity()).continuePoliceSignUp();
            }
        });

        return view;
    }

}
