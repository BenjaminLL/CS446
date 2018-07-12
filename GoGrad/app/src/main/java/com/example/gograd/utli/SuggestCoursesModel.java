package com.example.gograd.utli;

import java.util.ArrayList;

public class SuggestCoursesModel {

    private ArrayList<String> suggestCourses;
    //database
    private String whichPlan;

    public SuggestCoursesModel(String whichPlan) {

        suggestCourses = new ArrayList<>();
        this.whichPlan = whichPlan;
    }

    public ArrayList<String> getSuggestCourses() {

        ArrayList<String> temp;
        for (int row = 1; row < 10 ; row++) {
            /*
            if (row.ischeck == true) {
                String[] lines = row.successor.split(",");
                for(String line: lines) {
                    for(String prerequest : lines.prerequest.split(",")) {
                        for (int row = 1; row < total_length; i++) {
                            if (row.name == prerequest && row.ischeck == true) {
                                suggestCourses.add(line);
                            }
                        }
                    }
                }
            }
            else {
                for(String prerequest : row.name.prerequest.split(",")) {
                        for (int row = 1; row < total_length; i++) {
                            if (row.name == prerequest && row.ischeck == true) {
                                suggestCourses.add(line);
                            }
                        }
                 }
            }
            */
        }
        return getSuggestCourses();
    }

}
