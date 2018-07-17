package com.example.gograd.utli.constraints;

import com.example.gograd.ChecklistOpenHelper;

import java.util.Observable;

public class EachConstraintsChild extends EachConstraints {

    private Boolean isUpdated = false;

    public EachConstraintsChild(String whichPlan, String name,
                                Boolean isChecked, ChecklistOpenHelper checklistOpenHelper) {
        super(whichPlan, name, isChecked, checklistOpenHelper);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EachConstraintsParent) {
            if (isChecked != (Boolean) arg) {
                isChecked = (Boolean) arg;
                isUpdated = true;
            }
            updateTable();
        }
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }
}
