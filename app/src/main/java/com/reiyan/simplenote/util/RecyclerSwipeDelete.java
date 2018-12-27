package com.reiyan.simplenote.util;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.reiyan.simplenote.adapter.RecyclerAdapter;
import com.reiyan.simplenote.model.NotesModel;


public class RecyclerSwipeDelete extends ItemTouchHelper.SimpleCallback {

    private RecyclerAdapter adapter;
    private NotesModel model;
    private View layout;
    private Snackbar snackbar;
    int recentylyDeletePostion;

    public RecyclerSwipeDelete(RecyclerAdapter adapter, NotesModel model, View layout) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.model = model;
        this.layout = layout;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        recentylyDeletePostion = viewHolder.getAdapterPosition();
        model.deleteData(adapter.getNoteAt(viewHolder.getAdapterPosition()));
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        snackbar = Snackbar.make(layout, "Note Deleted ", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> model.inserData(adapter.getNoteAt(viewHolder.getAdapterPosition())));
        snackbar.show();
    }

}
