package com.example.gograd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gograd.utli.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserChecklistActivity extends AppCompatActivity {

    private List<Pair<String, ArrayList<EachCourse>>> requiredCourses;
//    private List<Pair<String, ArrayList<String>>> additionalConstraints;

    private List<Double> unitsNumber;
    private List<Integer> box;
    private List<Integer> nonMBox;
    private Map<String, Integer> numLimit;

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


        System.out.println("here");


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

        unitsNumber = new ArrayList<>();
        unitsNumber.add(7.5);
        unitsNumber.add(3.5);
        unitsNumber.add(3.0);
        unitsNumber.add(4.0);
//
//
//        /**
//         * Useful data for display checklist
//         */
//        // box stores the locations of all containers
//        box = new ArrayList<>();
//        box.add(R.drawable.csbox);
//        box.add(R.drawable.mathbox);
//        box.add(R.drawable.electivebox);
//        // nonMBox only store the containers that are used for non-Math units
//        nonMBox = new ArrayList<>();
//        nonMBox.add(R.drawable.non1box);
//        nonMBox.add(R.drawable.non2box);
//        nonMBox.add(R.drawable.electivebox);
//
//        numLimit = new HashMap<>();
//        numLimit.put("CS Units", 10);
//        numLimit.put("Math Units", 4);
//        numLimit.put("Elective Units", 0);
//
//
//        /**
//         * view contents
//         */
//        LinearLayout linearLayout = findViewById(R.id.results);

//        for (int i = 0; i < requiredCourses.size(); ++i) {
//
//            ConstraintLayout courseUnits = new ConstraintLayout(this);
//            courseUnits.setId(View.generateViewId());
//            linearLayout.addView(courseUnits, i, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT));
//
//            // local variables
//            String name = requiredCourses.get(i).first;
//            int total = requiredCourses.get(i).second.size();
//
//            // initial the yellow flag(circle)
//            ImageView flag = new ImageView(this);
//            flag.setId(View.generateViewId());
//            flag.setImageResource(R.drawable.oval);
//            int flagWidth = dpToPx(26, this);
//            int flagHeight = dpToPx(26, this);
//            courseUnits.addView(flag, new ConstraintLayout.LayoutParams(flagWidth, flagHeight));
//
//
//            // initial the box container
//            ImageView container = new ImageView(this);
//            container.setId(View.generateViewId());
//            //set the image background
//            if (name.equals("Non-Math Units")) {
//                if (total == 2) {
//                    container.setImageResource(nonMBox.get(0));
//                } else if (total > 2){
//                    container.setImageResource(nonMBox.get(1));
//                } else {
//                    container.setImageResource(nonMBox.get(2));
//                }
//            } else {
//                container.setImageResource(box.get(i));
//            }
//
//            courseUnits.addView(container, new ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
//
//            // initial the title and underline
//            TextView title = new TextView(this);
//            title.setId(View.generateViewId());
//            title.setText(requiredCourses.get(i).first + " -- " + unitsNumber.get(i));
//            title.setTextColor(Color.BLACK);
//            title.setTextSize(16.12f);
//            courseUnits.addView(title, new ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
//
//            ImageView underline = new ImageView(this);
//            underline.setId(View.generateViewId());
//            underline.setImageResource(R.drawable.horbar);
////            int underlineWidth = dpToPx(245, this);
//            courseUnits.addView(underline, new ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
//
//            /**
//             * set Constraints
//             */
//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(courseUnits);
//
//            // flag
//            int tmpStartMargin = dpToPx(22, this);
//            int tmpTopMargin = dpToPx(24, this);
//            constraintSet.connect(flag.getId(), ConstraintSet.START, courseUnits.getId(), ConstraintSet.START, tmpStartMargin);
//            constraintSet.connect(flag.getId(), ConstraintSet.TOP, courseUnits.getId(), ConstraintSet.TOP, tmpTopMargin);
//
//            // container
//            int tmpEndMargin = dpToPx(16, this);
////            int tmpBottomMargin = dpToPx(12, this);
//            tmpStartMargin = dpToPx(32, this);
//            constraintSet.connect(container.getId(), ConstraintSet.END, courseUnits.getId(), ConstraintSet.END, tmpEndMargin);
//            constraintSet.connect(container.getId(), ConstraintSet.START, courseUnits.getId(), ConstraintSet.START, tmpStartMargin);
//            constraintSet.connect(container.getId(), ConstraintSet.TOP, flag.getId(), ConstraintSet.BOTTOM, 0);
////            if (i == requiredCourses.size() - 1) {
////                constraintSet.connect(container.getId(), ConstraintSet.BOTTOM, courseUnits.getId(), ConstraintSet.BOTTOM, tmpBottomMargin);
////            }
//            constraintSet.setHorizontalBias(container.getId(), (float) 0.5);
//
//            // title
//            tmpTopMargin = dpToPx(18, this);
//            tmpStartMargin = dpToPx(18, this);
//            constraintSet.connect(title.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, tmpStartMargin);
//            constraintSet.connect(title.getId(), ConstraintSet.TOP, container.getId(), ConstraintSet.TOP, tmpTopMargin);
//
//            // underline
//            tmpStartMargin = dpToPx(14, this);
//            tmpEndMargin = dpToPx(14, this);
//            constraintSet.connect(underline.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, tmpStartMargin);
//            constraintSet.connect(underline.getId(), ConstraintSet.END, container.getId(), ConstraintSet.END, tmpEndMargin);
//            constraintSet.connect(underline.getId(), ConstraintSet.TOP, title.getId(), ConstraintSet.BOTTOM, 0);
//
//            // COURSES
//
//            constraintSet.applyTo(courseUnits);
//
//        }


    }

    public int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = (int) ((dp * displayMetrics.density) + 0.5);
        return px;
    }

}
