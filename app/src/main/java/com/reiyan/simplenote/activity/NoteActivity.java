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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.model.Notes;
import com.reiyan.simplenote.model.NotesModel;
import com.reiyan.simplenote.receiver.NotificationHelper;

import java.util.Calendar;
import java.util.Random;

public class NoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private EditText etTitle, etNote;
    private InputMethodManager inputMethodManager;
    private NotesModel helper;
    private Notes notes;
    private int id, year, month, day, hour, minute;
    private AlertDialog.Builder builder;
    private View view;
    private TextView date, time;
    private Spinner spinnerRepeat;
    private String[] dummy;
    private SpinnerAdapter spinnerAdapter;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private Calendar calendar;
    private ConstraintLayout constrainDialog;
    private boolean repeat;


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

        dummy = new String[] {
                "Doesn't Repeat",
                "Repeat"
        };
        spinnerAdapter = new com.reiyan.simplenote.adapter.SpinnerAdapter(this, dummy);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);


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
                view = LayoutInflater.from(this).inflate(R.layout.dialog_remind, constrainDialog, false);
                date = view.findViewById(R.id.date);
                time = view.findViewById(R.id.time);
                spinnerRepeat = view.findViewById(R.id.repeat);

                spinnerRepeat.setAdapter(spinnerAdapter);
                spinnerRepeat.setSelection(0);
                spinnerRepeat.setOnItemSelectedListener(this);

                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),  0);

                builder.setTitle("Add reminder");
                builder.setCancelable(true);
                builder.setView(view);

                date.setOnClickListener(v -> {
                    datePicker = new DatePickerDialog(NoteActivity.this, (view, year, month, dayOfMonth) -> {
                        date.setText(dayOfMonth + " - " + (month + 1) + " - " + year);
                        date.setTextColor(this.getColor(android.R.color.black));
                    }, year, month, day);
                    datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePicker.show();
                });

                time.setOnClickListener(v -> {
                    timePicker = new TimePickerDialog(NoteActivity.this, (view, hourOfDay, minute) -> {
                        time.setText(hourOfDay + "." + minute);
                        time.setTextColor(this.getColor(android.R.color.black));
                    }, hour, minute, false);
                    timePicker.show();
                });

                builder.setPositiveButton("OK", (dialog, which) -> Toast.makeText(NoteActivity.this, "tanggal " + date.getText().toString() + "\n jam " + time.getText().toString(), Toast.LENGTH_SHORT).show());

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Toast.makeText(this, "Halo 0", Toast.LENGTH_SHORT).show();
                NotificationHelper helper = new NotificationHelper(this);
                helper.getManager().notify(new Random().nextInt(), helper.getNotificationRepeat("halo", "kang akmj").build());
                break;
            case 1:
                Toast.makeText(this, "Halo 1", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
