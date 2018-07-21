package com.example.gograd.util;

import android.content.Context;
import android.util.Pair;

import com.example.gograd.database.ChecklistOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ModifyPlan {

    private String whichPlan;
    private ChecklistOpenHelper checklistOpenHelper;

    public ModifyPlan(String whichPlan, Context context) {

        this.whichPlan = whichPlan;
        checklistOpenHelper = new ChecklistOpenHelper(context, "checklist.db", null, 1);
    }

    public Boolean getIsExist() {
        checklistOpenHelper.getReadableDatabase();
        return checklistOpenHelper.checkExistPlan(whichPlan);
    }

    public void insertChecklist(List<Pair<String, ArrayList<String>>> course, List<Pair<String, ArrayList<String>>> add) {
        checklistOpenHelper.getWritableDatabase();

        boolean insert = checklistOpenHelper.insertChecklist(whichPlan);
        if (insert) {
            checklistOpenHelper.createUserTable(whichPlan, course, add);
        }
    }

    public void deleteChecklist() {
        checklistOpenHelper.getWritableDatabase();
        checklistOpenHelper.deleteChecklist(whichPlan);
    }
}
