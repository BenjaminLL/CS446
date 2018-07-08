package com.example.gograd;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String CHECKLIST_NAME = "@My First Checklist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void showChecklist(View view) {
        Intent checkList = new Intent(this, ChecklistActivity.class);

        // get the id name of button which is pressed
        final Resources res = view.getContext().getResources();
        String idName = res.getResourceEntryName(view.getId());
//        TextView checklist = findViewById();
        checkList.putExtra(CHECKLIST_NAME, idName);

        startActivity(checkList);
    }
}
