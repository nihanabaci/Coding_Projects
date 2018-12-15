package com.nihanabaci.stockwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.*;



import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private List<stock> stockList;
    private MainActivity mainAct;

    public Adapter(List<stock> empList, MainActivity ma) {
        this.stockList = empList;
        mainAct = ma;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        stock stk = stockList.get(position);
        String color;
        String arrow;

        if(stk.getChange() < 0)
        {
            color = "#FF0000";
            arrow = "";
        }
        else
        {
            color = "#00FF00";
            arrow  = " ";
        }

        holder.symbol.setText(stk.getSymbol());
        holder.name.setText(stk.getName());
        holder.price.setText(String.valueOf(stk.getPrice()));
        holder.priceChange.setText(String.valueOf(stk.getChange()));
        holder.percentage.setText((String.valueOf(stk.getPercentage())));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

}


