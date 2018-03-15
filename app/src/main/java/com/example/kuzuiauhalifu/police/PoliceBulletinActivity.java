package com.example.kuzuiauhalifu.police;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.kuzuiauhalifu.R;
import com.example.kuzuiauhalifu.adapters.BulletinDataAdapter;
import com.example.kuzuiauhalifu.util.Util;

import java.util.ArrayList;
import java.util.List;

public class PoliceBulletinActivity extends AppCompatActivity {

    List<BulletinDataAdapter> DataAdapterClassList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayRequest ;
    ArrayList<Integer> bulletinIds;
    RequestQueue requestQueue ;
    String php_file = "get_bulletin.php";
    Util util;
    String network_address;
    View ChildView ;
    int RecyclerViewClickedItemPOS;
    String clickedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_bulletin);
    }
}
