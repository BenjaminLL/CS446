package com.example.gograd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import com.example.gograd.utli.*;

import java.util.ArrayList;
import java.util.List;

public class UserChecklistActivity extends AppCompatActivity {

    private List<Pair<String, ArrayList<EachCourse>>> requiredCourses;
//    private List<Pair<String, ArrayList<String>>> additionalConstraints;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_checklist);

        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        title = intent.getStringExtra(UserPageActivity.TITLE);

        ab.setTitle(title);


        /**
         * fake data
         */
        requiredCourses = new ArrayList<>();

        /**
         * view contents
         */



    }

}
