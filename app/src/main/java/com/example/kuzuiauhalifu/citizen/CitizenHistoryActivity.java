package com.example.kuzuiauhalifu.citizen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.adapters.BulletinDataAdapter;
import com.example.kuzuiauhalifu.adapters.CitizenBulletinRecyclerViewAdapter;
import com.example.kuzuiauhalifu.util.PrefManager;
import com.example.kuzuiauhalifu.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CitizenHistoryActivity extends AppCompatActivity {

    List<BulletinDataAdapter> DataAdapterClassList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayRequest ;
    ArrayList<Integer> bulletinIds;
    RequestQueue requestQueue ;
    String php_file = "get_history_citizen.php";
    Util util;
    PrefManager prefManager;
    String user_id;
    String network_address;
    View ChildView ;
    int RecyclerViewClickedItemPOS ;
    String clickedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_history);

        DataAdapterClassList = new ArrayList<>();

        bulletinIds = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCitizenHistory);

        progressBar = (ProgressBar) findViewById(R.id.progressBarCitizenHistory);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        util = new Util();
        prefManager = new PrefManager(getApplicationContext());

        user_id = prefManager.getUserId();

        network_address = util.getIpAddress() + php_file + "?id=" + user_id;

        // JSON data web call function call from here.
        JSON_WEB_CALL(network_address);

        //RecyclerView Item click listener code starts from here.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(CitizenHistoryActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked item value.
                    RecyclerViewClickedItemPOS = Recyclerview.getChildAdapterPosition(ChildView);

                    clickedItemId = bulletinIds.get(RecyclerViewClickedItemPOS).toString();

                    Intent intent = new Intent(CitizenHistoryActivity.this, CitizenViewHistoryActivity.class);
                    intent.putExtra("entry_id", clickedItemId);
                    startActivity(intent);

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    public void JSON_WEB_CALL(String HTTP_SERVER_URL){

        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(CitizenHistoryActivity.this,"Connection Error. Please check your connection",Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(CitizenHistoryActivity.this,"Authentication error",Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(CitizenHistoryActivity.this,"Server error",Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(CitizenHistoryActivity.this,"Network error",Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(CitizenHistoryActivity.this,"Data from server not Available",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            BulletinDataAdapter GetDataAdapter2 = new BulletinDataAdapter();

            JSONObject json;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setId(json.getInt("id"));
                GetDataAdapter2.setCategory(json.getString("category"));
                GetDataAdapter2.setStatus(json.getString("status"));
                GetDataAdapter2.setLocation(json.getString("location"));
                GetDataAdapter2.setDate(json.getString("date"));

                //Adding id here to show on click event.
                bulletinIds.add(json.getInt("id"));

            }
            catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(CitizenHistoryActivity.this,
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        progressBar.setVisibility(View.GONE);

        recyclerViewadapter = new CitizenBulletinRecyclerViewAdapter(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}
