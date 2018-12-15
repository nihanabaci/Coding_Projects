package com.nihanabaci.knowyourgovernment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class official_page2 extends AppCompatActivity {

    public TextView address;
    public TextView title;
    public TextView name;
    public ImageView image;

    public String old_a;
    public String old_t;
    public String old_n;
    public String old_i;
    public String old_party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_page2);


        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        image = (ImageView) findViewById(R.id.image);
        address = (TextView) findViewById(R.id.address);
        //party = (TextView) findViewById(R.id.address);


       // ConstraintLayout ly = new ConstraintLayout();
        ConstraintLayout ly = (ConstraintLayout) findViewById(R.id.layout);

        Intent intent = getIntent();
        if (intent.hasExtra("OFFICIAL") && intent.hasExtra("NAME") ) {
            old_t = intent.getStringExtra("OFFICIAL");
            old_n = intent.getStringExtra("NAME");
            old_i = intent.getStringExtra("IMAGE");
            old_a = intent.getStringExtra("ADDRESS");
            old_party = intent.getStringExtra("PARTY");


            if(old_party.equals("(Republican)"))
            {
                ly.setBackgroundColor(Color.RED);
            }
            else if(old_party.equals("(Democratic)"))
            {
                ly.setBackgroundColor(Color.BLUE);
            }
            else
            {
                ly.setBackgroundColor(Color.BLACK);
            }




            title.setText(old_t);
            name.setText(old_n);
            address.setText(old_a);



        }

        if (image != null) {
            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    final String changedUrl = old_i.replace("http:", "https:");
                    picasso.load(changedUrl)
                            .error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder)
                            .into(image);
                }
            }).build();
            picasso.load(old_i)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(image);
        } else {
            Picasso.get().load(old_i)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missingimage)
                    .into(image);
        }
    }

}
