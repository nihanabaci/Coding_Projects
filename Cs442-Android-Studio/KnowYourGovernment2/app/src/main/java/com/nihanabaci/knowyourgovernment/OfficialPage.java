package com.nihanabaci.knowyourgovernment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import 	android.graphics.Paint;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.Serializable;
import java.lang.Throwable;
import java.lang.Object;
import android.content.pm.PackageManager;
import java.nio.channels.Channels;
import com.squareup.picasso.Picasso;
import android.widget.TextView;



public class OfficialPage extends AppCompatActivity  implements Serializable
        //implements View.OnClickListener
        {
    public TextView official;
    public TextView name;
    public TextView RorD;
    public TextView addr;
    public TextView o_address;
    public TextView phone;
    public TextView email;
    public TextView website;

    public ImageView google;
    public ImageView facebook;
    public ImageView twitter;
    public ImageView youtube;

    public ImageView image;

    public ScrollView scroll;






    public String oldOfficial;
    public String oldName;
    public String oldRorD;
    public String oldAddr;
    public String oldO_Address;
    public String old_phone;
    public String old_email;
    public String old_website;

    public String old_google;
    public String old_facebook;
    public String old_twitter;
    public String old_youtube;

    public String old_image;

    private static final int EDIT_ACT = 1;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_page);






        official = (TextView) findViewById(R.id.official);
        name = (TextView) findViewById(R.id.name);
        RorD = (TextView) findViewById(R.id.party);
        addr = (TextView) findViewById(R.id.address);
        o_address = (TextView) findViewById(R.id.o_address);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        website = (TextView) findViewById(R.id.website);

        google = (ImageView) findViewById(R.id.iw2);
        youtube = (ImageView) findViewById(R.id.iw1);
        twitter = (ImageView) findViewById(R.id.iw3);
        facebook = (ImageView) findViewById(R.id.iw4);
        image = (ImageView) findViewById(R.id.imageView2);

        scroll = (ScrollView) findViewById(R.id.scroll);













        Intent intent = getIntent();
        if (intent.hasExtra("OFFICIAL") && intent.hasExtra("NAME") && intent.hasExtra("PARTY")) {
            oldOfficial = intent.getStringExtra("OFFICIAL");
            oldName = intent.getStringExtra("NAME");
            oldRorD = intent.getStringExtra("PARTY");
            oldAddr = intent.getStringExtra("ADDRESS");
            oldO_Address=  intent.getStringExtra("ADD");
            old_phone = intent.getStringExtra("PHONE");
            old_email =intent.getStringExtra("EMAIL");
            old_website = intent.getStringExtra("WEBSITE");

            old_youtube = intent.getStringExtra("YOUTUBE");
            old_google = intent.getStringExtra("GOOGLE");
            old_twitter = intent.getStringExtra("TWITTER");
            old_facebook = intent.getStringExtra("FACEBOOK");

            old_image = intent.getStringExtra("IMAGE");

            if(old_youtube == null)
            {
                youtube.setVisibility(View.GONE);
            }
            if(old_google == null)
            {
                google.setVisibility(View.GONE);
            }
            if(old_twitter == null)
            {
                twitter.setVisibility(View.GONE);
            }
            if(old_facebook == null)
            {
                facebook.setVisibility(View.GONE);
            }

            if(oldRorD.equals("(Republican)"))
            {
                scroll.setBackgroundColor(Color.RED);
            }
            else if(oldRorD.equals("(Democratic)"))
            {
                scroll.setBackgroundColor(Color.BLUE);
            }
            else
            {
                scroll.setBackgroundColor(Color.BLACK);
            }






            official.setText(oldOfficial);
            name.setText(oldName);
            RorD.setText(oldRorD);
            addr.setText(oldAddr);
            o_address.setText(oldO_Address);
            phone.setText(old_phone);
            email.setText(old_email);
            website.setText(old_website);


            if(!(oldO_Address.equals("No Data Provided")))
            {
                o_address.setPaintFlags(o_address.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            if(!(old_phone.equals("No Data Provided")))
            {
                phone.setPaintFlags(phone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            if(!(old_email.equals("No Data Provided")))
            {
                email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            if(!(old_website.equals("No Data Provided")))
            {
                website.setPaintFlags(website.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
        }

        o_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(oldO_Address.equals("No Data Provided")))
                {
                    Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(oldO_Address));

                    Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
                    intent.setPackage("com.google.android.apps.maps");

                    startActivity(intent);
                }


            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(old_phone.equals("No Data Provided")))
                {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + old_phone));

                    startActivity(intent);
                }
            }
        });


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(old_email.equals("No Data Provided"))) {

                    String[] addresses = new String[]{old_email};

                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

                    intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Email from Sample Implied Intent App");
                    intent.putExtra(Intent.EXTRA_TEXT, "Email text body...");
                    startActivity(intent);


                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(old_website.equals("No Data Provided"))) {

                    String url = old_website;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }


            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = old_google;
                Intent intent = null;
                try {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.google.android.apps.plus",
                            "com.google.android.apps.plus.phone.UrlGatewayActivity");
                    intent.putExtra("customAppUri", name);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://plus.google.com/" + name)));
                }

            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = old_youtube;
                Intent intent = null;
                try {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse("https://www.youtube.com/" + name));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/" + name)));
                }
            }


        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                String name = old_twitter;
                try {
                    // get the Twitter app if possible
                    getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
                }
                startActivity(intent);
            }



        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FACEBOOK_URL = "https://www.facebook.com/" + old_facebook;
                String urlToUse;
                PackageManager packageManager = getPackageManager();
                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) { //newer versions of fb app
                        urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                    } else { //older versions of fb app
                        urlToUse = "fb://page/" + old_facebook;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    urlToUse = FACEBOOK_URL; //normal web url
                }
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                facebookIntent.setData(Uri.parse(urlToUse));
                startActivity(facebookIntent);
            }



        });

        if (image != null){
            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    final String changedUrl = old_image.replace("http:", "https:");
                    picasso.load(changedUrl)
                            .error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder)
                            .into(image);
                }
            }).build();
            picasso.load(old_image)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(image);
        }
        else {
            Picasso.get().load(old_image)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missingimage)
                    .into(image);
        }


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentEdit = new Intent(OfficialPage.this, official_page2.class);
                intentEdit.putExtra("OFFICIAL", oldOfficial);
                intentEdit.putExtra("NAME", oldName);
                intentEdit.putExtra("IMAGE", old_image);
                intentEdit.putExtra("ADDRESS", oldAddr);
                intentEdit.putExtra("PARTY", oldRorD);




                //intentEdit.putExtra("NEW_NOTE", "false");
                startActivityForResult(intentEdit, EDIT_ACT);
            }



        });







    }



}
