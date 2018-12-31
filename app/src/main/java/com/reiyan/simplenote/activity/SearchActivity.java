package com.reiyan.simplenote.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.adapter.RecyclerAdapter;
import com.reiyan.simplenote.model.NotesModel;
import com.reiyan.simplenote.util.RecyclerOnClick;

public class SearchActivity extends AppCompatActivity implements RecyclerOnClick {

    private RecyclerView recView;
    private Toolbar toolbar;
    private RecyclerAdapter adapter;
    private NotesModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recView = findViewById(R.id.recView);
        toolbar = findViewById(R.id.toolbar);
        adapter = new RecyclerAdapter(this, this);
        model = ViewModelProviders.of(SearchActivity.this).get(NotesModel.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            recView.setAdapter(adapter);
            recView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            model.findByKey(getIntent().getStringExtra("key")).observe(SearchActivity.this, notes -> adapter.setData(notes));
        }
    }

    @Override
    public void onClick(int position, int id) {
        Intent intentToNote = new Intent(SearchActivity.this, NoteActivity.class);
        intentToNote.putExtra("id", id);
        startActivity(intentToNote);
        intentToNote.removeExtra("id");
    }
}
