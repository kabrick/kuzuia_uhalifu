package com.example.kuzuiauhalifu.auth;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuzuiauhalifu.AuthActivity;
import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.util.Util;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContinuePoliceSignUpFragment extends Fragment {

    TextView tv;
    FloatingActionButton fab;
    EditText username, password;
    Util util;


    public ContinuePoliceSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_continue_police_sign_up, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_police_sign_up_complete);
        tv = (TextView) view.findViewById(R.id.officer_name);
        tv.setText(((AuthActivity) getActivity()).getName());

        util = new Util();

        username = (EditText) view.findViewById(R.id.username_police_sign);
        password = (EditText) view.findViewById(R.id.password_police_sign);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPolice(username.getText().toString(), password.getText().toString());
                ((AuthActivity) getActivity()).PoliceLogIn(view);
            }
        });

        return view;
    }

    public void insertPolice(String username, String password){
        String network_address = util.getIpAddress() + "signup_police.php?username=" + username + "&password=" + password;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //
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
