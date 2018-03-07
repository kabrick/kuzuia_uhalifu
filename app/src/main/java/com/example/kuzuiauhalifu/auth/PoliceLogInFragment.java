package com.example.kuzuiauhalifu.auth;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.police.PoliceActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoliceLogInFragment extends Fragment {


    public PoliceLogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_police_log_in, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_police_log);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get username and password
                //send variables to network and confirm
                //if okay, go to citizen view
                Intent intent = new Intent(getContext(), PoliceActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
