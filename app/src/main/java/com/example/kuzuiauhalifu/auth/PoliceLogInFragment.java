package com.example.kuzuiauhalifu.auth;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuzuiauhalifu.AuthActivity;
import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.police.PoliceActivity;
import com.example.kuzuiauhalifu.util.PrefManager;
import com.example.kuzuiauhalifu.util.Util;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoliceLogInFragment extends Fragment {

    Util util;
    PrefManager prefManager;
    EditText username, password;

    public PoliceLogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_police_log_in, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_police_log);
        username = (EditText)view.findViewById(R.id.username_police_log);
        password = (EditText)view.findViewById(R.id.password_police_log);

        util = new Util();
        prefManager = new PrefManager(getContext());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCreds(username.getText().toString(), password.getText().toString());
            }
        });

        return view;
    }

    public void checkCreds(String username, String password){
        String network_address = util.getIpAddress() + "police_log.php?username=" + username + "&password=" + password;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!Objects.equals(response, "0")) {
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            prefManager.setUserId(response);
                            Intent intent = new Intent(getContext(), PoliceActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Username or password is wrong", Toast.LENGTH_LONG).show();
                        }
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
