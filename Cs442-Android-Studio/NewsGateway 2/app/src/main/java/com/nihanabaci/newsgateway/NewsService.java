package com.nihanabaci.newsgateway;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

public class NewsService extends Service{
    private static final String TAG = "CountService";
    private boolean running = true;
    private ArrayList<News> fruitList = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (intent.hasExtra("FRUIT_LIST")) {
            ArrayList<News> listIn = (ArrayList<News>) intent.getSerializableExtra("FRUIT_LIST");
            fruitList.addAll(listIn);
        }

        if (intent.hasExtra("ARTICLES_LIST") && (MainActivity.number != -1)) {
            ArrayList<Articles> listIn = (ArrayList<Articles>) intent.getSerializableExtra("ARTICLES_LIST");
            Log.d(TAG, "GETS THE INTENT" + fruitList.isEmpty());
            fruitList.get(MainActivity.number).setArticles(listIn);
        }



        new Thread(new Runnable() {
            @Override
            public void run() {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(MainActivity.number != -1 && fruitList.get(MainActivity.number).getArticles0() != null)
                    {
                        sendFruit(fruitList.get(MainActivity.number));
                    }


                sendMessage("Service Thread Stopped");


                Log.d(TAG, "run: Ending loop");
            }
        }).start();


        return Service.START_NOT_STICKY;
    }

    private void sendFruit(News toSend) {
        Intent intent = new Intent();
        intent.setAction(MainActivity.NEWS_DATA_BROADCAST_FROM_SERVICE);
        intent.putExtra(MainActivity.SERVICE_DATA, toSend);
        sendBroadcast(intent);
    }

   /* private void sendArticles(Articles toSend) {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ARTICLES_DATA_BROADCAST_FROM_SERVICE);
        intent.putExtra(MainActivity.SERVICE_DATA, toSend);
        sendBroadcast(intent);
    }*/

    private void sendMessage(String msg) {
        Intent intent = new Intent();
        intent.setAction(MainActivity.MESSAGE_BROADCAST_FROM_SERVICE);
        intent.putExtra(MainActivity.SERVICE_DATA, msg);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        sendMessage("Service Destroyed");
        running = false;
        super.onDestroy();
    }

}
