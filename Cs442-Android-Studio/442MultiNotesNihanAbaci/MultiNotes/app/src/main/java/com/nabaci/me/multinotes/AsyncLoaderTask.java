package com.nabaci.me.multinotes;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AsyncLoaderTask extends AsyncTask<String, Void, List<Note>> {

    private MainActivity mainActivity;

    public AsyncLoaderTask(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected List<Note> doInBackground(String... params) {

        String json;
        List<Note> noteLst = new ArrayList<>();  // Main content is here
        try {

            InputStream is = new FileInputStream(params[0]);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray list = new JSONArray(json);

            for (int i=0; i<list.length(); i++) {
                JSONObject note = list.getJSONObject(i);
                noteLst.add(new Note(note.getString("title"), note.getString("time"), note.getString("text")));
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(mainActivity.getApplicationContext(), "File not present", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteLst;
    }

    @Override
    protected void onPostExecute(List<Note> notes) {
        mainActivity.setList(notes);
    }
}
