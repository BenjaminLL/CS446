package com.example.gograd.utli;

import java.util.ArrayList;

public class EachChecklist {

    private ArrayList<EachCourse> eachCourse;
    //database

    public EachChecklist() {
        eachCourse = new ArrayList<>();
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
