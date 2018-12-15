package com.nihanabaci.stockwatch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.ArrayList;

public class UpdatingData extends AsyncTask<ArrayList<stock>, Integer, ArrayList<stock>> {

    MainActivity context;

    UpdatingData(MainActivity context){
        this.context = context;
    }

    @Override
    protected ArrayList<stock> doInBackground(ArrayList<stock>... arrayLists) {
        new FetchingData(context).execute(new ArrayList<stock>());
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<stock> stocks) {
        super.onPostExecute(stocks);
        //context.fetchStocks();
    }
}
