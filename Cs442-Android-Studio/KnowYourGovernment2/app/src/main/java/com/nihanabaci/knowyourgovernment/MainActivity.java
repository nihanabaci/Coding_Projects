package com.nihanabaci.knowyourgovernment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo;
import java.lang.Boolean;


//ADD LONG CLICK LISTENER
//dialog -
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{




    public static final String TAG = "MyActivity";

    private ArrayList<Official> OfficialList = new ArrayList<>();  // Main content is here

    private RecyclerView recyclerView; // Layout's recyclerview

    private Adapter mAdapter; // Data to recyclerview adapter

    private Locator locator;

    private String address;

    public static String zipcode;

    private static final int EDIT_ACT = 1;

    public static String add;

    public static boolean read = false;



    public static String API = "AIzaSyCkXi-Kpq9H6VdpWM3eFINcQKHGn7oJo_I";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locator = new Locator(this);

        if(!doNetCheck()){
            AlertDialog.Builder noneFound = new AlertDialog.Builder(this);
            noneFound.setTitle("Not Connected to network");
            noneFound.show();
        }
        new JSON(this).execute();
        initR(OfficialList);
    }

    public  void initR(ArrayList<Official> o){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new Adapter(o, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



    public void setData(double lat, double lon) {

        if(this.read == false)
        {
            Log.d(TAG, "setData: Lat: " + lat + ", Lon: " + lon);
            address = doAddress(lat, lon);
            ((TextView) findViewById(R.id.address)).setText(address);
            MainActivity.read = true;
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 5) {
            Log.d(TAG, "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }

    private String doAddress(double latitude, double longitude) {


        Geocoder mGeocoder = new Geocoder(this, Locale.getDefault());
        zipcode = null;
        Address address = null;
        String city="";
        String state="";
        String add = "";

        try {

            if (mGeocoder != null) {

                List<Address> addresses = mGeocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null && addresses.size() > 0) {

                    for (int i = 0; i < addresses.size(); i++) {
                        address = addresses.get(i);
                        if (address!= null) {
                            zipcode = address.getPostalCode();
                            city = address.getLocality();
                            state = address.getAdminArea();
                            Log.d(TAG, "Postal code: " + address.getPostalCode());
                            add = city + ", " + state + ", " + zipcode;
                            break;
                        }


                    }
                    return add;
                }
            }
        }
        catch (IOException e) {
            Log.d(TAG, "doAddress: " + e.getMessage());

        }

        return null;


    }

    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        locator.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        //mAdapter.notifyDataSetChanged();

        new JSON(this).execute("https://www.googleapis.com/civicinfo/v2/representatives?key=" + API +  "&address=" + this.address);

        //String watch= "https://www.marketwatch.com/tools/quotes/lookup.asp?lookup=some_stock" + stk.getSymbol();
        //execute this
        super.onResume();
    }





    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Official m = OfficialList.get(pos);

        Intent intentEdit = new Intent(MainActivity.this, OfficialPage.class);
        intentEdit.putExtra("OFFICIAL", m.getOfficial());
        intentEdit.putExtra("NAME", m.getName());
        intentEdit.putExtra("PARTY", m.getROrD());
        intentEdit.putExtra("ADD", m.getAddress());
        intentEdit.putExtra("PHONE", m.getPhone());
        intentEdit.putExtra("EMAIL", m.getEmail());
        intentEdit.putExtra("WEBSITE", m.getWebsite());
        intentEdit.putExtra("GOOGLE", m.getGoogle());
        intentEdit.putExtra("FACEBOOK", m.getFacebook());
        intentEdit.putExtra("TWITTER", m.getTwitter());
        intentEdit.putExtra("YOUTUBE", m.getYoutube());
        intentEdit.putExtra("IMAGE", m.getImage());






        String s=((TextView) findViewById(R.id.address)).getText().toString();

        intentEdit.putExtra("ADDRESS", s);

        //intentEdit.putExtra("NEW_NOTE", "false");
        startActivityForResult(intentEdit, EDIT_ACT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: ");

        switch (item.getItemId()) {
            case R.id.info:
                Intent intentAbt = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAbt);
            case R.id.search:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final EditText et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(et);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String y = et.getText().toString();
                        if(doNetCheck())
                        {

                            OfficialList.clear();
                            MainActivity.zipcode = y;
                            JSON jh = new JSON(MainActivity.this);
                            jh.execute();

                            ((TextView) findViewById(R.id.address)).setText(add);




                        }





                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                builder.setTitle("Enter a City, State or a Zip Code");



                AlertDialog dialog = builder.create();
                dialog.show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void updateData(ArrayList<Official> cList) {
        OfficialList.clear();
        OfficialList.addAll(cList);
        mAdapter.notifyDataSetChanged();
    }

    private Boolean doNetCheck() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            Toast.makeText(this, "Cannot access ConnectivityManager", Toast.LENGTH_SHORT).show();
            return false;
        }
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return  true;
        } else {
            return  false;
        }
    }

}
