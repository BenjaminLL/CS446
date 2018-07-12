package com.example.gograd.utli;

import android.content.Context;
import android.util.Pair;

import com.example.gograd.DatabaseAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SplitString {

    private List<Pair<String, ArrayList<String>>> course;
    private List<Pair<String, ArrayList<String>>> addition;
    //database
    private String whichPlan;
    private DatabaseAccess databaseAccess;
    private Context context;

    public SplitString(String whichPlan, Context context) {

        course = new ArrayList<>();
        addition = new ArrayList<>();
        this.whichPlan = whichPlan;
        this.context = context;
    }

    //call get units four times
    public List<Pair<String, ArrayList<String>>> getCourse() {
        String key;
        String value;

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        key = "CS Units";
        value = databaseAccess.getCSUnits(whichPlan);
        splitCourses(key, value);

        key = "Math Units";
        value = databaseAccess.getMath(whichPlan);
        splitCourses(key, value);

        key = "Elective Units";
        value = databaseAccess.getElective(whichPlan);
        splitCourses(key, value);

        key = "Non-Math Units";
        value = databaseAccess.getNonMath(whichPlan);
        splitCourses(key, value);

        databaseAccess.close();
        return course;
    }

    //call additional constrains
    public List<Pair<String, ArrayList<String>>> getAddition() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        splitConstraints(databaseAccess.getConstraints(whichPlan));
        databaseAccess.close();
        return addition;
    }

    private void splitCourses(String key, String value) {

        String[] lines = value.split("\\r?\\n");
        ArrayList<String> tempArr = new ArrayList<>();
        Collections.addAll(tempArr, lines);
        Pair<String, ArrayList<String>> tempPair = new Pair<>(key, tempArr);
        course.add(tempPair);
    }

    private void splitConstraints(String s) {

        String[] lines = s.split("\\r?\\n");
        int layer = 1;
        String temp = "";
        ArrayList<String> tempList = new ArrayList<>();

        for (String line : lines) {

            if (line.equals("Non-math units that satisfy breadth and depth requirement:")) {
                continue;
            }

            if (layer == 1) {

                if (line.substring(0, 1).equals("!") && !line.substring(1, 2).equals("!")) {
                    tempList.add(line.substring(1));
                    Pair<String, ArrayList<String>> tempPair = new Pair<>("bottom", tempList);
                    addition.add(tempPair);
                } else {
                    layer = 2;
                    temp = line.substring(2);
                }

            } else {
                if (line.substring(0, 1).equals("!") && !line.substring(1, 2).equals("!")) {
                    tempList.add(line.substring(1));
                    layer = 1;
                    ArrayList<String> newArray = new ArrayList<>();
                    for (int i = 0; i < tempList.size(); i++) {
                        newArray.add(i, tempList.get(i));
                    }
                    Pair<String, ArrayList<String>> tempPair = new Pair<>(temp, newArray);
                    addition.add(tempPair);
                    tempList.clear();
                    temp = "";
                } else {
                    tempList.add(line.substring(2));
                }
            }
        }
    }

}
