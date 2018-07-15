package com.example.gograd.utli.constraints;

import java.util.Observable;

public class EachConstraintsChild extends EachConstraints {

    public EachConstraintsChild(String whichPlan, String name, Boolean isChecked) {
        super(whichPlan, name, isChecked);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EachConstraintsParent) {
            isChecked = (boolean) arg;
            updateTable();
        }
    }

}
