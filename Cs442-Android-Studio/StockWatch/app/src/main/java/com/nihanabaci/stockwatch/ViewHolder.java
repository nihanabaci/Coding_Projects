package com.nihanabaci.stockwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView symbol;
        public TextView name;
        public TextView price;
        public TextView priceChange;
        public TextView percentage;


        public ViewHolder(View view) {
            super(view);
            symbol = (TextView) view.findViewById(R.id.stock_symbol);
            name = (TextView) view.findViewById(R.id.company_name);
            price = (TextView) view.findViewById(R.id.price);
            priceChange = (TextView) view.findViewById(R.id.price_change);
            percentage = (TextView) view.findViewById(R.id.percentage);
        }
    }


