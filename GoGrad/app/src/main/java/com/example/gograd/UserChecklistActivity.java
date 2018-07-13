package com.example.gograd;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;

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
        ArrayList<EachCourse> mathunit = new ArrayList<>();
        ArrayList<EachCourse> electiveunit = new ArrayList<>();
        ArrayList<EachCourse> nonmathunit = new ArrayList<>();

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


        mathunit.add(new EachCourse("17/18BCS", "MATH 1[34]5", false));
        mathunit.add(new EachCourse("17/18BCS", "MATH 1[34]6", false));
        mathunit.add(new EachCourse("17/18BCS", "MATH 1[234]7", false));
        mathunit.add(new EachCourse("17/18BCS", "MATH 1[234]8", false));
        mathunit.add(new EachCourse("17/18BCS", "MATH 2[34]9", false));
        mathunit.add(new EachCourse("17/18BCS", "STAT 2[34]0", false));
        mathunit.add(new EachCourse("17/18BCS", "STAT 2[34]1", false));

        nonmathunit.add(new EachCourse("17/18BCS", "MATH 2[34]9", false));
        nonmathunit.add(new EachCourse("17/18BCS", "STAT 2[34]0", false));
        nonmathunit.add(new EachCourse("17/18BCS", "STAT 2[34]1", false));

        requiredCourses.add(new Pair<>("CS Units", csunit));
        requiredCourses.add(new Pair<>("Math Units", mathunit));
        requiredCourses.add(new Pair<>("Elective Units", electiveunit));
        requiredCourses.add(new Pair<>("Non-Math Units", nonmathunit));

        /**
         * view contents
         */
//        LinearLayout linearLayout = findViewById(R.id.results);
//        
//        for (int i = 0; i < requiredCourses.size(); ++i) {
//
//            ConstraintLayout courseUnits = new ConstraintLayout(this);
//            courseUnits.setId(View.generateViewId());
//            linearLayout.addView(courseUnits, i, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
//
//        }


    }

}
