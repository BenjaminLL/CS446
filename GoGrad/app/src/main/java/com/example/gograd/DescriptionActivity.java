package com.example.gograd;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

//        Intent intent = getIntent();
//        String courseName = intent.getStringExtra(ChecklistActivity.COURSE_NAME);
//
//        System.out.println(courseName);
//        TextView content = (TextView) findViewById(R.id.textView9);
//
//        if (courseName.equals("Communication\nList I")) {
//            content.setText(
//                    "At least 60% in one of: \nEMLS 101R, EMLS 102R, EMLS/ENGL 129R, ENGL 109, SPCOM 100, SPCOM 223");
//        } else if(courseName.equals("Communication\nList II")) {
//            content.setText(
//                    " One of: \nEMLS 103R, EMLS 104R, EMLS 110R, ENGL 101B, ENGL 108D, ENGL 119, ENGL 209, " +
//                            "ENGL 210E, ENGL 210F, ENGL 251A, ENGL 378/MTHEL 300, SPCOM 225, SPCOM 227, SPCOM 228, " +
//                            "or an additional course from the first list");
//        }
//
//        ab.setTitle(courseName);
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
