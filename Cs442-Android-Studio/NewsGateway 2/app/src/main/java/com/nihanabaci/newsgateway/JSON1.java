package com.nihanabaci.newsgateway;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;


public class JSON1 extends AsyncTask<String, Integer, String> {

    private MainActivity mainActivity;
    private static final String TAG = "AFJDKANGKJADMGKA";


    private static  String DATA_URL = "";




    public JSON1(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected void onPreExecute() {
        //Toast.makeText(mainActivity, "Loading Country Data...", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPostExecute(String s) {
        if(mainActivity.sign == "0")
        {
            ArrayList<News> NList = parseJSON(s);
            if(NList != null){mainActivity.updateData(NList);

            }


        }
        if(mainActivity.sign == "1")
        {


            ArrayList<Articles> AList = AparseJSON(s);
            Log.d(TAG, "STEP 2" + AList.isEmpty());
            if(AList != null){
                mainActivity.AupdateData(AList);
                }


        }
    }


    @Override
    protected String doInBackground(String... params) {
        DATA_URL = mainActivity.URL;

        Uri dataUri = Uri.parse(DATA_URL);
        String urlToUse = dataUri.toString();
        //Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }


        } catch (Exception e) {
            return null;
        }



        return sb.toString();
    }


    private ArrayList<News> parseJSON(String s) {

        ArrayList<News> NewsList = new ArrayList<>();

        Log.d(TAG, "PARSED");

        News o = null;
        try {


            JSONObject obj = new JSONObject(s); //jsonarray


                JSONArray sources = obj.getJSONArray("sources");
                for (int i = 0; i < sources.length(); i++) {


                    String id = sources.getJSONObject(i).getString("id");
                    String name = sources.getJSONObject(i).getString("name");
                    String category = sources.getJSONObject(i).getString("category");

                    o = new News(id, name, category);


                    NewsList.add(o);



            }


            return NewsList;

        } catch (Exception e) {
            //Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<Articles> AparseJSON(String s) {


        ArrayList<Articles> ArticleList = new ArrayList<>();

        String author= "";
        String title= "";
        String description= "";
        String pDate = "";
        String urlToImage = "";
        String url = "";

        Articles o = null;
        try {


            JSONObject obj = new JSONObject(s); //jsonarray

                JSONArray articles = obj.getJSONArray("articles");
                for (int i = 0; i < articles.length(); i++)
                {

                    author = articles.getJSONObject(i).getString("author");
                    title = articles.getJSONObject(i).getString("title");
                    description = articles.getJSONObject(i).getString("description");
                    pDate = articles.getJSONObject(i).getString("publishedAt");
                    urlToImage = articles.getJSONObject(i).getString("urlToImage");
                    url = articles.getJSONObject(i).getString("url");


                    String formattedDate =" ";


                    try {
                        if(pDate.contains("+"))
                        {
                             pDate = pDate.split("+")[0];
                        }
                        if(pDate.contains("."))
                        {
                            pDate = pDate.split(".")[0];
                        }

                        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        Log.d(TAG, "DATE  : " + pDate);
                        DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                        Date date = originalFormat.parse(pDate);
                        formattedDate = targetFormat.format(date);
                        Log.d(TAG, "NEW DATE   : " + formattedDate);

                        System.out.println(formattedDate);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }





                    o= new Articles(author, title, description, formattedDate, urlToImage, url);

                    ArticleList.add(o);


                }




            return ArticleList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
