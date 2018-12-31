package com.reiyan.simplenote.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.reiyan.simplenote.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txt3, txt4, txt6, txt8, txt10, txt11, txt12;
    private Typeface rubikFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = findViewById(R.id.toolbar);
        txt3 = findViewById(R.id.textView3);
        txt4 = findViewById(R.id.textView4);
        txt6 = findViewById(R.id.textView6);
        txt8 = findViewById(R.id.textView8);
        txt10 = findViewById(R.id.textView10);
        txt11 = findViewById(R.id.textView11);
        txt12 = findViewById(R.id.textView12);
        rubikFont = Typeface.createFromAsset(getAssets(), "fonts/Rubik-Regular.ttf");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Apps");

        txt3.setTypeface(rubikFont);
        txt4.setTypeface(rubikFont);
        txt6.setTypeface(rubikFont);
        txt8.setTypeface(rubikFont);
        txt10.setTypeface(rubikFont);
        txt11.setTypeface(rubikFont);
        txt12.setTypeface(rubikFont);

    }
}
