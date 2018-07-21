package com.example.gograd;

import android.content.Intent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class OptionSelectionActivity extends AppCompatActivity implements ProgramDialog.DialogListener {

    public static final String PROGRAM = "null";
    public static final String TITLE = "null";
    private String programName;

    private final String[] bcsOptions = new String[] {"BCS(No Option)", "AI", "Bio", "Bus", "CFA", "DH", "HCI", "SE",
            "Option"};
    private final String[] bcsAI = new String[] {"No Option yet", "Academic Year"};
    private final String[] bcsCFA_HCI = new String[] {"17/18", "16/17", "Academic Year"};
    private final String[] bcsOthers = new String[] {"17/18", "16/17", "15/16", "14/15", "13/14", "Academic Year"};

    private Map<String, String[]> options;
    private TextView option;
    private TextView year;
    private TextView optionSelector;
    private TextView yearSelector;
    private Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_selection);

        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // get the program name from ProgramSelectionActivity
        Intent intent = getIntent();
        programName = intent.getStringExtra(ProgramSelectionActivity.PROGRAM_NAME);

        /**
         * initial components
         */
        option = findViewById(R.id.option);
        year = findViewById(R.id.year);
        optionSelector = findViewById(R.id.option_selector);
        yearSelector = findViewById(R.id.year_selector);
        search = findViewById(R.id.search);

        /**
         * initial the options content
         */
        options = new HashMap<>();
        options.put("BCS", bcsOptions);


        /**
         * open dialog
         */
        optionSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(bcsOptions, "Option");
            }
        });

        yearSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String optionChosen = option.getText().toString();

                if (optionChosen.equals("") || optionChosen.equals("AI")) {

                    openDialog(bcsAI, "Year");
                } else if (optionChosen.equals("CFA") || optionChosen.equals("HCI")) {

                    openDialog(bcsCFA_HCI, "Year");
                } else {

                    openDialog(bcsOthers, "Year");
                }

            }
        });

    }

    public void showResult(View view) {

        Intent searchResult = new Intent(this, SearchResultActivity.class);

        String academicYear = year.getText().toString();
        String optionName = option.getText().toString();

        if (optionName.equals("")) {
            Toast claim = Toast.makeText(this, "Option required",
                    Toast.LENGTH_SHORT);
            claim.show();
            return;
        }

        if (academicYear.equals("")) {
            Toast claim = Toast.makeText(this, "Academic year required",
                    Toast.LENGTH_SHORT);
            claim.show();
            return;
        }

        String fullOption = "";

        if (optionName.equals("BCS(No Option)")) {
            optionName = "";
            fullOption = "";
        } else {

            if (optionName.equals("SE")) {
                fullOption = "Software Engineering";
            } else if (optionName.equals("HCI")) {
                fullOption = "Human-Computer Interaction";
            } else if (optionName.equals("DH")) {
                fullOption = "Digital Hardware";
            } else if (optionName.equals("CFA")) {
                fullOption = "Computational Fine Arts";
            } else if (optionName.equals("Bus")) {
                fullOption = "Business";
            } else if (optionName.equals("Bio")) {
                fullOption = "Bioinformatics";
            } else if (optionName.equals("AI")) {
                fullOption = "AI";
            }

            optionName = "/" + optionName;
        }
        // send the program name to the next activity
        String fullProgramName = academicYear + programName + optionName;
        String titleNextActivity = academicYear + " " + programName;

        if (!fullOption.equals("")) {
            titleNextActivity += "/" + fullOption;
        }

        Bundle programInfo = new Bundle();
        programInfo.putString("PROGRAM", fullProgramName);
        programInfo.putString("TITLE", titleNextActivity);

        searchResult.putExtras(programInfo);

        startActivity(searchResult);
    }


    public void openDialog(String[] options, String title) {

        // set passed arguments
        Bundle bundle = new Bundle();
        bundle.putStringArray("values", options);
        bundle.putString("dialog_title", title);

        ProgramDialog programDialog = new ProgramDialog();
        programDialog.setArguments(bundle);
        programDialog.show(getSupportFragmentManager(), "example");

    }

    @Override
    public void applyTexts(String object, String value) {
        if (object.equals("Option")) {
            option.setText(value);
            year.setText("");
        } else {
            if (value.equals("No Option yet")) {
                year.setText("");
            } else {
                year.setText(value);
            }
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
