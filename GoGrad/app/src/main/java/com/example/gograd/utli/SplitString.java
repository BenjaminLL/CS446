package com.example.gograd.utli;

import android.content.Context;
import android.util.Pair;

import com.example.gograd.DatabaseAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SplitString {

    private List<Pair<String, ArrayList<String>>> course;
    private List<Pair<String, ArrayList<String>>> constraints;
    private List<Double> courseUnits;

    public SplitString(String whichPlan, Context context) {

        course = new ArrayList<>();
        constraints = new ArrayList<>();
        courseUnits = new ArrayList<>();

        /* courses initializer */
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        String key1 = "CS Units";
        String value1 = databaseAccess.getCSUnits(whichPlan);
        splitCourses(key1, value1);

        String key2 = "Math Units";
        String value2 = databaseAccess.getMath(whichPlan);
        splitCourses(key2, value2);

        String key3 = "Elective Units";
        String value3 = databaseAccess.getElective(whichPlan);
        splitCourses(key3, value3);

        String key4 = "Non-Math Units";
        String value4 = databaseAccess.getNonMath(whichPlan);
        splitCourses(key4, value4);
        /* courses initializer end*/

        /* courses units initializer */
        Double unit1 = databaseAccess.getTotalCS(whichPlan);
        courseUnits.add(unit1);

        Double unit2 = databaseAccess.getTotalMath(whichPlan);
        courseUnits.add(unit2);

        Double unit3 = databaseAccess.getTotalElective(whichPlan);
        courseUnits.add(unit3);

        Double unit4 = databaseAccess.getTotalNonMath(whichPlan);
        courseUnits.add(unit4);
        /* courses units initializer end*/

        /* constraints initializer */
        splitConstraints(databaseAccess.getConstraints(whichPlan));
        /* constraints initializer end*/

        databaseAccess.close();
    }

    //call get units four times
    public List<Pair<String, ArrayList<String>>> getCourse() {
        return course;
    }

    //call additional constrains
    public List<Pair<String, ArrayList<String>>> getConstraints() {
        return constraints;
    }

    // call units
    public List<Double> getCourseUnits() {
        return courseUnits;
    }

    private void splitCourses(String key, String value) {

        ArrayList<String> tempArr = new ArrayList<>();
        if (value == null) {
            Pair<String, ArrayList<String>> tempPair = new Pair<>(key, tempArr);
            course.add(tempPair);
            return;
        }
        String[] lines = value.split("\\r?\\n");
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
                    ArrayList<String> blank = new ArrayList<>();
                    Pair<String, ArrayList<String>> tempPair = new Pair<>(line.substring(1), blank);
                    constraints.add(tempPair);
                    tempList.clear();
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
                    constraints.add(tempPair);
                    tempList.clear();
                    temp = "";
                } else {
                    tempList.add(line.substring(2));
                }
            }
        }
    }

}
