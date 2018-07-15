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
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

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

        /**
         * display the course description
         */
        ConstraintLayout rootView = findViewById(R.id.description);
        Toolbar toolbar = findViewById(R.id.my_toolbar);


        ImageView flag = new ImageView(this);
        flag.setId(View.generateViewId());
        flag.setImageResource(R.drawable.oval);
        int flagWidth = dpToPx(20, this);
        int flagHeight = dpToPx(20, this);
        rootView.addView(flag, new ConstraintLayout.LayoutParams(flagWidth, flagHeight));

        TextView title = new TextView(this);
        title.setId(View.generateViewId());
        title.setText(courseName);
        title.setTextColor(Color.BLACK);
        title.setTextSize(16.12f);
        rootView.addView(title, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        TextView description = new TextView(this);
        description.setId(View.generateViewId());
        String desText = "<b> Description: </b>" + "CS 135 is for students who would prefer " +
        "a more conceptual treatment of introductory computer science in a " +
                "simple language that is educationally effective but not commercially relevant. " +
                "While the course is designed to be taken by those with no prior programming experience, " +
                "students with prior experience will also find it relevant, due to its unusual focus. " +
                "It is suitable for both CS majors and non-majors.";
        description.setText(Html.fromHtml(desText));
        description.setTextColor(Color.BLACK);
        description.setTextSize(14.1f);
        rootView.addView(description, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        TextView preRequest = new TextView(this);
        preRequest.setId(View.generateViewId());
        String antiText = "<b> Prerequisites: </b>" + "CS 115, 121, 122, 123, 125, 131, 132, 133, 134, 137, 138, 145," +
                " CHE 121, CIVE 121, ECE 150, GENE 121, PHYS 139, SYDE 121";
        preRequest.setText(Html.fromHtml(antiText));
        preRequest.setTextColor(Color.BLACK);
        preRequest.setTextSize(14.1f);
        rootView.addView(preRequest, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));


        TextView coReqest = new TextView(this);
        coReqest.setId(View.generateViewId());
        String succText = "<b> Successor: </b>" + "CS 136";
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
        constraintSet.connect(flag.getId(), ConstraintSet.TOP, toolbar.getId(), ConstraintSet.BOTTOM, topMargin);

        // title
        topMargin = dpToPx(22, this);
        leftMargin = dpToPx(8, this);
        constraintSet.connect(title.getId(), ConstraintSet.LEFT, flag.getId(), ConstraintSet.RIGHT, leftMargin);
        constraintSet.connect(title.getId(), ConstraintSet.TOP, toolbar.getId(), ConstraintSet.BOTTOM, topMargin);

        // description
        topMargin = dpToPx(8, this);
        int rightMargin = dpToPx(24, this);
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

        constraintSet.applyTo(rootView);

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
