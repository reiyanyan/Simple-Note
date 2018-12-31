package com.reiyan.simplenote.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reiyan.simplenote.R;
import com.reiyan.simplenote.adapter.SliderAdapter;
import com.reiyan.simplenote.model.Intros;
import com.reiyan.simplenote.util.SharedHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager introViewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private SliderAdapter adapter;
    private List<Intros> data;
    private Button btnBack, btnNext;
    private int currentPage;
    private SharedHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        introViewPager = findViewById(R.id.introViewPager);
        dotsLayout = findViewById(R.id.dotsLayout);
        data = new ArrayList<>();
        adapter = new SliderAdapter(this, data);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        helper = new SharedHelper(this);

        if (helper.isIntroDone() == 1) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        }

        data.add(new Intros(R.drawable.ic_about, "Cupcake ipsum dolor sit amet fruitcake candy cotton candy. Cake dessert macaroon. Toffee croissant I love ice cream"));
        data.add(new Intros(R.drawable.ic_checked, "Cupcake ipsum dolor sit amet fruitcake candy cotton candy. Cake dessert macaroon. Toffee croissant I love ice cream"));
        data.add(new Intros(R.drawable.ic_exit, "Cupcake ipsum dolor sit amet fruitcake candy cotton candy. Cake dessert macaroon. Toffee croissant I love ice cream"));

        introViewPager.setAdapter(adapter);
        introViewPager.addOnPageChangeListener(this);
        addDotsIndicator(0);

        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void addDotsIndicator(int position){

        dots = new TextView[data.size()];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(this.getColor(R.color.transparentWhite));

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[position].setTextColor(this.getColor(android.R.color.white));

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        addDotsIndicator(i);
        currentPage = i;

        if (i == 0){
            btnNext.setEnabled(true);
            btnBack.setEnabled(false);
            btnBack.setVisibility(View.INVISIBLE);

            btnNext.setText("Next");
        } else if (i == dots.length - 1) {
            btnNext.setEnabled(true);
            btnBack.setEnabled(true);
            btnBack.setVisibility(View.VISIBLE);

            btnNext.setText("Finish");
            btnBack.setText("Back");
        } else {
            btnNext.setEnabled(true);
            btnBack.setEnabled(true);
            btnBack.setVisibility(View.VISIBLE);

            btnNext.setText("Next");
            btnBack.setText("Back");
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                if (currentPage + 1 < dots.length)
                    introViewPager.setCurrentItem(currentPage + 1);
                else
                    launchMainActivity();
                break;
            case R.id.btnBack:
                introViewPager.setCurrentItem(currentPage - 1);
                break;
        }
    }

    private void launchMainActivity() {
        helper.setFirsLaunchDone(1);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }
}
