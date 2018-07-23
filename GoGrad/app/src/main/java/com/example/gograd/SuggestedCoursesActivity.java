package com.example.gograd;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class SuggestedCoursesActivity extends AppCompatActivity {

    TextView courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_courses);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Suggested Courses");

        /**
         * get the passed values (course names)
         */
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Course") ;
        List<String> courseNames = bundle.getStringArrayList("courses");


        /**
         * display on the screen
         */
        courses = findViewById(R.id.courses);
        String resultString = "";

        for (int i = 0; i < courseNames.size(); ++i) {

            if (i == 0) {
                resultString += courseNames.get(i);
            } else {
                resultString += ", " + courseNames.get(i);
            }
        }

        if (resultString.equals("")) {
            resultString = "Hello World!";
        }

        courses.setText(resultString);

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
