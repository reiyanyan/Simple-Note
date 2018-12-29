package com.reiyan.simplenote.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.adapter.RecyclerAdapter;
import com.reiyan.simplenote.model.NotesModel;
import com.reiyan.simplenote.util.RecyclerOnClick;
import com.reiyan.simplenote.util.RecyclerSwipeDelete;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerOnClick {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Intent intent;
    private RecyclerView recView;
    private RecyclerAdapter adapter;
    private NotesModel model;
    private ItemTouchHelper helper;
    private RecyclerSwipeDelete swipeMe;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        fab = findViewById(R.id.fab);
        navigationView = findViewById(R.id.nav_view);
        recView = findViewById(R.id.recView);
        constraintLayout = findViewById(R.id.constraint);
        intent = new Intent(MainActivity.this, NoteActivity.class);
        model = ViewModelProviders.of(this).get(NotesModel.class);
        adapter = new RecyclerAdapter(this, this);
        swipeMe = new RecyclerSwipeDelete(adapter, model,constraintLayout);
        helper = new ItemTouchHelper(swipeMe);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        helper.attachToRecyclerView(recView);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        model.getAllNotes().observe(this, notes -> adapter.setData(notes));

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fab.setOnClickListener(view -> startActivity(intent));
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(int position, int id) {
        intent.putExtra("id", id);
        startActivity(intent);
        intent.removeExtra("id");
    }

}
