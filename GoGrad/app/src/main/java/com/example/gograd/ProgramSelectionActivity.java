package com.example.gograd;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramSelectionActivity extends AppCompatActivity {

    public static final String PROGRAM_NAME = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_selection);

        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
            // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void selectOptions(View view) {
        Intent OptionSelection = new Intent(this, OptionSelectionActivity.class);

        // send the program name to the next activity
        final Resources res = view.getContext().getResources();
        String idName = res.getResourceEntryName(view.getId());

        if (!idName.equals("BCS")) {
            Toast info = Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT);
            info.show();
        } else {
            OptionSelection.putExtra(PROGRAM_NAME, idName);
            startActivity(OptionSelection);
        }

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
