package com.nihanabaci.stockwatch;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncStockLoader extends AsyncTask<String, String, String> {

    private MainActivity mainActivity;

    private static final String DATA_URL = "https://restcountries.eu/rest/v1/all";
    private static final String TAG = "AsyncCountryLoader";

    public AsyncStockLoader(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mainActivity, "Loading Country Data...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(String s) {
        ArrayList<stock> stockList = parseJSON(s);
        if (stockList != null)
            Toast.makeText(mainActivity, "Loaded " + stockList.size() + " countries.", Toast.LENGTH_SHORT).show();
        mainActivity.updateData(stockList);
    }


    @Override
    protected String doInBackground(String... params) {

        Uri dataUri = Uri.parse(params[0]);
        String urlToUse = dataUri.toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Log.d(TAG, "doInBackground: ResponseCode: " + conn.getResponseCode());

            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "doInBackground: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            return null;
        }

        Log.d(TAG, "doInBackground: " + sb.toString());

        return sb.toString();
    }


    private ArrayList<stock> parseJSON(String s) {

        ArrayList<stock> stockList = new ArrayList<>();
        try {
            JSONArray jObjMain = new JSONArray(s);

            for (int i = 0; i < jObjMain.length(); i++) {
                JSONObject jCountry = (JSONObject) jObjMain.get(i);
                String symbol = jCountry.getString("symbol");
                String name = jCountry.getString("name");
                double price = 0.0;
                double change = 0.0;
                double percentage = 0.0;
                String id= jCountry.getString("iexId");

                stockList.add(
                        new stock(symbol, name, price, change, percentage, id));

            }
            return stockList;
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
