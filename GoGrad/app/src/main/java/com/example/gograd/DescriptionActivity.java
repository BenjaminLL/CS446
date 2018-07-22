package com.example.gograd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gograd.util.*;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    private CourseDescriptions courseDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String courseName = intent.getStringExtra(SearchResultActivity.COURSE_NAME);

        ab.setTitle(courseName);

        courseDescriptions = new CourseDescriptions(this, courseName);
        ArrayList<CourseDescriptions.Content> courses = courseDescriptions.getListOfDescriptions();



        /**
         * display the course description
         */
        LinearLayout linearLayout = findViewById(R.id.list);
        ConstraintLayout rootView = new ConstraintLayout(this);
        rootView.setId(View.generateViewId());
        linearLayout.addView(rootView, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        boolean first = true;
        TextView preBottom = new TextView(this);


        for (int i = 0; i < courses.size(); ++i) {

            // local variables
            CourseDescriptions.Content tmpCourse = courses.get(i);
            String name = tmpCourse.getCourseName();
            String fulltitle = tmpCourse.getT();
            String des = tmpCourse.getD();
            String pre = tmpCourse.getPrereq();
            String anti = tmpCourse.getCoreq();


            ImageView flag = new ImageView(this);
            flag.setId(View.generateViewId());
            flag.setImageResource(R.drawable.oval);
            int flagWidth = dpToPx(20, this);
            int flagHeight = dpToPx(20, this);
            rootView.addView(flag, new ConstraintLayout.LayoutParams(flagWidth, flagHeight));

            TextView title = new TextView(this);
            title.setId(View.generateViewId());
            title.setText(name + " (" + fulltitle + ")");
            title.setTextColor(Color.BLACK);
            title.setTextSize(16.12f);
            rootView.addView(title, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));

            TextView description = new TextView(this);
            description.setId(View.generateViewId());
            String desText = "<b> Description: </b>" + des;
            description.setText(Html.fromHtml(desText));
            description.setTextColor(Color.BLACK);
            description.setTextSize(14.1f);
            rootView.addView(description, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));

            TextView preRequest = new TextView(this);
            preRequest.setId(View.generateViewId());
            String antiText = "<b> Prerequisites: </b>" + pre;
            preRequest.setText(Html.fromHtml(antiText));
            preRequest.setTextColor(Color.BLACK);
            preRequest.setTextSize(14.1f);
            rootView.addView(preRequest, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));


            TextView coReqest = new TextView(this);
            coReqest.setId(View.generateViewId());
            String succText = "<b> Successors: </b>" + anti;
            coReqest.setText(Html.fromHtml(succText));
            coReqest.setTextColor(Color.BLACK);
            coReqest.setTextSize(14.1f);
            rootView.addView(coReqest, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));


            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootView);

            // flag
            int topMargin = dpToPx(24, this);
            int leftMargin = dpToPx(16, this);
            constraintSet.connect(flag.getId(), ConstraintSet.START, rootView.getId(), ConstraintSet.START, leftMargin);
            if (first) {
                constraintSet.connect(flag.getId(), ConstraintSet.TOP, rootView.getId(), ConstraintSet.TOP, topMargin);
            } else {
                constraintSet.connect(flag.getId(), ConstraintSet.TOP, preBottom.getId(), ConstraintSet.BOTTOM, topMargin);
            }

            // title
            int rightMargin = dpToPx(16, this);
            topMargin = dpToPx(22, this);
            leftMargin = dpToPx(8, this);
            constraintSet.connect(title.getId(), ConstraintSet.LEFT, flag.getId(), ConstraintSet.RIGHT, leftMargin);
            constraintSet.connect(title.getId(), ConstraintSet.RIGHT, rootView.getId(), ConstraintSet.RIGHT, rightMargin);
            if (first) {
                constraintSet.connect(title.getId(), ConstraintSet.TOP, rootView.getId(), ConstraintSet.TOP, topMargin);
            } else {
                constraintSet.connect(title.getId(), ConstraintSet.TOP, preBottom.getId(), ConstraintSet.BOTTOM, topMargin);
            }

            // description
            rightMargin = dpToPx(24, this);
            topMargin = dpToPx(8, this);
            constraintSet.connect(description.getId(), ConstraintSet.LEFT, title.getId(), ConstraintSet.LEFT, 0);
            constraintSet.connect(description.getId(), ConstraintSet.RIGHT, rootView.getId(), ConstraintSet.RIGHT, rightMargin);
            constraintSet.connect(description.getId(), ConstraintSet.TOP, title.getId(), ConstraintSet.BOTTOM, topMargin);

            // preRequest
            constraintSet.connect(preRequest.getId(), ConstraintSet.LEFT, title.getId(), ConstraintSet.LEFT, 0);
            constraintSet.connect(preRequest.getId(), ConstraintSet.RIGHT, rootView.getId(), ConstraintSet.RIGHT, rightMargin);
            constraintSet.connect(preRequest.getId(), ConstraintSet.TOP, description.getId(), ConstraintSet.BOTTOM, topMargin);

            // coReqest
            constraintSet.connect(coReqest.getId(), ConstraintSet.LEFT, title.getId(), ConstraintSet.LEFT, 0);
            constraintSet.connect(coReqest.getId(), ConstraintSet.RIGHT, rootView.getId(), ConstraintSet.RIGHT, rightMargin);
            constraintSet.connect(coReqest.getId(), ConstraintSet.TOP, preRequest.getId(), ConstraintSet.BOTTOM, topMargin);

            if (i == courses.size() - 1) {
                int bottomMargin = dpToPx(20, this);
                constraintSet.connect(coReqest.getId(), ConstraintSet.BOTTOM, rootView.getId(), ConstraintSet.BOTTOM, bottomMargin);
            }

            if (first) {
                first = false;
            }
            preBottom = coReqest;
            constraintSet.applyTo(rootView);
        }

    }


    public int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = (int) ((dp * displayMetrics.density) + 0.5);
        return px;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
