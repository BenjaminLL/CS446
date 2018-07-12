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
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.gograd.utli.*;

public class SearchResultActivity extends AppCompatActivity {

    private List<Pair<String, ArrayList<String>>> requiredCourses;
    private List<Pair<String, ArrayList<String>>> additionalConstraints;
    private List<Double> unitsNumber;
    private Map<String, TextView> courseViews;
    private List<Integer> box;
    private List<Integer> nonMBox;
    private Map<String, Integer> numLimit;

    private String fullProgramName;
    private SplitString translator;

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

        // get the program name from ProgramSelectionActivity
        Intent intent = getIntent();
        fullProgramName = intent.getStringExtra(OptionSelectionActivity.PROGRAM);
        System.out.println("Program: " + fullProgramName);

        translator = new SplitString(fullProgramName, this);
        requiredCourses = translator.getCourse();
        unitsNumber = translator.getCourseUnits();

        additionalConstraints = translator.getConstraints();
//        for (int i = 0; i < additionalConstraints.size(); ++i) {
//            String name = additionalConstraints.get(i).first;
//            ArrayList<String> values = additionalConstraints.get(i).second;
//            System.out.println(name);
//            for (int j = 0; j < values.size(); ++j) {
//                System.out.println(values.get(j));
//            }
//        }


        // initial the courseViews that store all courses
        courseViews = new HashMap<>();

        // box stores the locations of all containers
        box = new ArrayList<>();
        box.add(R.drawable.csbox);
        box.add(R.drawable.mathbox);
        box.add(R.drawable.electivebox);
        // nonMBox only store the containers that are used for non-Math units
        nonMBox = new ArrayList<>();
        nonMBox.add(R.drawable.non1box);
        nonMBox.add(R.drawable.non2box);
        nonMBox.add(R.drawable.electivebox);

        numLimit = new HashMap<>();
        numLimit.put("CS Units", 10);
        numLimit.put("Math Units", 4);
        numLimit.put("Elective Units", 0);

        /**
         * display
         */
        // root view
        ConstraintLayout rootView = findViewById(R.id.userPage);
        LinearLayout linearLayout = findViewById(R.id.results);



        /**
         * display the required courses
         */
        for (int i = 0; i < requiredCourses.size(); ++i) {

            ConstraintLayout courseUnits = new ConstraintLayout(this);
            courseUnits.setId(View.generateViewId());
            linearLayout.addView(courseUnits, i, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));

            // local variables
            String name = requiredCourses.get(i).first;
            int total = requiredCourses.get(i).second.size();


            // initial the yellow flag(circle)
            ImageView flag = new ImageView(this);
            flag.setId(View.generateViewId());
            flag.setImageResource(R.drawable.oval);
            int flagWidth = dpToPx(26, this);
            int flagHeight = dpToPx(26, this);
            courseUnits.addView(flag, new ConstraintLayout.LayoutParams(flagWidth, flagHeight));

            // initial the box container
            ImageView container = new ImageView(this);
            container.setId(View.generateViewId());
                //set the image background
            if (name.equals("Non-Math Units")) {
                if (total == 2) {
                    container.setImageResource(nonMBox.get(0));
                } else if (total > 2){
                    container.setImageResource(nonMBox.get(1));
                } else {
                    container.setImageResource(nonMBox.get(2));
                }
            } else {
                container.setImageResource(box.get(i));
            }

