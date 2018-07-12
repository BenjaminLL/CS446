package com.example.gograd.utli;

import java.util.ArrayList;

public class EachChecklist {

    private ArrayList<EachCourse> eachCourse;
    //database
    private String whichPlan;

    public EachChecklist(String whichPlan) {

        eachCourse = new ArrayList<>();
        this.whichPlan = whichPlan;
    }

    public ArrayList<EachCourse> getEachCourse() {
        /*
        for (int row = 0; row < total_length; i++) {
           EachCourse temp = new EachCourse(row.name, row.ischeck);
        }
        */
        return eachCourse;
    }

}
