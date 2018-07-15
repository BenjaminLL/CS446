package com.example.gograd.utli.constraints;

import java.util.Observable;

public class EachConstraintsParent extends EachConstraints {

    private int limitation;

    private int current;

    public EachConstraintsParent(String whichPlan, String name, Boolean isChecked, int limitation, int current) {
        super(whichPlan, name, isChecked);
        this.limitation = limitation;
        this.current = current;
        limitationCheck();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EachConstraintsChild) {
            boolean flag = (boolean) arg;
            if (flag) {
                current++;
            }
            else {
                current--;
            }
            limitationCheck();
        }
    }

    private void limitationCheck() {
        if (current >= limitation) {
            isChecked = true;
        }
        else {
            isChecked = false;
        }
        updateTable();
    }

}
