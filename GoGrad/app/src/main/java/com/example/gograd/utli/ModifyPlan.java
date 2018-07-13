package com.example.gograd.utli;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.gograd.ChecklistOpenHelper;

public class ModifyPlan {

    private String whichPlan;
    private ChecklistOpenHelper checklistOpenHelper;

    public ModifyPlan(String whichPlan, Context context) {

        this.whichPlan = whichPlan;
        checklistOpenHelper = new ChecklistOpenHelper(context, "checklist.db", null, 1);
    }

    public Boolean getIsInChecklist() {
        SQLiteDatabase db = checklistOpenHelper.getWritableDatabase();
        
        checklistOpenHelper.insertChecklist(whichPlan);
    }

    public void insertChecklist() {
        checklistOpenHelper.insertChecklist(whichPlan);
    }

    public void deleteChecklist() {
        checklistOpenHelper.deleteChecklist(whichPlan);
    }
}
