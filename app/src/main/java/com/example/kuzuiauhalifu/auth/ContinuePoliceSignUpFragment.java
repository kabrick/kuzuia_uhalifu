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
public class ContinuePoliceSignUpFragment extends Fragment {


    public ContinuePoliceSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_continue_police_sign_up, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_police_sign_up_complete);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get all details
                //save all details
                //delete all prefs
                //go to log in
                ((AuthActivity) getActivity()).PoliceLogIn(view);
            }
        });

        return view;
    }

}