            courseUnits.addView(container, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));


            // initial the title and underline
            TextView title = new TextView(this);
            title.setId(View.generateViewId());
            title.setText(requiredCourses.get(i).first + " -- " + unitsNumber.get(i));
            title.setTextColor(Color.BLACK);
            title.setTextSize(16.12f);
            courseUnits.addView(title, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

            ImageView underline = new ImageView(this);
            underline.setId(View.generateViewId());
            underline.setImageResource(R.drawable.horbar);
//            int underlineWidth = dpToPx(245, this);
            courseUnits.addView(underline, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));


            // initial the courses
            List<Pair<ImageView, TextView>> courseList1 = new ArrayList<>();
            List<Pair<ImageView, TextView>> courseList2 = new ArrayList<>();

            int count = 0;
            ArrayList<String> courses = requiredCourses.get(i).second;
            for (int j = 0; j < courses.size(); ++j) {

                final String text = courses.get(j);
                TextView course = new TextView(this);
                course.setId(View.generateViewId());
                course.setText(text);
                course.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                course.setTextColor(Color.BLACK);
                course.setGravity(Gravity.CENTER_VERTICAL);
                int textWidth = dpToPx(110, this);
                courseUnits.addView(course, new ConstraintLayout.LayoutParams(
                        textWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                ImageView bulletPoint = new ImageView(this);
                bulletPoint.setId(View.generateViewId());
                bulletPoint.setImageResource(R.drawable.oval);
                int ovalSize = dpToPx(10, this);
                courseUnits.addView(bulletPoint, new ConstraintLayout.LayoutParams(ovalSize, ovalSize));

                // get the number of courses should be placed on the first column
                int firstColumnCount;

                if (name.equals("Non-Math Units")) {
                    firstColumnCount = total % 2 == 0? total / 2: total / 2 + 1;
                } else {
                    firstColumnCount = numLimit.get(name);
                }

                if (count < firstColumnCount) {
                    courseList1.add(new Pair<>(bulletPoint, course));
                    ++count;
                } else {
                    courseList2.add(new Pair<>(bulletPoint, course));
                }

                courseViews.put(text, course);
                course.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = text;
                        showDescription(message);
                    }
                });

            }

            /**
             * set Constraints
             */
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(courseUnits);

            // flag
            int tmpStartMargin = dpToPx(22, this);
            int tmpTopMargin = dpToPx(24, this);
            constraintSet.connect(flag.getId(), ConstraintSet.START, courseUnits.getId(), ConstraintSet.START, tmpStartMargin);
            constraintSet.connect(flag.getId(), ConstraintSet.TOP, courseUnits.getId(), ConstraintSet.TOP, tmpTopMargin);

            // container
            int tmpEndMargin = dpToPx(16, this);
//            int tmpBottomMargin = dpToPx(12, this);
            tmpStartMargin = dpToPx(32, this);
            constraintSet.connect(container.getId(), ConstraintSet.END, courseUnits.getId(), ConstraintSet.END, tmpEndMargin);
            constraintSet.connect(container.getId(), ConstraintSet.START, courseUnits.getId(), ConstraintSet.START, tmpStartMargin);
            constraintSet.connect(container.getId(), ConstraintSet.TOP, flag.getId(), ConstraintSet.BOTTOM, 0);
