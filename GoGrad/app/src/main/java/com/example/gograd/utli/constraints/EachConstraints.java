package com.example.gograd.utli.constraints;

import com.example.gograd.ChecklistOpenHelper;

import java.util.Observable;
import java.util.Observer;

public abstract class EachConstraints extends Observable implements Observer {

    protected Boolean isChecked;
    private String whichPlan;
    private String name;
    private ChecklistOpenHelper checklistOpenHelper;

    public EachConstraints(String whichPlan, String name, Boolean isChecked, ChecklistOpenHelper checklistOpenHelper) {
        this.whichPlan = whichPlan;
        this.name = name;
        this.isChecked = isChecked;
        this.checklistOpenHelper = checklistOpenHelper;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
        updateTable();
        this.setChanged();
        notifyObservers(this.isChecked);


    }

    protected void updateTable() {
        checklistOpenHelper.updateUserTable_Status(whichPlan, name, isChecked);
    }
}
