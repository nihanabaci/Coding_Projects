package com.nihanabaci.newsgateway;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NewsFragment extends Fragment{
    private static final String TAG = "HELLOOOO";

    public NewsFragment() { }

    public static NewsFragment newInstance(Articles country, int index, int max)
    {

        NewsFragment f = new NewsFragment();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable("COUNTRY_DATA", country);
        bdl.putSerializable("INDEX", index);
        bdl.putSerializable("TOTAL_COUNT", max);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment_layout = inflater.inflate(R.layout.fragment_news, container, false);

        final Articles currentCountry = (Articles) getArguments().getSerializable("COUNTRY_DATA");
        int index = getArguments().getInt("INDEX");
        int total = getArguments().getInt("TOTAL_COUNT");


        TextView title = ((TextView) fragment_layout.findViewById(R.id.title));
        title.setText(currentCountry.getTitle());

        ((TextView) fragment_layout.findViewById(R.id.date)).setText(currentCountry.getDate());

        ((TextView) fragment_layout.findViewById(R.id.author)).setText(currentCountry.getAuthor());
        final String imageUrl = currentCountry.getUrlImage();
        final ImageView imageview = fragment_layout.findViewById(R.id.imageView);
        if (imageUrl != null) {
            Picasso picasso = new Picasso.Builder(getContext().getApplicationContext()).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    final String changedUrl = imageUrl.replace("http:", "https:");
                    picasso.load(changedUrl)
                            .into(imageview);
                }
            }).build();
            picasso.load(imageUrl)
                    .into(imageview);
        }
        TextView description = ((TextView) fragment_layout.findViewById(R.id.description));
        description.setText(currentCountry.getDescription());

        TextView pageNum = fragment_layout.findViewById(R.id.page_num);
        pageNum.setText(String.format(Locale.US, "%d of %d", index, total));


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(currentCountry.getUrl()));
                startActivity(i);
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentCountry.getUrl()));
                startActivity(i);
            }
        });
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentCountry.getUrl()));
                startActivity(i);
            }
        });


        return fragment_layout;
    }


    public void clickFlag(String name) {

        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(name));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

    }

}
