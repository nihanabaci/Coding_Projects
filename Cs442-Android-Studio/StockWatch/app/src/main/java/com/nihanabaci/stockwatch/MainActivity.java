package com.nihanabaci.stockwatch;

import android.content.*;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Comparable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;



public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private ArrayList<stock> stocksList = new ArrayList<stock>();  // Main content is here
    private RecyclerView recyclerView; // Layout's recyclerview
    private Adapter mAdapter;
    private SwipeRefreshLayout swiper;

    private DatabaseHandler databaseHandler;

    public ArrayList<stock> fetchStocks = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swiper = findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
         if(!doNetCheck()){
                AlertDialog.Builder noneFound = new AlertDialog.Builder(this);
                noneFound.setTitle("Not Connected to network");
                noneFound.show();
            }






           // @Override


            /*public void onRefresh() {
                mAdapter.notifyDataSetChanged();
                swiper.setRefreshing(false);
                Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
            }
        });*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        databaseHandler = new DatabaseHandler(this);

        mAdapter = new Adapter(stocksList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ArrayList<stock> list = databaseHandler.loadStocks();
        //stocksList.clear();
        //stocksList.addAll(list);

       // new AsyncStockLoader(this).execute();

        //new FetchingData(this).execute();

        fetchStocks();

    }

    public  void fetchStocks(){
        ArrayList<stock> list = databaseHandler.loadStocks();
        stocksList.addAll(list);
        mAdapter.notifyDataSetChanged();
        new FetchingData(this).execute(stocksList);
    }

    public void initRecyclerView(ArrayList<stock> stockList){
//        mAdapter = new Adapter(stockList, this);
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Collections.sort(stockList);
        mAdapter.notifyDataSetChanged();
    }

    private void doRefresh() {
        if(doNetCheck()){
            //new UpdateFinancialData(this);
            initRecyclerView(new ArrayList<stock>());
            stocksList.clear();
            fetchStocks();
            swiper.setRefreshing(false);
            Toast.makeText(this, "Updated Data", Toast.LENGTH_SHORT).show();
        }
        else
            {
            swiper.setRefreshing(false);
            AlertDialog.Builder noneFound = new AlertDialog.Builder(this);
            noneFound.setTitle("Not Connected to network");
            noneFound.show();
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mi_new:
                if (!doNetCheck()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Not Connected to network");
                    builder1.show();
                }

                 else
                    {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog);
                        } else {
                            builder = new AlertDialog.Builder(this);
                        }
                        LayoutInflater inflater = getLayoutInflater();
                        final View queryView = inflater.inflate(R.layout.diolog, null);
                        builder.setView(queryView)
                                .setTitle("Stock Selection")
                                .setMessage("Please enter a Stock Symbol:")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        EditText v = (EditText) queryView.findViewById(R.id.stock_query);
                                        String stockQuery = v.getText().toString();
                                        ArrayList<stock> result = searchStocks(stockQuery);
                                        showFetchedCourseDialog(result, stockQuery);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();

                    }
                    return true;
                    default:
                        return super.onOptionsItemSelected(item);

                }
        }








               /* AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Stock Selection");
                builder.setMessage("Please enter a Stock Symbol:");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
                final EditText et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(et);

                for(int i =0; i < fetchStocks.size(); i++){
                    arrayAdapter.add(fetchStocks.get(i).getName());
                }

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText v = (EditText)queryView.findViewById(R.id.stock_query);
                        String stockQuery = v.getText().toString();
                        ArrayList<Stock> result = searchStocks(stockQuery);
                        showFetchedCourseDialog(result, stockQuery);

                        //you have to look into the list to see if that value exists
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //the test at the buttom

                     //   Toast.makeText(MainActivity.this, "You changed your mind!", Toast.LENGTH_SHORT).show();
                        //tv1.setText("No Way Selected");
                    }
                });



                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            default:
                return false;*/



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
    public  void addNewStock(stock stock){
        databaseHandler.addStock(stock);
        ArrayList<stock> list = databaseHandler.loadStocks();
        stocksList.clear();
        stocksList.addAll(list);
        new FetchingData(this).execute(stocksList);
    }

    public void showFetchedCourseDialog(final ArrayList<stock> fetchedStocks, String query){
        android.app.AlertDialog.Builder builderSingle = new android.app.AlertDialog.Builder(this);
        builderSingle.setTitle("Make A Selection");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        for(int i =0; i < fetchedStocks.size(); i++){
            arrayAdapter.add(fetchedStocks.get(i).getName());
        }

        builderSingle.setNegativeButton("NEVERMIND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stock stock =  new stock(
                        fetchedStocks.get(which).getSymbol(),
                        fetchedStocks.get(which).getName(),
                        fetchedStocks.get(which).getId()
                );
                addNewStock(stock);
            }
        });
        if(fetchedStocks.size() >0){
            builderSingle.show();
        }
        else{
            android.app.AlertDialog.Builder noneFound = new android.app.AlertDialog.Builder(this);
            noneFound.setTitle("Symbol not Found: " + query);
            noneFound.show();
        }
    }

    @Override
    protected void onResume() {
       // databaseHandler.dumpDbToLog();

        //recyclerView = (RecyclerView) findViewById(R.id.recycler);
        //ArrayList<stock> list = databaseHandler.loadStocks();

        //stocksList.clear();
       // stocksList.addAll(list);

        mAdapter.notifyDataSetChanged();

       new AsyncStockLoader(this).execute("https://api.iextrading.com/1.0/ref-data/symbols");

       //mAdapter = new Adapter(stocksList, this);

        //recyclerView.setAdapter(mAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Collections.sort(stocksList);

        //mAdapter.notifyDataSetChanged();

        //new AsyncStockLoader(this).execute();

        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        databaseHandler.shutDown();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks

        int pos = recyclerView.getChildLayoutPosition(v);
        stock stk = stocksList.get(pos);
        String watch= "https://www.marketwatch.com/tools/quotes/lookup.asp?lookup=some_stock" + stk.getSymbol();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(watch));
        startActivity(i);

    }
    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        final int pos = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Delete current position from list
                databaseHandler.deleteStock(stocksList.get(pos).getSymbol());
                stocksList.remove(pos);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage("Delete " + stocksList.get(pos).getSymbol() + "?");

        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }
    public void removeNote(int pos) {
        stocksList.remove(pos);
        mAdapter.notifyDataSetChanged();
    }
    public void setList(ArrayList<stock> stks) {
        stocksList = stks;
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        mAdapter = new Adapter(stocksList, this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void updateData(ArrayList<stock> cList) {
        if (!fetchStocks.isEmpty()) {
            fetchStocks.clear();
        }
        fetchStocks.addAll(cList);
    }

    public ArrayList<stock> searchStocks(String stockQuery){
        ArrayList<stock> toRet = new ArrayList<>();
        if(stockQuery.length() <= 0){
            return toRet;
        }
        for(int i =0; i < fetchStocks.size(); i++){
            if(fetchStocks.get(i).getSymbol().contains(stockQuery.toUpperCase())){
                toRet.add(fetchStocks.get(i));
            }
        }
        return toRet;
    }

}

