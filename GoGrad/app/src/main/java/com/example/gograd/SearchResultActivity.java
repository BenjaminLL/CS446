package com.example.gograd;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        ConstraintLayout rootView = findViewById(R.id.userPage);

//        ImageView imageView = new ImageView(this);
//        Drawable box = getResources().getDrawable(R.drawable.rectangle);
//        ScaleDrawable sd = new ScaleDrawable(box, 0, 280, 400);
//        imageView.setImageResource(sd);

    }
}
