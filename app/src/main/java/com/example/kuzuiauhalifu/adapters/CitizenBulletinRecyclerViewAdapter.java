package com.example.kuzuiauhalifu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuzuiauhalifu.R;

import java.util.List;

public class CitizenBulletinRecyclerViewAdapter extends RecyclerView.Adapter<CitizenBulletinRecyclerViewAdapter.ViewHolder> {
    Context context;

    List<BulletinDataAdapter> dataAdapters;

    public CitizenBulletinRecyclerViewAdapter(List<BulletinDataAdapter> getDataAdapter, Context context){

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bulletincardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        BulletinDataAdapter dataAdapter =  dataAdapters.get(position);

        viewHolder.category.setText(dataAdapter.getCategory());

        viewHolder.location.setText(dataAdapter.getLocation());

        viewHolder.date.setText(dataAdapter.getDate());

        viewHolder.status.setText(dataAdapter.getStatus());

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView category;
        TextView location;
        TextView date;
        TextView status;


        ViewHolder(View itemView) {

            super(itemView);

            category = (TextView) itemView.findViewById(R.id.categoryBulletin) ;
            location = (TextView) itemView.findViewById(R.id.locationBulletin) ;
            date = (TextView) itemView.findViewById(R.id.dateBulletin) ;
            status = (TextView) itemView.findViewById(R.id.statusBulletin) ;


        }
    }
}
