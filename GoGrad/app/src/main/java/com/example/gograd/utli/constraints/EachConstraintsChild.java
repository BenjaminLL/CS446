package com.example.gograd.utli.constraints;

import com.example.gograd.ChecklistOpenHelper;

import java.util.Observable;

public class EachConstraintsChild extends EachConstraints {

    public EachConstraintsChild(String whichPlan, String name,
                                Boolean isChecked, ChecklistOpenHelper checklistOpenHelper) {
        super(whichPlan, name, isChecked, checklistOpenHelper);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EachConstraintsParent) {
            isChecked = (Boolean) arg;
            updateTable();
        }
    }

}
