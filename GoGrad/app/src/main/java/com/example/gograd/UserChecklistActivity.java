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

        ArrayList<EachCourse> csunit = new ArrayList<>();

        csunit.add(new EachCourse("17/18BCS", "CS1[134]5", false));
        csunit.add(new EachCourse("17/18BCS", "CS1[34]6", false));
        csunit.add(new EachCourse("17/18BCS", "CS 240", false));
        csunit.add(new EachCourse("17/18BCS", "CS 241", false));
        csunit.add(new EachCourse("17/18BCS", "CS 245", false));
        csunit.add(new EachCourse("17/18BCS", "CS 246", false));
        csunit.add(new EachCourse("17/18BCS", "CS 251", false));
        csunit.add(new EachCourse("17/18BCS", "CS 341", false));
        csunit.add(new EachCourse("17/18BCS", "CS 350", false));
        csunit.add(new EachCourse("17/18BCS", "CS 340-398; 440-489", false));
        csunit.add(new EachCourse("17/18BCS", "CS 340-398; 440-489", false));
        csunit.add(new EachCourse("17/18BCS", "CS 340-398; 440-489", false));
        csunit.add(new EachCourse("17/18BCS", "CS 440-489", false));
        csunit.add(new EachCourse("17/18BCS", "CS 440-489", false));
        csunit.add(new EachCourse("17/18BCS", "CS 440-498 or CS 499T or CS 6xx or CS 7xx or CO 487 or STAT 440", false));



        /**
         * view contents
         */



    }

}