//            if (i == requiredCourses.size() - 1) {
//                constraintSet.connect(container.getId(), ConstraintSet.BOTTOM, courseUnits.getId(), ConstraintSet.BOTTOM, tmpBottomMargin);
//            }
            constraintSet.setHorizontalBias(container.getId(), (float) 0.5);

            // title
            tmpTopMargin = dpToPx(18, this);
            tmpStartMargin = dpToPx(18, this);
            constraintSet.connect(title.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, tmpStartMargin);
            constraintSet.connect(title.getId(), ConstraintSet.TOP, container.getId(), ConstraintSet.TOP, tmpTopMargin);

            // underline
            tmpStartMargin = dpToPx(14, this);
            tmpEndMargin = dpToPx(14, this);
            constraintSet.connect(underline.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, tmpStartMargin);
            constraintSet.connect(underline.getId(), ConstraintSet.END, container.getId(), ConstraintSet.END, tmpEndMargin);
            constraintSet.connect(underline.getId(), ConstraintSet.TOP, title.getId(), ConstraintSet.BOTTOM, 0);

            // COURSES
            int list1StartMargin = dpToPx(17, this);
            int list2StartMargin = dpToPx(145, this);
            int bulletToTextH = dpToPx(8, this);
            int bulletToTextV = dpToPx(7, this);
            int margin = dpToPx(5, this);

            for (int j = 0; j < courseList1.size(); ++j) {

                ImageView tmpBullet = courseList1.get(j).first;
                TextView tmpCourse = courseList1.get(j).second;

                if (j == 0) {
                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, underline.getId(), ConstraintSet.BOTTOM, margin);
                } else {
                    TextView preCourse = courseList1.get(j - 1).second;
                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, preCourse.getId(), ConstraintSet.BOTTOM, margin);
                }
                constraintSet.connect(tmpBullet.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, list1StartMargin);
                constraintSet.connect(tmpBullet.getId(), ConstraintSet.TOP, tmpCourse.getId(), ConstraintSet.TOP, bulletToTextV);
                constraintSet.connect(tmpCourse.getId(), ConstraintSet.LEFT, tmpBullet.getId(), ConstraintSet.RIGHT, bulletToTextH);

            }

            for (int j = 0; j < courseList2.size(); ++j) {

                ImageView tmpBullet = courseList2.get(j).first;
                TextView tmpCourse = courseList2.get(j).second;

                if (j == 0) {
                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, underline.getId(), ConstraintSet.BOTTOM, margin);
                } else {
                    TextView preCourse = courseList2.get(j - 1).second;
                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, preCourse.getId(), ConstraintSet.BOTTOM, margin);
                }
                constraintSet.connect(tmpBullet.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, list2StartMargin);
                constraintSet.connect(tmpBullet.getId(), ConstraintSet.TOP, tmpCourse.getId(), ConstraintSet.TOP, bulletToTextV);
                constraintSet.connect(tmpCourse.getId(), ConstraintSet.LEFT, tmpBullet.getId(), ConstraintSet.RIGHT, bulletToTextH);

            }


            constraintSet.applyTo(courseUnits);

        }


        /**
         * fake data
         */
