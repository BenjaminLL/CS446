package com.example.gograd;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ChecklistActivity extends AppCompatActivity {

    public static final String COURSE_NAME = "Course Description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.CHECKLIST_NAME);
        ab.setTitle("My First Checklist");
    }

    public void showDescription(View view) {

        Intent description = new Intent(this, DescriptionActivity.class);

        // get the id name of text which is pressed
        TextView t = (TextView) view;
        String text = ((TextView) view).getText().toString();

        description.putExtra(COURSE_NAME, text);

        startActivity(description);

    }

    public void showSuggestedCourses(View view) {

        Intent sCourses = new Intent(this, SuggestedCourses.class);
        startActivity(sCourses);
    }


}
