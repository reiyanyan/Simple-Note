package com.reiyan.simplenote.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.adapter.RecyclerAdapter;
import com.reiyan.simplenote.model.NotesModel;
import com.reiyan.simplenote.util.RecyclerOnClick;
import com.reiyan.simplenote.util.RecyclerSwipeDelete;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerOnClick, TextView.OnEditorActionListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Intent intentToNote, intentToSearch;
    private RecyclerView recView;
    private RecyclerAdapter adapter;
    private NotesModel model;
    private ItemTouchHelper helper;
    private RecyclerSwipeDelete swipeMe;
    private ConstraintLayout constraintLayout;
    private EditText edtSearch;
    private String key;
    private InputMethodManager inputMethodManager;
    private Typeface rubikFont;


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
        intentToNote = new Intent(MainActivity.this, NoteActivity.class);
        intentToSearch = new Intent(MainActivity.this, SearchActivity.class);
        model = ViewModelProviders.of(this).get(NotesModel.class);
        adapter = new RecyclerAdapter(this, this);
        swipeMe = new RecyclerSwipeDelete(adapter, model,constraintLayout);
        helper = new ItemTouchHelper(swipeMe);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        edtSearch = findViewById(R.id.search);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        rubikFont = Typeface.createFromAsset(getAssets(), "fonts/Rubik-Regular.ttf");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        helper.attachToRecyclerView(recView);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        model.getAllNotes().observe(this, notes -> adapter.setData(notes));

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fab.setOnClickListener(view -> startActivity(intentToNote));
        navigationView.setNavigationItemSelectedListener(this);

        edtSearch.setTypeface(rubikFont);
        edtSearch.setOnEditorActionListener(this);

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
            case R.id.new_note:
                startActivity(intentToNote);
                break;
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.nav_exit:
                finish();
                System.exit(0);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(int position, int id) {
        intentToNote.putExtra("id", id);
        startActivity(intentToNote);
        intentToNote.removeExtra("id");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE){
            key = edtSearch.getText().toString();

            if (key.isEmpty()){
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),  0);
                edtSearch.clearFocus();
                return true;
            }

            intentToSearch.putExtra("key", "%" + key + "%");
            startActivity(intentToSearch);
            edtSearch.clearFocus();

            return true;
        }
        return false;
    }

}