//        additionalConstraints = new ArrayList<>();
//
//        ArrayList<String> data1 = new ArrayList<>();
//        data1.add("CS 452, CS 454, CS 456, CS 457");
//        data1.add("One of CS 343, 349, 442, 444, 445, 446, 447, 450, 452, 454, 456, 457, 458");
//        ArrayList<String> data2 = new ArrayList<>();
//        data2.add("One of CS 343, 349, 442, 444, 445, 446, 447, 450, 452, 454, 456, 457, 458");
//        data2.add("One of CS 348, 448, 449, 473, 476, 482, 483, 484, 485, 486, 488");
//        data2.add("One of CS 360, 365, 370, 371, 462, 466, 467, 475, 487");
//
//        ArrayList<String> bottomS = new ArrayList<>();
//        bottomS.add("Seven (regular) or eight (co-op) terms enrolled in at least three courses totaling 1.5 units");
//        bottomS.add("No more than 2.0 units of failed courses");
//        bottomS.add("No more than 5.0 units of unusable course attempts (failures and repeats of passed courses)");
//        bottomS.add("CS major average of 60% or higher");
//        bottomS.add("Cumulative average of 60% or higher");
//        bottomS.add("Co-op requirements met, if applicable, including PD 1, PD 11, PD 10, and a minimum of two other PD courses.");
//        additionalConstraints.add(new Pair<>("Two of:", data1));
//        additionalConstraints.add(new Pair<>("Two of:", data2));
//        additionalConstraints.add(new Pair<>("Bottom", bottomS));



        /**
         * display the additional constraints
         */
        ConstraintLayout addConstraintBox = new ConstraintLayout(this);
        addConstraintBox.setId(View.generateViewId());
        linearLayout.addView(addConstraintBox, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        // initial the yellow flag(circle)
        ImageView flag = new ImageView(this);
        flag.setId(View.generateViewId());
        flag.setImageResource(R.drawable.oval);
        int flagWidth = dpToPx(26, this);
        int flagHeight = dpToPx(26, this);
        addConstraintBox.addView(flag, new ConstraintLayout.LayoutParams(flagWidth, flagHeight));

        // initial the box container
        ImageView container = new ImageView(this);
        container.setId(View.generateViewId());
            //set the image background
        container.setImageResource(R.drawable.adcons1);
        container.setVisibility(View.INVISIBLE);
        addConstraintBox.addView(container, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));



        // initial the title and underline
        TextView title = new TextView(this);
        title.setId(View.generateViewId());
        title.setText("Additional Constraints");
        title.setTextColor(Color.BLACK);
        title.setTextSize(16.12f);
        addConstraintBox.addView(title, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

        ImageView underline = new ImageView(this);
        underline.setId(View.generateViewId());
        underline.setImageResource(R.drawable.horbar);
        addConstraintBox.addView(underline, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(addConstraintBox);

        // flag
        int tmpStartMargin = dpToPx(22, this);
        int tmpTopMargin = dpToPx(24, this);
        constraintSet.connect(flag.getId(), ConstraintSet.START, addConstraintBox.getId(), ConstraintSet.START, tmpStartMargin);
        constraintSet.connect(flag.getId(), ConstraintSet.TOP, addConstraintBox.getId(), ConstraintSet.TOP, tmpTopMargin);

        // container
        int tmpEndMargin = dpToPx(16, this);
        tmpStartMargin = dpToPx(32, this);
        constraintSet.connect(container.getId(), ConstraintSet.END, addConstraintBox.getId(), ConstraintSet.END, tmpEndMargin);
        constraintSet.connect(container.getId(), ConstraintSet.START, addConstraintBox.getId(), ConstraintSet.START, tmpStartMargin);
        constraintSet.connect(container.getId(), ConstraintSet.TOP, flag.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.setHorizontalBias(container.getId(), (float) 0.5);

        // title
        tmpTopMargin = dpToPx(18, this);
        tmpStartMargin = dpToPx(18, this);
        constraintSet.connect(title.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, tmpStartMargin);
        constraintSet.connect(title.getId(), ConstraintSet.TOP, container.getId(), ConstraintSet.TOP, tmpTopMargin);

        // underline
        tmpStartMargin = dpToPx(14, this);
        tmpEndMargin = dpToPx(14, this);
        constraintSet.connect(underline.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, tmpStartMargin);
        constraintSet.connect(underline.getId(), ConstraintSet.END, container.getId(), ConstraintSet.END, tmpEndMargin);
        constraintSet.connect(underline.getId(), ConstraintSet.TOP, title.getId(), ConstraintSet.BOTTOM, 0);

        constraintSet.applyTo(addConstraintBox);


        /**
         * add contents
         */
        int level = 0;
        TextView preText = new TextView(this);

        for (int i = 0; i < additionalConstraints.size(); ++i) {

            String cat = additionalConstraints.get(i).first;

            List<Pair<ImageView, TextView>> levelOneText = new ArrayList<>();
            List<Pair<ImageView, TextView>> levelTwoText = new ArrayList<>();
            List<List<Pair<ImageView, TextView>>>  contents = new ArrayList<>();
            contents.add(levelOneText);
            contents.add(levelTwoText);

            if (!cat.equals("bottom")) {
                // Bullet Point
                ImageView bulletPoint = new ImageView(this);
                bulletPoint.setId(View.generateViewId());
                bulletPoint.setImageResource(R.drawable.oval);
                int ovalSize = dpToPx(10, this);
                addConstraintBox.addView(bulletPoint, new ConstraintLayout.LayoutParams(ovalSize, ovalSize));

                // TextView
                TextView info = new TextView(this);
                info.setId(View.generateViewId());
                info.setText(cat);
                info.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                info.setTextColor(Color.BLACK);
                info.setGravity(Gravity.CENTER_VERTICAL);
                int textWidth = dpToPx(200, this);
                addConstraintBox.addView(info, new ConstraintLayout.LayoutParams(
                        textWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                contents.get(level).add(new Pair<>(bulletPoint, info));
                ++level;

            }

            ArrayList<String> tmpContents = additionalConstraints.get(i).second;

            for (int j = 0; j < tmpContents.size(); ++j) {

                // Bullet Point
                ImageView bulletPoint = new ImageView(this);
                bulletPoint.setId(View.generateViewId());
                bulletPoint.setImageResource(R.drawable.oval);
                int ovalSize = dpToPx(10, this);
                addConstraintBox.addView(bulletPoint, new ConstraintLayout.LayoutParams(ovalSize, ovalSize));

                // TextView
                TextView info = new TextView(this);
                info.setId(View.generateViewId());
                info.setText(tmpContents.get(j));
                info.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                info.setTextColor(Color.BLACK);
                info.setGravity(Gravity.CENTER_VERTICAL);
                int textWidth = dpToPx(200, this);
                addConstraintBox.addView(info, new ConstraintLayout.LayoutParams(
                        textWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                // added
                contents.get(level).add(new Pair<>(bulletPoint, info));
            }

            ConstraintSet ContentConstraintSet = new ConstraintSet();
            ContentConstraintSet.clone(addConstraintBox);


            for (int k = 0; k <= level; ++k) {

                List<Pair<ImageView, TextView>> levelText = contents.get(k);
                List<Integer> startMargin = new ArrayList<>();
                startMargin.add(dpToPx(17, this));
                startMargin.add(dpToPx(50, this));

                for (int j = 0; j < levelText.size(); ++j) {
                    ImageView tmpBl = levelText.get(j).first;
                    TextView tmpCon = levelText.get(j).second;

                    int bulletToTextH = dpToPx(8, this);
                    int bulletToTextV = dpToPx(7, this);
                    int margin = dpToPx(5, this);

                    // bullet Point
                    ContentConstraintSet.connect(tmpBl.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, startMargin.get(k));
                    ContentConstraintSet.connect(tmpBl.getId(), ConstraintSet.TOP, tmpCon.getId(), ConstraintSet.TOP, bulletToTextV);

                    // text
                    ContentConstraintSet.connect(tmpCon.getId(), ConstraintSet.START, tmpBl.getId(), ConstraintSet.END, bulletToTextH);
                    if (j == 0) {
                        if (i == 0 && k == 0) {
                            ContentConstraintSet.connect(tmpCon.getId(), ConstraintSet.TOP, underline.getId(), ConstraintSet.BOTTOM, margin);
                        } else {
                            ContentConstraintSet.connect(tmpCon.getId(), ConstraintSet.TOP, preText.getId(), ConstraintSet.BOTTOM, margin);
                        }
                    } else {
                        TextView preCon = levelText.get(j - 1).second;
                        ContentConstraintSet.connect(tmpCon.getId(), ConstraintSet.TOP, preCon.getId(), ConstraintSet.BOTTOM, margin);
                    }

                    if (i == additionalConstraints.size() - 1 && k == level && j == levelText.size() - 1) {

                        ContentConstraintSet.connect(tmpCon.getId(), ConstraintSet.BOTTOM, addConstraintBox.getId(), ConstraintSet.BOTTOM, 100);
                    }

                    if (j == levelText.size() - 1) {
                        preText = tmpCon;
                    }
                }
            }

            level = 0;

            ContentConstraintSet.applyTo(addConstraintBox);

        }

    }


    public int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = (int) ((dp * displayMetrics.density) + 0.5);
        return px;
    }

    public void showDescription(String text) {
        Intent description = new Intent(this, DescriptionActivity.class);
        startActivity(description);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(1, intent);
    }
}
