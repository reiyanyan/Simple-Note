package com.reiyan.simplenote.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.model.Intros;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Intros> data;
    private View view;
    private TextView txtDesc;
    private ImageView imgDesc;
    private Typeface rubikFont;

    public SliderAdapter(Context context, List<Intros> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        view = LayoutInflater.from(context).inflate(R.layout.intro_item, container, false);
        txtDesc = view.findViewById(R.id.txtDesc);
        imgDesc = view.findViewById(R.id.imgDesc);
        rubikFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rubik-Regular.ttf");

        imgDesc.setImageResource(data.get(position).getImages());
        txtDesc.setText(data.get(position).getDesc());
        txtDesc.setTypeface(rubikFont);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
