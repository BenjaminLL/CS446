package com.example.gograd.util.constraints;

import com.example.gograd.database.ChecklistOpenHelper;

import java.util.Observable;

public class EachConstraintsParent extends EachConstraints {

    private int limitation;

    private int current;

    private Boolean isUpdated = false;

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
                if (current >= limitation && !isChecked) {
                    isUpdated = true;
                }
            } else {
                current--;
                if (current < limitation && isChecked) {
                    isUpdated = true;
                }
            }

            limitationCheck();
        }
    }

    private void limitationCheck() {
        isChecked = current >= limitation;
        updateTable();
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public int getCurrent() {
        return current;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
