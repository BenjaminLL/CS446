package com.example.gograd.util;

import android.content.Context;

import com.example.gograd.database.ChecklistOpenHelper;
import com.example.gograd.database.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

public class SuggestedCourses {

    private ChecklistOpenHelper checklistOpenHelper;
    private String plan;
    private List<String> resultCourses;

    public SuggestedCourses(String plan, Context context) {

        checklistOpenHelper = new ChecklistOpenHelper(context, "checklist.db", null, 1);
        resultCourses = new ArrayList<>();

        ArrayList<String> unCheckedCourses = checklistOpenHelper.getUncheck(plan);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        for (int i = 0; i < unCheckedCourses.size(); ++i) {
            
            String courseName = unCheckedCourses.get(i);

            boolean isValid = databaseAccess.getIsValid(courseName);

            if (isValid) {
                ArrayList<List<String>> orHave = databaseAccess.getOrHave(courseName);
                List<String> mustHave = databaseAccess.getMustHave(courseName);

                if (!checkMustHave(mustHave) || !checkOrHave(orHave)) {
                    continue;
                } else {
                    resultCourses.add(courseName);
                }

            }
        }
    }

    private boolean checkOrHave(ArrayList<List<String>> orHave) {

        for (int i = 0; i < orHave.size(); ++i) {
            List<String> choices = orHave.get(i);

            for (int j = 0; j < choices.size(); ++j) {

                String courseName = choices.get(j);
                boolean checked = checklistOpenHelper.getIsCheck(plan, courseName);

                if (checked) break;
                if (!checked && j == choices.size() - 1) return false;
            }
        }

        return true;
    }

    private boolean checkMustHave(List<String> mustHave) {

        for (int i = 0; i < mustHave.size(); ++i) {
            String courseName = mustHave.get(i);
            boolean checked = checklistOpenHelper.getIsCheck(plan, courseName);
            if (!checked) return false;
        }

        return true;
    }
}
