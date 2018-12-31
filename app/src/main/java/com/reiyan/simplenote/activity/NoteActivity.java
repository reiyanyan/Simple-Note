package com.reiyan.simplenote.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.model.Notes;
import com.reiyan.simplenote.model.NotesModel;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etTitle, etNote;
    private InputMethodManager inputMethodManager;
    private NotesModel helper;
    private Notes notes;
    private int id;
    private Typeface rubikFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        toolbar = findViewById(R.id.toolbar);
        etTitle = findViewById(R.id.et_title);
        etNote = findViewById(R.id.et_note);
        helper = ViewModelProviders.of(this).get(NotesModel.class);
        notes = new Notes();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        rubikFont = Typeface.createFromAsset(getAssets(), "fonts/Rubik-Regular.ttf");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNote.setTypeface(rubikFont);
        etTitle.setTypeface(rubikFont);

        if (getIntent().hasExtra("id")){
            id = getIntent().getIntExtra("id", -1);

            etTitle.clearFocus();
            etNote.clearFocus();

            helper.findById(id).observe(this, notes -> {
                etNote.setText(notes.getNote());
                etTitle.setText(notes.getTitle());
            });

        } else {
            etNote.requestFocus();
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            /*case R.id.pin:
                Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.done:
                if (getIntent().hasExtra("id")){
                    id = getIntent().getIntExtra("id", -1);
                    notes.setId(id);
                    notes.setTitle(etTitle.getText().toString());
                    notes.setNote(etNote.getText().toString());
                    helper.updateData(notes);
                    finish();
                } else {
                    notes.setTitle(etTitle.getText().toString());
                    notes.setNote(etNote.getText().toString());
                    helper.inserData(notes);
                    inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),  0);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),  0);
    }
}
