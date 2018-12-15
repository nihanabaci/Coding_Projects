package com.nihanabaci.stockwatch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchingData extends AsyncTask<ArrayList<stock>, Void, ArrayList<stock>> {

    private ArrayList<stock> stocksList;
    MainActivity context;
    private static final String TAG ="FetchingData";

    FetchingData(MainActivity context){
        this.context = context;
    }

    @Override
    protected ArrayList<stock> doInBackground(ArrayList<stock>... arrayLists) {
        String json="";
        try {
            for(int i = 0; i < arrayLists[0].size(); i++){
                URL url = new URL("https://api.iextrading.com/1.0/stock/"+arrayLists[0].get(i).getSymbol()+"/quote?displayPercent=true");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
                String line;
                while ((line = reader.readLine()) != null) {
                    json = line;
                }
                try{
                    JSONObject jStock= new JSONObject(json);
                    double price ;
                    double change;
                    double percentage ;

                    price = jStock.getDouble("latestPrice");
                    change = jStock.getDouble("change");
                    percentage = jStock.getDouble("changePercent");

                    arrayLists[0].get(i).setPrice(price);
                    arrayLists[0].get(i).setChange(change);
                    arrayLists[0].get(i).setPercentage(percentage);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        } catch (Exception e){
            return null;
        }
        return arrayLists[0];
    }

    @Override
    protected void onPostExecute(ArrayList<stock> stocks) {
        //Log.d("ONPOSTEXECUTE", stocks + "");
        context.onResume();
        super.onPostExecute(stocks);
    }
}
