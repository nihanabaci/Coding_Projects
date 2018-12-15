package com.nihanabaci.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.graphics.*;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "Adapter";
    private List<Official> OfficialList;
    private MainActivity mainAct;

    public Adapter(List<Official> empList, MainActivity ma) {
        this.OfficialList = empList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.official_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        //itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Official off = OfficialList.get(position);

        holder.official.setText(off.getOfficial());
        holder.name.setText(off.getName());
        holder.RorD.setText(off.getROrD());
    }

    @Override
    public int getItemCount() {
        return OfficialList.size();
    }
}
