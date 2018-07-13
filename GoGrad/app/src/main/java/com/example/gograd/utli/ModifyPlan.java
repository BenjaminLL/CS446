package com.example.gograd.utli;

import android.content.Context;

import com.example.gograd.ChecklistOpenHelper;

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

    public void insertChecklist() {
        checklistOpenHelper.getWritableDatabase();
        checklistOpenHelper.insertChecklist(whichPlan);
    }

    public void deleteChecklist() {
        checklistOpenHelper.getWritableDatabase();
        checklistOpenHelper.deleteChecklist(whichPlan);
    }
}
