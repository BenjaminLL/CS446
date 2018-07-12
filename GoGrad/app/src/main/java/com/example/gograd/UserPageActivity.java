package com.example.gograd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.generateViewId;

public class UserPageActivity extends AppCompatActivity {

    private ChecklistOpenHelper databaseHelper;

    int numChecklist = 0;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> ids = new ArrayList<>();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        databaseHelper = new ChecklistOpenHelper(this, "checklist.db", null, 1);
        databaseHelper.getWritableDatabase();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

//        databaseHelper.deleteDatabase();
//        databaseHelper.TestDefaultChecklist();

        names = databaseHelper.getList();
        ids = databaseHelper.getID();

        numChecklist = names.size();


        /**
         * TEST
         */
//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();
//        String value = databaseAccess.getCSUnits("17/18BCS");
//        System.out.println("value: " + value);
//        databaseAccess.close();

        ConstraintLayout rootView = findViewById(R.id.userPage);

        // if the user has no checklist in local
        if (numChecklist == 0) {
            ImageView background = new ImageView(this);
            background.setId(R.id.logo);
            background.setAlpha(100);
            background.setImageResource(R.drawable.logo);
            int bgWidth = dpToPx(204, this);
            int bgHeight = dpToPx(204, this);
            rootView.addView(background, new ConstraintLayout.LayoutParams(bgWidth, bgHeight));

            TextView claim = new TextView(this);
            claim.setId(R.id.claim);
            claim.setText("Please add more checklists!");
            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Myanmar Sangam MN.ttf");
            claim.setTypeface(tf);
            claim.setTextSize(20);
            rootView.addView(claim, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT));

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootView);

            // Set the constraints between background logo and the root view
            constraintSet.connect(background.getId(), ConstraintSet.TOP, rootView.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(background.getId(), ConstraintSet.BOTTOM, rootView.getId(), ConstraintSet.BOTTOM, 0);
            constraintSet.connect(background.getId(), ConstraintSet.LEFT, rootView.getId(), ConstraintSet.LEFT, 0);
            constraintSet.connect(background.getId(), ConstraintSet.RIGHT, rootView.getId(), ConstraintSet.RIGHT, 0);

            constraintSet.setVerticalBias(background.getId(), (float)0.3);
            constraintSet.setHorizontalBias(background.getId(), (float)0.5);

            // Set the constraints between background logo and the claim sentence
            constraintSet.connect(claim.getId(), ConstraintSet.TOP, background.getId(), ConstraintSet.BOTTOM, 8);
            constraintSet.connect(claim.getId(), ConstraintSet.LEFT, rootView.getId(), ConstraintSet.LEFT, 8);
            constraintSet.connect(claim.getId(), ConstraintSet.RIGHT, rootView.getId(), ConstraintSet.RIGHT, 8);

            constraintSet.applyTo(rootView);

        } else { // if the user has one or more checklist added in local

            LinearLayout linearLayout = findViewById(R.id.tablelist);

            for (int i = 0; i < numChecklist; ++i) {
                ConstraintLayout checklist = new ConstraintLayout(this);
                checklist.setId(ids.get(i));
                linearLayout.addView(checklist, i, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));

                // Set the image
                ImageButton imageButton = new ImageButton(this);
                imageButton.setId(generateViewId());
                imageButton.setBackgroundResource(R.drawable.checklist2);
                int tmpWidth = dpToPx(346,this);
                int tmpHeight = dpToPx(239, this);
                checklist.addView(imageButton, new ConstraintLayout.LayoutParams(tmpWidth, tmpHeight));

                // Set the Name
                TextView tagName = new TextView(this);
                tagName.setId(generateViewId());
                tagName.setText(names.get(i));
                tagName.setTextColor(Color.BLACK);
//                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Myanmar Sangam MN.ttf");
//                tagName.setTypeface(tf);
                tagName.setTextSize(20.15f);
                checklist.addView(tagName, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));

                // set Constraints
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(checklist);

                    // set image constraints
                if (i == 0) {
                    int tmpMargin = dpToPx(20, this);
                    constraintSet.connect(imageButton.getId(), ConstraintSet.TOP, checklist.getId(), ConstraintSet.TOP, tmpMargin);
                } else {
                    constraintSet.connect(imageButton.getId(), ConstraintSet.TOP, checklist.getId(), ConstraintSet.TOP, 0);
                }
                constraintSet.connect(imageButton.getId(), ConstraintSet.START, checklist.getId(), ConstraintSet.START, 8);
                constraintSet.connect(imageButton.getId(), ConstraintSet.END, checklist.getId(), ConstraintSet.END, 8);
                constraintSet.setHorizontalBias(imageButton.getId(), (float)0.5);

                    // set text constraints
                int tmpTopMargin = dpToPx(12, this);
                int tmpLeftMargin = dpToPx(24, this);
                constraintSet.connect(tagName.getId(), ConstraintSet.START, imageButton.getId(), ConstraintSet.START, tmpLeftMargin);
                constraintSet.connect(tagName.getId(), ConstraintSet.TOP, imageButton.getId(), ConstraintSet.TOP, tmpTopMargin);

                constraintSet.applyTo(checklist);
            }

        }

    }


    public int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = (int)((dp * displayMetrics.density) + 0.5);
        return px;
    }

    public void selectProgram(View view) {
        Intent checkList = new Intent(this, ProgramSelectionActivity.class);
        startActivity(checkList);
    }

}
