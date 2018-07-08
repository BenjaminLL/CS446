package com.example.gograd;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionSelectionActivity extends AppCompatActivity implements ProgramDialog.DialogListener {

    public static final String PROGRAM = "null";

    private final String[] bcsOptions = new String[] {"AI", "Bio", "Bus", "CFA", "DH", "HCI", "SE",
            "Option"};
    private final String[] bcsAI = new String[] {"no option yet", "Academic Year"};
    private final String[] bcsCFA_HCI = new String[] {"17/18", "16/17", "Academic Year"};
    private final String[] bcsOthers = new String[] {"17/18", "16/17", "15/16", "14/15", "13/14",
            "12/13", "11/12", "Academic Year"};

    private Map<String, String[]> options;
    private EditText option;
    private EditText year;
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
        String programName = intent.getStringExtra(ProgramSelectionActivity.PROGRAM_NAME);

        /**
         * initial components
         */
        option = findViewById(R.id.option);
        year = findViewById(R.id.year);
        search = findViewById(R.id.search);

        /**
         * initial the options content
         */
        options = new HashMap<>();
        options.put("BCS", bcsOptions);


        /**
         * open dialog
         */
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(bcsOptions);
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String optionChosen = option.getText().toString();

                if (optionChosen.equals("") || optionChosen.equals("AI")) {

                    openDialog(bcsAI);
                } else if (optionChosen.equals("CFA") || optionChosen.equals("HCI")) {

                    openDialog(bcsCFA_HCI);
                } else {

                    openDialog(bcsOthers);
                }

            }
        });

    }


    public void openDialog(String[] options) {

        // set passed arguments
        Bundle bundle = new Bundle();
        bundle.putStringArray("values", options);

        ProgramDialog programDialog = new ProgramDialog();
        programDialog.setArguments(bundle);
        programDialog.show(getSupportFragmentManager(), "example");

    }

    @Override
    public void applyTexts(String object, String value) {
        if (object.equals("Option")) {
            option.setText(value);
        } else {
            year.setText(value);
        }
    }

}
