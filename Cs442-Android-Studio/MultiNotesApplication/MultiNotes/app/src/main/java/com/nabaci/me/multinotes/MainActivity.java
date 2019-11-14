package com.nabaci.me.multinotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private List<Note> noteList = new ArrayList<>();  // Main content is here
    private RecyclerView recyclerView; // Layout's recyclerview
    private NotesAdapter mAdapter; // Data to recyclerview adapter
    private boolean isNew = true;

    private static final int EDIT_ACT = 1;

    AsyncLoaderTask alt = new AsyncLoaderTask(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File f = new File(getApplicationContext().getFilesDir() + "/data.json");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception e) {

            }
        }
        alt.execute(getApplicationContext().getFilesDir() + "/data.json");
    }

    @Override
    protected void onPause() {
        saveFile();
        super.onPause();
    }

    @Override
    protected void onResume() {
        recyclerView = (RecyclerView) findViewById(R.id.rec_notes);

        mAdapter = new NotesAdapter(noteList, this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mi_about:
                Intent intentAbt = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAbt);
                return true;
            case R.id.mi_new:
                Intent intentEdit = new Intent(MainActivity.this, EditActivity.class);
                isNew = true;
                startActivityForResult(intentEdit, EDIT_ACT);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks

        int pos = recyclerView.getChildLayoutPosition(v);
        Note note = noteList.get(pos);
        Intent intentEdit = new Intent(MainActivity.this, EditActivity.class);
        intentEdit.putExtra("NOTE_POS", pos);
        intentEdit.putExtra("NOTE_TITLE", note.getTitle());
        intentEdit.putExtra("NOTE_TEXT", note.getText());
        intentEdit.putExtra("NEW_NOTE", "false");
        startActivityForResult(intentEdit, EDIT_ACT);
    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        final int pos = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Delete current position from list
                removeNote(pos);
                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage("Delete " + noteList.get(pos).getTitle() + "?");

        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ACT) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("SAVE_NOTE", false)) {
                    if (Integer.parseInt(data.getStringExtra("NOTE_POS")) >= 0 && noteList.size() > 0 && !isNew) {
                        removeNote(Integer.parseInt(data.getStringExtra("NOTE_POS")));
                    }
                    addNote(new Note(data.getStringExtra("NOTE_TITLE"), data.getStringExtra("NOTE_TIME"), data.getStringExtra("NOTE_TEXT")));
                    isNew = false;
                    Toast.makeText(getApplicationContext(), "Note Added", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Cross Activity Problem!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeNote(int pos) {
        noteList.remove(pos);
        mAdapter.notifyDataSetChanged();
    }

    public void addNote(Note note) {
        noteList.add(0, note);
        mAdapter.notifyDataSetChanged();
    }

    public void setList(List<Note> notes) {
        noteList = notes;
        recyclerView = (RecyclerView) findViewById(R.id.rec_notes);

        mAdapter = new NotesAdapter(noteList, this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void saveFile() {
        JSONArray list = new JSONArray();
        for (int i=0; i<noteList.size(); i++) {
            JSONObject note = new JSONObject();
            try {
                note.put("title", noteList.get(i).getTitle());
                note.put("text", noteList.get(i).getText());
                note.put("time", noteList.get(i).getTime());
                list.put(note);
            } catch (Exception e) {

            }
        }
        // Save file here
        try {
            Writer output = null;
            File file = new File(getApplicationContext().getFilesDir() + "/data.json");
            output = new BufferedWriter(new FileWriter(file));
            output.write(list.toString());
            output.close();
            Toast.makeText(getApplicationContext(), "File Saved", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void readFile() {
        String json;
        try {
            InputStream is = getApplicationContext().openFileInput("data.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray list = new JSONArray(json);

            for (int i=0; i<list.length(); i++) {
                JSONObject note = list.getJSONObject(i);
                noteList.add(new Note(note.getString("title"), note.getString("time"), note.getString("text")));
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not present!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
