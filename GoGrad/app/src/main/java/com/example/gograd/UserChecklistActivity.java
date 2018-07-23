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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gograd.util.*;
import com.example.gograd.util.SuggestedCoursesModel;
import com.example.gograd.util.constraints.Constraints;
import com.example.gograd.util.constraints.EachConstraintsChild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserChecklistActivity extends AppCompatActivity {

    final public static String COURSE_NAME = "null";

    private EachChecklist checklist;
    private List<Constraints> constraints;
    private List<Pair<String, ArrayList<EachCourse>>> requiredCourses;

    private Map<String, List<Pair<String, CheckBox>>> checkBoxR;
    private Map<String, List<Pair<EditText, CheckBox>>> checkBoxU;
    private List<Double> unitsNumber;
    private List<Integer> box;
    private List<Integer> electiveBox;
    private List<Integer> nonMBox;
    private Map<String, Integer> numLimit;
    private List<CheckBox> constraintsData;

    private ModifyPlan modifyPlan;

    private String title;
    LinearLayout linearLayout;
    private View currEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_checklist);

        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        deleteChecklist();
                        break;
                }
                return true;
            }
        });

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        title = intent.getStringExtra(UserPageActivity.TITLE);
        final String programName = title;
        final Context context = this;

        ab.setTitle(title);

        /**
         * initial global variables
         */
        modifyPlan = new ModifyPlan(title,this);
        checkBoxR = new HashMap<>();
        checkBoxU = new HashMap<>();
        constraintsData = new ArrayList<>();

        /**
         * get checklist from user database
         */
        checklist = new EachChecklist(title, this);
        requiredCourses = checklist.getCourses();
        constraints = checklist.getConstraints();
        unitsNumber = checklist.getCourseUnits();


        /**
         * Useful data for display checklist
         */
        // box stores the locations of all containers
        box = new ArrayList<>();
        box.add(R.drawable.csboxche);
        box.add(R.drawable.mathbox);

        electiveBox = new ArrayList<>();
        electiveBox.add(R.drawable.electivebox3);
        electiveBox.add(R.drawable.electivebox4);

        // nonMBox only store the containers that are used for non-Math units
        nonMBox = new ArrayList<>();
        nonMBox.add(R.drawable.non2box);
        nonMBox.add(R.drawable.nonbox6);
        nonMBox.add(R.drawable.nonbox7);

        numLimit = new HashMap<>();
        numLimit.put("CS Units", 10);
        numLimit.put("Math Units", 4);


        /**
         * view contents
         */
        linearLayout = findViewById(R.id.results);

        for (int i = 0; i < requiredCourses.size(); ++i) {

            ConstraintLayout courseUnits = new ConstraintLayout(this);
            courseUnits.setId(View.generateViewId());
            linearLayout.addView(courseUnits, i, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));

            // local variables
            String name = requiredCourses.get(i).first;
            double total = unitsNumber.get(i);

            if (!name.equals("Elective Units")) {
                checkBoxR.put(name, new ArrayList<Pair<String, CheckBox>>());
                if (name.equals("Non-Math Units")) {
                    checkBoxU.put(name, new ArrayList<Pair<EditText, CheckBox>>());
                }
            } else {
                checkBoxU.put(name, new ArrayList<Pair<EditText, CheckBox>>());
            }

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

                if (total <= 5.0) {
                    container.setImageResource(nonMBox.get(0));
                } else if (total <= 6.0) {
                    container.setImageResource(nonMBox.get(1));
                } else {
                    container.setImageResource(nonMBox.get(2));
                }

            } else if (name.equals("Elective Units")){

                if (total <= 3.0) {
                    container.setImageResource(electiveBox.get(0));
                } else {
                    container.setImageResource(electiveBox.get(1));
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
            courseUnits.addView(underline, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));


            // initial the courses
            List<Pair<CheckBox, View>> courseList1 = new ArrayList<>();
            List<Pair<CheckBox, View>> courseList2 = new ArrayList<>();
            int posList1ForEdit = 0; // indicate the position of editText that user enters on each column
            int posList2ForEdit = 0;
            boolean swichColumnForNonMath = true;


            int count = 0;
            final ArrayList<EachCourse> courses = requiredCourses.get(i).second;
            final int filled = courses.size();
            int totalCourses = (int) (total * 2);

            System.out.println(name + ": " + filled);

            for (int j = 0; j < totalCourses; ++j) {

                TextView course = new TextView(this);
                EditText editText = new EditText(this);

                boolean insertedByUser = false;

                // there are two types of course: 1. required course(TextView) 2. elective course(EditView)
                if (j < filled && courses.get(j).getIsOrigin()) {

                    course.setId(View.generateViewId());
                    final String text = courses.get(j).getName();
                    course.setText(text);
                    course.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    course.setTextColor(Color.BLACK);
                    course.setGravity(Gravity.CENTER_VERTICAL);
                    int textWidth = dpToPx(100, this);
                    courseUnits.addView(course, new ConstraintLayout.LayoutParams(
                            textWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                    //set the click listener(show course description)
                    course.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String message = text;
                            showDescription(message);
                        }
                    });
                } else {

                    editText.setId(View.generateViewId());
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    if (j < filled) {
                        final String text = courses.get(j).getName();
                        editText.setText(text);
                    }
                    int textWidth = dpToPx(80, this);
                    courseUnits.addView(editText, new ConstraintLayout.LayoutParams(
                            textWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                    insertedByUser = true;
                }

                // checkbox
                CheckBox checkBox = new CheckBox(this);
                checkBox.setId(View.generateViewId());
                checkBox.setFocusable(true);
//                checkBox.setFocusableInTouchMode(true);
                if (j < filled) {
                    final boolean isChecked = courses.get(j).getIscheck();
                    checkBox.setChecked(isChecked);
                }
                courseUnits.addView(checkBox, new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));


                /**
                 * pack views --> add listener
                 */
                if (j < filled && courses.get(j).getIsOrigin()) {

                    checkBoxR.get(name).add(new Pair<>(courses.get(j).getName(), checkBox));
                } else {
                    checkBoxU.get(name).add(new Pair<>(editText, checkBox));
                }


                // get the number of courses should be placed on the first column
                int firstColumnCount;

                if (name.equals("CS Units") || name.equals("Math Units")) {
                    firstColumnCount = numLimit.get(name);
                } else {
                    firstColumnCount = totalCourses % 2 == 0? totalCourses / 2: totalCourses / 2 + 1;
                }

                // add each view to assigned column
                if (name.equals("Non-Math Units")) {

                    if (swichColumnForNonMath) {
                        if (insertedByUser) {
                            courseList1.add(new Pair<>(checkBox, (View) editText));
                        } else {
                            courseList1.add(new Pair<>(checkBox, (View) course));
                            ++posList1ForEdit;
                        }
                        swichColumnForNonMath = false;

                    } else {

                        if (insertedByUser) {
                            courseList2.add(new Pair<>(checkBox, (View) editText));
                        } else {
                            courseList2.add(new Pair<>(checkBox, (View) course));
                            ++posList2ForEdit;
                        }
                        swichColumnForNonMath = true;
                    }

                } else {
                    if (count < firstColumnCount) {

                        if (insertedByUser) {
                            courseList1.add(new Pair<>(checkBox, (View) editText));
                        } else {
                            courseList1.add(new Pair<>(checkBox, (View) course));
                            ++posList1ForEdit;
                        }
                        ++count;
                    } else {
                        if (insertedByUser) {
                            courseList2.add(new Pair<>(checkBox, (View) editText));
                        } else {
                            courseList2.add(new Pair<>(checkBox, (View) course));
                            ++posList2ForEdit;
                        }
                    }
                }

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
            tmpStartMargin = dpToPx(32, this);
            constraintSet.connect(container.getId(), ConstraintSet.END, courseUnits.getId(), ConstraintSet.END, tmpEndMargin);
            constraintSet.connect(container.getId(), ConstraintSet.START, courseUnits.getId(), ConstraintSet.START, tmpStartMargin);
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

            // COURSES
            int list1StartMargin = dpToPx(14, this);
            int list2StartMargin = dpToPx(140, this);
            int textToCheckbox = dpToPx(6, this);

            for (int j = 0; j < courseList1.size(); ++j) {

                CheckBox tmpCheckbox = courseList1.get(j).first;
                View tmpCourse = courseList1.get(j).second;

                if (j == 0) {
                    constraintSet.connect(tmpCheckbox.getId(), ConstraintSet.TOP, underline.getId(), ConstraintSet.BOTTOM, 0);
                } else {
                    View preCourse = courseList1.get(j - 1).second;
                    constraintSet.connect(tmpCheckbox.getId(), ConstraintSet.TOP, preCourse.getId(), ConstraintSet.BOTTOM, 0);
                }

                constraintSet.connect(tmpCheckbox.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, list1StartMargin);
                constraintSet.connect(tmpCourse.getId(), ConstraintSet.LEFT, tmpCheckbox.getId(), ConstraintSet.RIGHT, 0);

                // different constraints for EditText and TextView
                if (j >= posList1ForEdit) {

                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, tmpCheckbox.getId(), ConstraintSet.TOP, 0);
                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.BOTTOM, tmpCheckbox.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.setVerticalBias(tmpCourse.getId(), (float) 0.5);
                } else {

                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, tmpCheckbox.getId(), ConstraintSet.TOP, textToCheckbox);
                }
            }

            for (int j = 0; j < courseList2.size(); ++j) {

                CheckBox tmpCheckbox = courseList2.get(j).first;
                View tmpCourse = courseList2.get(j).second;

                if (j == 0) {
                    constraintSet.connect(tmpCheckbox.getId(), ConstraintSet.TOP, underline.getId(), ConstraintSet.BOTTOM, 0);
                } else {
                    View preCourse = courseList2.get(j - 1).second;
                    constraintSet.connect(tmpCheckbox.getId(), ConstraintSet.TOP, preCourse.getId(), ConstraintSet.BOTTOM, 0);
                }
                constraintSet.connect(tmpCheckbox.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, list2StartMargin);
                constraintSet.connect(tmpCourse.getId(), ConstraintSet.LEFT, tmpCheckbox.getId(), ConstraintSet.RIGHT, 0);

                if (j >= posList2ForEdit) {

                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, tmpCheckbox.getId(), ConstraintSet.TOP, 0);
                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.BOTTOM, tmpCheckbox.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.setVerticalBias(tmpCourse.getId(), (float) 0.5);
                } else {
                    constraintSet.connect(tmpCourse.getId(), ConstraintSet.TOP, tmpCheckbox.getId(), ConstraintSet.TOP, textToCheckbox);

                }
            }
            constraintSet.applyTo(courseUnits);

        }

        // add listeners for required courses
        addListener();


        /**
         * display additional constraints
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
        CheckBox preText = new CheckBox(this);

        for (int i = 0; i < constraints.size(); ++i) {

            // get each group of constraints
            Constraints tmpConstraint = constraints.get(i);

            String parent = tmpConstraint.getRelation().first.getName();
            ArrayList<EachConstraintsChild> children = tmpConstraint.getRelation().second;

            // store the text on each level
            List<List<CheckBox>>  contents = new ArrayList<>();
            contents.add(new ArrayList<CheckBox>());
            contents.add(new ArrayList<CheckBox>());

            for (int j = 0; j < 1 + children.size(); ++j) {

                int textWidth;

                // initial checkbox
                CheckBox checkBox = new CheckBox(this);
                checkBox.setId(View.generateViewId());
                checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                checkBox.setTextColor(Color.BLACK);
                checkBox.setGravity(Gravity.CENTER_VERTICAL);
                if (j == 0) {
                    boolean checked = tmpConstraint.getRelation().first.getIsChecked();
                    checkBox.setChecked(checked);
                    checkBox.setText(parent);
                    textWidth = dpToPx(280, this);
                } else {
                    boolean checked = children.get(j - 1).getIsChecked();
                    checkBox.setChecked(checked);
                    checkBox.setText(children.get(j - 1).getName());
                    textWidth = dpToPx(250, this);
                }

                addConstraintBox.addView(checkBox, new ConstraintLayout.LayoutParams(
                        textWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT));


                contents.get(level).add(checkBox);

                if (j == 0) {
                    ++level;
                }

                constraintsData.add(checkBox);
            }


            ConstraintSet ContentConstraintSet = new ConstraintSet();
            ContentConstraintSet.clone(addConstraintBox);

            for (int k = 0; k <= level; ++k) {

                List<CheckBox> levelText = contents.get(k);
                List<Integer> startMargin = new ArrayList<>();
                startMargin.add(dpToPx(17, this));
                startMargin.add(dpToPx(50, this));

                for (int j = 0; j < levelText.size(); ++j) {
                    CheckBox tmpCheck = levelText.get(j);

                    int margin = dpToPx(5, this);

                    // checkbox
                    ContentConstraintSet.connect(tmpCheck.getId(), ConstraintSet.START, container.getId(), ConstraintSet.START, startMargin.get(k));

                    if (j == 0) {
                        if (i == 0 && k == 0) {
                            ContentConstraintSet.connect(tmpCheck.getId(), ConstraintSet.TOP, underline.getId(), ConstraintSet.BOTTOM, margin);
                        } else {
                            ContentConstraintSet.connect(tmpCheck.getId(), ConstraintSet.TOP, preText.getId(), ConstraintSet.BOTTOM, margin);
                        }
                    } else {
                        CheckBox preCon = levelText.get(j - 1);
                        ContentConstraintSet.connect(tmpCheck.getId(), ConstraintSet.TOP, preCon.getId(), ConstraintSet.BOTTOM, margin);
                    }

                    if (i == constraints.size() - 1 && j == levelText.size() - 1) {

                        if (children.size() == 0 || k == level) {
                            ContentConstraintSet.connect(tmpCheck.getId(), ConstraintSet.BOTTOM, addConstraintBox.getId(), ConstraintSet.BOTTOM, 60);
                        }
                    }

                    if (j == levelText.size() - 1) {
                        preText = tmpCheck;
                    }
                }
            }

            level = 0;
            ContentConstraintSet.applyTo(addConstraintBox);

        }

        /**
         * suggested course button
         */
        System.out.println("button start");
        ConstraintLayout buttonConstraintbox = new ConstraintLayout(this);
        buttonConstraintbox.setId(View.generateViewId());
        linearLayout.addView(buttonConstraintbox, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        Button sugCourseButton = new Button(this);
        sugCourseButton.setId(View.generateViewId());
        sugCourseButton.setText("Suggested Courses");
        sugCourseButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        buttonConstraintbox.addView(sugCourseButton, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

        ConstraintSet buttonConstraints = new ConstraintSet();
        buttonConstraints.clone(buttonConstraintbox);

        int buttonStartMargin = dpToPx(50, this);
        int buttonEndMargin = dpToPx(16, this);
        buttonConstraints.connect(sugCourseButton.getId(), ConstraintSet.TOP, buttonConstraintbox.getId(), ConstraintSet.TOP, 0);
        buttonConstraints.connect(sugCourseButton.getId(), ConstraintSet.BOTTOM, buttonConstraintbox.getId(), ConstraintSet.BOTTOM, 60);
        buttonConstraints.connect(sugCourseButton.getId(), ConstraintSet.LEFT, buttonConstraintbox.getId(), ConstraintSet.LEFT, buttonStartMargin);
        buttonConstraints.connect(sugCourseButton.getId(), ConstraintSet.RIGHT, buttonConstraintbox.getId(), ConstraintSet.RIGHT, buttonEndMargin);

        buttonConstraints.applyTo(buttonConstraintbox);

        sugCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SuggestedCoursesModel model = new SuggestedCoursesModel(programName, context);
                List<String> result =  model.getResultCourses();
//                for (int c = 0; c < result.size(); ++c) {
//                    System.out.println(result.get(c));
//                }
                openSuggestedCourses(result);
            }
        });

        addListenerToConstraints();

    }

    private void openSuggestedCourses(List<String> result) {

        Intent sugCourses = new Intent(this, SuggestedCoursesActivity.class);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("courses", (ArrayList<String>) result);

        sugCourses.putExtra("Course", bundle);
        startActivity(sugCourses);
    }

    private void deleteChecklist() {

        modifyPlan.deleteChecklist();
        finish();
        Intent goHome = new Intent(this, UserPageActivity.class);
        startActivity(goHome);
    }


    public int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = (int) ((dp * displayMetrics.density) + 0.5);
        return px;
    }



    public void addListener() {

        // the first for loop will add onClicklistners to the checkboxes that point to TextView
        for (Map.Entry<String, List<Pair<String, CheckBox>>> entry: checkBoxR.entrySet()) {

            final String catName = entry.getKey();
            List<Pair<String, CheckBox>> checkBoxes = entry.getValue();

            for (int i = 0; i < checkBoxes.size(); ++i) {
                final String courseName = checkBoxes.get(i).first;
                CheckBox checkBox = checkBoxes.get(i).second;

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checklist.changeCourseIsCheck(courseName, catName);
                    }
                });

            }
        }

        // the first for loop will add onClicklistners to the checkboxes that point to EditText
        for (Map.Entry<String, List<Pair<EditText, CheckBox>>> entry: checkBoxU.entrySet()) {

            final Context context = this;
            final String catName = entry.getKey();
            List<Pair<EditText, CheckBox>> checkBoxes = entry.getValue();

            for (int i = 0; i < checkBoxes.size(); ++i) {

                final EditText course = checkBoxes.get(i).first;
                final CheckBox checkBox = checkBoxes.get(i).second;
                final String name = course.getText().toString();
                final boolean ischecked = checkBox.isChecked();

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        if (currEditText != null) {
                            if (currEditText.hasFocus()) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(currEditText.getWindowToken(), 0);
                                currEditText.clearFocus();
                            }
                        }

                        String courseName = course.getText().toString();
                        if (courseName.equals("")) {
                            checkBox.setChecked(!b);
                            Toast claim = Toast.makeText(context, "Please enter the course you enrolled", Toast.LENGTH_SHORT);
                            claim.show();
                        } else {
                            checklist.changeCourseIsCheck(courseName, catName);
                        }
                    }
                });

                course.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {

                        if (!b) {
                            String newText = course.getText().toString();

                            if (!newText.equals("") && !newText.equals(name)) {
                                checklist.deleteCourses(name, catName);
                                System.out.println(catName);
                                checklist.insertCourses(newText, catName);


                                if (!name.equals("") && ischecked) {
                                    checklist.changeCourseIsCheck(newText, catName);
                                }
                            } else if (newText.equals("") && !newText.equals(name)) {
                                checklist.deleteCourses(name, catName);
                            }
                        } else {
                            currEditText = course;
                        }
                    }
                });

            }

        }

    }

    private void addListenerToConstraints() {

        for (final CheckBox checkBox: constraintsData) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> effectedCons = checklist.changeConstraintsIsCheck(checkBox.getText().toString());
                    effectedCons.remove(0);
                    changeState(effectedCons, checkBox.isChecked());
                }
            });
        }
    }

    private void changeState(ArrayList<String> effectedCons, boolean state) {

        int pos = 0;
        int size = effectedCons.size();

        for (final CheckBox checkBox: constraintsData) {

            if (pos == size) break;

            String name = checkBox.getText().toString();
            
            if (name.equals(effectedCons.get(pos))) {
                checkBox.setChecked(state);
                ++pos;
            }
        }

    }


    public void showDescription(String text) {

        if (text.contains("-") || text.contains("XX") ||
                text.equals("CS 483(discontinued, may be replaced by other)")) {
            Toast claim = Toast.makeText(this, "No Description", Toast.LENGTH_SHORT);
            claim.show();
            return;
        }

        Intent description = new Intent(this, DescriptionActivity.class);

        description.putExtra(COURSE_NAME, text);
        startActivity(description);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checklist_menu, menu);
        return true;
    }


}
