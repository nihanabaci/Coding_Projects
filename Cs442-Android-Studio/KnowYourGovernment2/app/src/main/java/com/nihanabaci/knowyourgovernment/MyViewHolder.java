package com.nihanabaci.knowyourgovernment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView official;
    public TextView name;
    public TextView RorD;

    public MyViewHolder(View view) {
        super(view);
        official = (TextView) view.findViewById(R.id.government_official);
        name = (TextView) view.findViewById(R.id.name);
        RorD = (TextView) view.findViewById(R.id.RorD);
    }
}
