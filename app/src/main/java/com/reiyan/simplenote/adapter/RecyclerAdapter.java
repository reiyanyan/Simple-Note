package com.reiyan.simplenote.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.model.Notes;
import com.reiyan.simplenote.util.RecyclerOnClick;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    public List<Notes> data;
    private RecyclerOnClick recyclerOnClick;

    public RecyclerAdapter(Context context, RecyclerOnClick recyclerOnClick) {
        this.context = context;
        this.recyclerOnClick = recyclerOnClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_title);
            txtNote = itemView.findViewById(R.id.txt_note);

            itemView.setOnClickListener(v -> recyclerOnClick.onClick(getAdapterPosition(), data.get(getAdapterPosition()).getId()));
        }
    }

    public void setData(List<Notes> notes){
        this.data = notes;
        notifyDataSetChanged();
    }

    public Notes getNoteAt(int position){
        return data.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText(data.get(i).getTitle());
        viewHolder.txtNote.setText(data.get(i).getNote());
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        else
            return data.size();
    }

}
