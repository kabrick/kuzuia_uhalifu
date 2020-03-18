package com.example.kuzuiauhalifu.auth;


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
import com.example.kuzuiauhalifu.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContinueCitizenSignUpFragment extends Fragment {

    EditText name, username, password;
    Util util;


    public ContinueCitizenSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_continue_citizen_sign_up, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_citizen_sign_up_complete);
        name = (EditText)view.findViewById(R.id.full_name_citizen);
        username = (EditText)view.findViewById(R.id.username_citizen_sign);
        password = (EditText)view.findViewById(R.id.password_citizen_sign);

        util = new Util();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCitizen(username.getText().toString(), password.getText().toString(), name.getText().toString(), ((AuthActivity) getActivity()).getPhone());
                ((AuthActivity) getActivity()).citizenLogIn(view);
            }
        });

        return view;
    }

    public void insertCitizen(String username, String password, String name, String phone){
        String network_address = util.getIpAddress() + "signup_citizen.php?username=" + username + "&password=" + password + "&name=" + name + "&phone=" + phone;

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
