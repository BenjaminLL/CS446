package com.example.gograd.utli.constraints;

import com.example.gograd.ChecklistOpenHelper;

import java.util.Observable;

public class EachConstraintsParent extends EachConstraints {

    private int limitation;

    private int current;

    public EachConstraintsParent(String whichPlan, String name, Boolean isChecked,
                                 int limitation, int current, ChecklistOpenHelper checklistOpenHelper) {
        super(whichPlan, name, isChecked, checklistOpenHelper);
        this.limitation = limitation;
        this.current = current;
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof EachConstraintsChild) {
            boolean flag = (Boolean) arg;
            if (flag) {
                current++;
            } else {
                current--;
            }

            limitationCheck();
        }
    }

    private void limitationCheck() {
        isChecked = current >= limitation;
        updateTable();
    }

}
