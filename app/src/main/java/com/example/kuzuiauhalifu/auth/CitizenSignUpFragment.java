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

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitizenSignUpFragment extends Fragment {

    EditText phone;
    Util util;

    public CitizenSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_citizen_sign_up, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_citizen_sign_up_continue);
        phone = (EditText) view.findViewById(R.id.phone);
        util = new Util();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhone(phone.getText().toString());
            }
        });
        return view;
    }

    public void verifyPhone(final String phone){
        String network_address = util.getIpAddress() + "verify_phone.php?phone=" + phone;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!Objects.equals(response, "1")) {
                            ((AuthActivity) getActivity()).continueCitizenSignUp(phone);
                        } else {
                            Toast.makeText(getContext(), "Phone number already exists", Toast.LENGTH_LONG).show();
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
