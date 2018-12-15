package com.nihanabaci.newsgateway;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.NetworkInfo;
import java.lang.Boolean;
import java.lang.reflect.Array;
import java.util.ArrayList;
import android.support.v7.app.AlertDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.res.Configuration;
import android.view.MenuItem;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import android.view.Menu;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import com.squareup.picasso.Picasso;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private static ArrayList<News> NewsList = new ArrayList<>();
    private static ArrayList<Articles> ArticleList = new ArrayList<>();
    public static String API = "8123fecbd12e44cd988aa6c2902ada48";
    private static final String TAG = "HELLOOOO";
    private List<Fragment> fragments;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] items;
    ArrayAdapter<?> aa;
    private static final int EDIT_ACT = 1;
    public Menu menubar;
    static final String NEWS_DATA_BROADCAST_FROM_SERVICE = "NEWS_DATA_BROADCAST_FROM_SERVICE";
    static final String ARTICLES_DATA_BROADCAST_FROM_SERVICE = "ARTICLES_DATA_BROADCAST_FROM_SERVICE";
    static final String MESSAGE_BROADCAST_FROM_SERVICE = "MESSAGE_BROADCAST_FROM_SERVICE";
    static final String SERVICE_DATA = "SERVICE_DATA";
    private SampleReceiver sampleReceiver;
    public static String URL = "";
    public static String sign = "0";
    public TextView text;
    static int number = -1;
    private MyPageAdapter pageAdapter;
    private String currentSubRegion;


    private ViewPager pager;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        URL = "https://newsapi.org/v2/sources?language=en&country=us&apiKey=" + API;

        new JSON1(this).execute();


        fragments = new ArrayList<>();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);


        Menu1(NewsList);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        this.menubar = menu;

        return true;
    }

    public void stopService(View v) {
        Intent intent = new Intent(MainActivity.this, NewsService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(sampleReceiver);
        Intent intent = new Intent(MainActivity.this, NewsService.class);
        stopService(intent);
        super.onDestroy();
    }



        @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState(); // <== IMPORTANT
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig); // <== IMPORTANT
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {  // <== Important!



            return true;
        }

        ArrayList<News> NewsList2 = new ArrayList<>();
        for(int i=0; i<NewsList.size(); i++)
        {

            if((NewsList.get(i).getCategory()).equals(item.toString()))
            {
                NewsList2.add(NewsList.get(i));
            }
        }
        Menu1(NewsList2);


        return super.onOptionsItemSelected(item);

    }
    private void selectItem(int position) {
        this.URL = "https://newsapi.org/v2/top-headlines?pageSize=50&sources=" + NewsList.get(position).getId() +"&apiKey=" + API;
        sign = "1";

        this.number = position;
        new JSON1(this).execute(URL);
        mDrawerLayout.closeDrawer(mDrawerList);

        DrawerLayout relative = (DrawerLayout) findViewById(R.id.drawer_layout);
        relative.setBackgroundResource(0);

        setTitle(NewsList.get(this.number).getName());
    }


    public void updateData(ArrayList<News> cList)
    {
        NewsList.clear();
        NewsList.addAll(cList);
        aa.notifyDataSetChanged();
        Menu1(cList);
        CreateMenuBar(cList);
        createService(cList);




    }
    public void AupdateData(ArrayList<Articles> cList)
    {
        ArticleList.clear();
        ArticleList.addAll(cList);
        AcreateService(cList);



    }

    public void Menu1(ArrayList<News> ne)
    {
        int size = ne.size();

        items = new String[size];
        for (int i = 0; i < size; i++)
            items[i] = this.NewsList.get(i).getName();


        mDrawerLayout = findViewById(R.id.drawer_layout); // <== Important!
        mDrawerList = findViewById(R.id.drawer_list); // <== Important!

        aa = new ArrayAdapter<>(this,
                R.layout.drawer_list_item, items);

        mDrawerList.setAdapter(aa);

        mDrawerList.setOnItemClickListener(   // <== Important!
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectItem(position);
                    }
                }
        );

        mDrawerToggle = new ActionBarDrawerToggle(   // <== Important!
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );

        if (getSupportActionBar() != null) {  // <== Important!
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

    }

    public void CreateMenuBar(ArrayList<News> ne)
    {

        ArrayList<String> menuList = new ArrayList<>();

        for(int i= 0; i<ne.size(); i++)
        {
            String cat = ne.get(i).getCategory();
            if(!menuList.contains(cat)) {
                menuList.add(cat);
                this.menubar.add(0, i, 0, ne.get(i).getCategory());
            }

        }

    }
    public void createService(ArrayList<News> ne)
    {

        Intent intent = new Intent(MainActivity.this, NewsService.class);
        intent.putExtra("FRUIT_LIST", ne);


        startService(intent);


        sampleReceiver = new SampleReceiver();

        IntentFilter filter1 = new IntentFilter(NEWS_DATA_BROADCAST_FROM_SERVICE);
        registerReceiver(sampleReceiver, filter1);

        IntentFilter filter2 = new IntentFilter(MESSAGE_BROADCAST_FROM_SERVICE);
        registerReceiver(sampleReceiver, filter2);
    }

    public void AcreateService(ArrayList<Articles> ne)
    {

        Intent intent1 = new Intent(MainActivity.this, NewsService.class);
        intent1.putExtra("ARTICLES_LIST", ne);

        startService(intent1);

        sampleReceiver = new SampleReceiver();

        IntentFilter filter1 = new IntentFilter(ARTICLES_DATA_BROADCAST_FROM_SERVICE);
        registerReceiver(sampleReceiver, filter1);

        IntentFilter filter2 = new IntentFilter(MESSAGE_BROADCAST_FROM_SERVICE);
        registerReceiver(sampleReceiver, filter2);
    }



    class SampleReceiver extends BroadcastReceiver {



        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action == null)
                return;
            switch (action) {
                case NEWS_DATA_BROADCAST_FROM_SERVICE:
                    News newFruit = null;
                    if (intent.hasExtra(SERVICE_DATA))

                        newFruit = (News) intent.getSerializableExtra(SERVICE_DATA);
                        Log.d(TAG, "OLALA" + newFruit.getArticles0().isEmpty());
                        for (int i = 0; i < pageAdapter.getCount(); i++)
                            pageAdapter.notifyChangeInPosition(i);

                        fragments.clear();
                        for(int i=0; i<newFruit.getArticles0().size(); i++)
                        {
                            fragments.add(
                                    NewsFragment.newInstance(newFruit.getArticles0().get(i), i+1, newFruit.getArticles0().size())
                            );
                            Log.d(TAG, "WELL" + newFruit.getArticles0().get(i).getTitle());
                        }

                        pageAdapter.notifyDataSetChanged();
                        pager.setCurrentItem(0);

                    /*((TextView) findViewById(R.id.title)).setText(newFruit.getArticles0().get(0).getTitle());
                    ((TextView) findViewById(R.id.date)).setText(newFruit.getArticles0().get(0).getDate());
                    ((TextView) findViewById(R.id.author)).setText(newFruit.getArticles0().get(0).getAuthor());
                    final String imageUrl = newFruit.getArticles0().get(0).getUrlImage();
                    final ImageView imageview = findViewById(R.id.imageView);
                    if (imageUrl != null) {
                        Picasso picasso = new Picasso.Builder(MainActivity.this).listener(new Picasso.Listener() {
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
                    ((TextView) findViewById(R.id.description)).setText(newFruit.getArticles0().get(0).getDescription());

*/

                    break;

                case MESSAGE_BROADCAST_FROM_SERVICE:
                    String data = "";
                    if (intent.hasExtra(SERVICE_DATA))
                        data = intent.getStringExtra(SERVICE_DATA);
                    //((TextView) findViewById(R.id.textView)).setText(data);
                    break;

                default:
                    Log.d(TAG, "onReceive: Unkown broadcast received");
            }
        }

    }
    private class MyPageAdapter extends FragmentPagerAdapter {
        private long baseId = 0;


        MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        /**
         * Notify that the position of a fragment has been changed.
         * Create a new ID for each position to force recreation of the fragment
         * @param n number of items which have been changed
         */
        void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

    }

    }



