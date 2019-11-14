package com.nabaci.me.multinotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    public EditText etTitle;
    public EditText etText;
    public String oldTitle;
    public String oldText;
    public int notePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = (EditText) findViewById(R.id.et_title);
        etText = (EditText) findViewById(R.id.et_text);

        // Load data from intent here!
        Intent intent = getIntent();
        if (intent.hasExtra("NOTE_TITLE") && intent.hasExtra("NOTE_TEXT")) {
            oldTitle = intent.getStringExtra("NOTE_TITLE");
            oldText = intent.getStringExtra("NOTE_TEXT");
            etTitle.setText(oldTitle);
            etText.setText(oldText);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mi_save:
                Intent data = new Intent();
                if (etTitle.getText().toString().length() != 0) {
                    if (!etTitle.getText().toString().equals(oldTitle) || !etText.getText().toString().equals(oldText)) {
                        data.putExtra("NOTE_TITLE", etTitle.getText().toString());
                        data.putExtra("NOTE_TEXT", etText.getText().toString());
                        data.putExtra("NOTE_TIME", new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date()));
                        data.putExtra("SAVE_NOTE", true);
                        data.putExtra("NOTE_POS", String.valueOf(notePos));
                    } else {
                        data.putExtra("NOTE_POS", String.valueOf(-1));
                        data.putExtra("SAVE_NOTE", false);
                    }
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Untitled note not saved", Toast.LENGTH_SHORT).show();
                    finish();
                }

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        if (etTitle.getText().toString().length() != 0) {
            if (!etTitle.getText().toString().equals(oldTitle) || !etText.getText().toString().equals(oldText)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent data = new Intent();
                        data.putExtra("NOTE_TITLE", etTitle.getText().toString());
                        data.putExtra("NOTE_TEXT", etText.getText().toString());
                        data.putExtra("NOTE_TIME", new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date()));
                        data.putExtra("SAVE_NOTE", true);
                        data.putExtra("NOTE_POS", String.valueOf(notePos));
                        setResult(RESULT_OK, data);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        finish();
                    }
                });

                builder.setMessage("Note not saved. Save it?");

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Untitled note not saved", Toast.LENGTH_SHORT).show();
            finish();
        }
//        super.onBackPressed();
    }
}
