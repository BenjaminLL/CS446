package com.example.gograd.utli.constraints;
import com.example.gograd.ChecklistOpenHelper;

import java.util.Observable;
import java.util.Observer;

public abstract class EachConstraints extends Observable implements Observer {

    private String whichPlan;
    private String name;
    protected Boolean isChecked;
    private ChecklistOpenHelper checklistOpenHelper;

    public EachConstraints(String whichPlan, String name, Boolean isChecked) {
        this.whichPlan = whichPlan;
        this.name = name;
        this.isChecked = isChecked;
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
        notifyObservers(isChecked);
    }

    protected void updateTable() {
        checklistOpenHelper.updateUserTable_Status(whichPlan, name, isChecked);
    }
}
