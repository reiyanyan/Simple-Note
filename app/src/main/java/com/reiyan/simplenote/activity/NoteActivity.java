package com.reiyan.simplenote.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.model.Notes;
import com.reiyan.simplenote.model.NotesModel;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etTitle, etNote;
    private InputMethodManager inputMethodManager;
    private NotesModel helper;
    private Notes notes;
    private int id, year, month, day;
    private AlertDialog.Builder builder;
    private View view;
    private TextView date, time;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Calendar calendar;
    private ConstraintLayout constrainDialog;


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
        builder = new AlertDialog.Builder(this);
        constrainDialog = findViewById(R.id.constrainDialog);
        view = LayoutInflater.from(this).inflate(R.layout.dialog_remind, constrainDialog, false);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("id")){
            id = getIntent().getIntExtra("id", -1);
            helper.findById(id).observe(this, notes -> etNote.setText(notes.getNote()));
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
            case R.id.pin:
                Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remind:
                date = view.findViewById(R.id.date);
                time = view.findViewById(R.id.time);
                builder.setTitle("Add reminder");
                builder.setCancelable(true);
                builder.setView(view);

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker = new DatePickerDialog(NoteActivity.this, (view, year, month, dayOfMonth) -> date.setText(dayOfMonth + " - " + (month + 1) + " - " + year),year, month, day);
                        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        datePicker.show();
                    }
                });
                builder.show();
                break;
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

}
