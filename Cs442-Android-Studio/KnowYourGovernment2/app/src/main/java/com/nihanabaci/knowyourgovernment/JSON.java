package com.nihanabaci.knowyourgovernment;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.widget.Toast;


public class JSON extends AsyncTask<String, Integer, String> {


    private MainActivity mainActivity;



    private static  String DATA_URL = "";

    private static final String TAG = "AsyncCountryLoader";

    public static String city = "";
    public static String state = "";
    public static String zip = "";


    public JSON(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected void onPreExecute() {
        //Toast.makeText(mainActivity, "Loading Country Data...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(String s) {
        ArrayList<Official> OList = parseJSON(s);
      //  Log.d("TAG", "YOO");
        if (OList != null)
            //Toast.makeText(mainActivity, "Loaded " + OList.size() + " countries.", Toast.LENGTH_SHORT).show();
        mainActivity.updateData(OList);
    }


    @Override
    protected String doInBackground(String... params) {
        DATA_URL = "https://www.googleapis.com/civicinfo/v2/representatives?key=" + MainActivity.API + "&address=" + MainActivity.zipcode;

        Uri dataUri = Uri.parse(DATA_URL);
        String urlToUse = dataUri.toString();
        //Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Log.d(TAG, "doInBackground: ResponseCode: " + conn.getResponseCode());

            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

           // Log.d(TAG, "doInBackground: " + sb.toString());

        } catch (Exception e) {
          //  Log.e(TAG, "doInBackground: ", e);
            return null;
        }

        //Log.d(TAG, "doInBackground: " + sb.toString());

        return sb.toString();
    }


    private ArrayList<Official> parseJSON(String s) {

        ArrayList<Official> countryList = new ArrayList<>();
        Official o = null;
        try {


            JSONObject obj = new JSONObject(s); //jsonarray
            city = obj.getJSONObject("normalizedInput").getString("city");
            state = obj.getJSONObject("normalizedInput").getString("state");
            zip = obj.getJSONObject("normalizedInput").getString("zip");


            mainActivity.add = city + ", " + state + ", " + zip;
            JSONArray offices = obj.getJSONArray("offices");
            JSONArray officials = obj.getJSONArray("officials");





            for (int i = 0; i < offices.length(); i++)
            {


                String nm = officials.getJSONObject(i).getString("name");
                String off = offices.getJSONObject(i).getString("name");
                String part= "";
                String line1= "";
                String line2= "";
                String line3= "";
                String city1="";
                String state1="";
                String zip1="";
                String fullA= " ";
                String phone=" ";
                String email = " ";
                String website= " ";
                String account = " ";
                String twitter ;
                String facebook ;
                String youtube ;
                String google;
                String image;



                if(officials.getJSONObject(i).has("party")){
                     part = officials.getJSONObject(i).getString("party");
                }
                else
                {
                    part = "Unknown";
                }

                String parenParty = "(" + part + ")";






                if(officials.getJSONObject(i).has("address"))
                {
                    JSONArray adArray = new JSONArray(officials.getJSONObject(i).getString("address"));
                    for (int j = 0; j < adArray.length(); j++) {
                        if(adArray.getJSONObject(j).has("line1"))
                        {
                            line1 = adArray.getJSONObject(j).getString("line1");
                        }
                        if(adArray.getJSONObject(j).has("line2"))
                        {
                            line2 = adArray.getJSONObject(j).getString("line2");
                        }
                        if(adArray.getJSONObject(j).has("line3"))
                        {
                            line3 = adArray.getJSONObject(j).getString("line3");
                        }
                        city1 = adArray.getJSONObject(0).getString("city");
                        state1 = adArray.getJSONObject(0).getString("state");
                        zip1 = adArray.getJSONObject(0).getString("zip");

                        fullA = line1 + line2 + line3 + "\n" + city1 + " "+state1 +" " + zip1;


                    }
                }
                else
                {
                    fullA= "No Data Provided";
                }


                o = new Official(off, nm, parenParty);

                o.setAddress(fullA);






               if(officials.getJSONObject(i).has("phones"))
                {
                    JSONArray adArray1 = new JSONArray(officials.getJSONObject(i).getString("phones"));
                    if(adArray1.getString(0) != null) {

                        phone = adArray1.getString(0);
                    }
                    else
                    {
                        phone = "No Data Provided";
                    }

                }
                else
                {
                    phone= "No Data Provided";
                }

               o.setPhone(phone);




                if(officials.getJSONObject(i).has("emails"))
                {
                    JSONArray adArray2 = new JSONArray(officials.getJSONObject(i).getString("emails"));
                    if(adArray2.getString(0) != null) {

                        email = adArray2.getString(0);
                    }
                    else
                    {
                        email = "No Data Provided";
                    }

                }
                else
                {
                    email= "No Data Provided";
                }

                o.setEmail(email);



                if(officials.getJSONObject(i).has("urls"))
                {
                    JSONArray adArray3 = new JSONArray(officials.getJSONObject(i).getString("urls"));
                    if(adArray3.getString(0) != null) {

                        website = adArray3.getString(0);
                    }
                    else
                    {
                        website = "No Data Provided";
                    }

                }
                else
                {
                    website= "No Data Provided";
                }

                o.setWebsite(website);



                if(officials.getJSONObject(i).has("channels"))
                {
                    JSONArray adArray = new JSONArray(officials.getJSONObject(i).getString("channels"));
                    for (int j = 0; j < adArray.length(); j++) {
                        if(adArray.getJSONObject(j).has("type")) {
                            account = adArray.getJSONObject(j).getString("type");
                            if (account.equals("GooglePlus")) {
                                google = adArray.getJSONObject(j).getString("id");
                                o.setGoogle(google);
                            }
                            if (account.equals("Facebook")) {
                                facebook = adArray.getJSONObject(j).getString("id");
                                o.setFacebook(facebook);
                            }
                            if (account.equals("Twitter")) {
                                twitter = adArray.getJSONObject(j).getString("id");
                                o.setTwitter(twitter);
                            }
                            if (account.equals("YouTube")) {
                                youtube = adArray.getJSONObject(j).getString("id");
                                o.setYouTube(youtube);
                            }

                        }



                    }
                }


                if(officials.getJSONObject(i).has("photoUrl")){
                    image = officials.getJSONObject(i).getString("photoUrl");
                    o.setImage(image);
                }


                countryList.add(o);

            }

            return countryList;
        } catch (Exception e) {
            //Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}