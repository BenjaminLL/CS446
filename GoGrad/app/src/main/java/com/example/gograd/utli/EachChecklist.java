package com.example.gograd.utli;

import android.content.Context;
import android.util.Pair;

import com.example.gograd.ChecklistOpenHelper;
import com.example.gograd.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

public class EachChecklist {

    private List<Pair<String, ArrayList<EachCourse>>> course;
    private List<Pair<EachConstraints, ArrayList<EachConstraints>>> constraint;
    private String whichPlan;
    private Context context;
    private ChecklistOpenHelper checklistOpenHelper;


    public EachChecklist(String whichPlan, Context context) {

        course = new ArrayList<>();
        constraint = new ArrayList<>();
        this.whichPlan = whichPlan;
        this.context = context;
        ChecklistOpenHelper checklistOpenHelper = new ChecklistOpenHelper(context, "checklist.db", null, 1);
    }

    public List<Pair<String, ArrayList<EachCourse>>> getCourse() {

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


        
        return course;
    }

    public List<Pair<EachConstraints, ArrayList<EachConstraints>>> getConstraint() {
        return constraint;
    }

    private void splitCourses(String key, String value) {

        ArrayList<EachCourse> tempArr = new ArrayList<>();
        if (value == null) {
            Pair<String, ArrayList<EachCourse>> tempPair = new Pair<>(key, tempArr);
            course.add(tempPair);
            return;
        }
        String[] lines = value.split("\\r?\\n");
        for (String line : lines) {
            EachCourse eachCourse = new EachCourse(whichPlan, line,
                    checklistOpenHelper.getIsCheck(whichPlan, line), checklistOpenHelper.getIsOrigin(whichPlan, line));
            tempArr.add(eachCourse);
        }
        Pair<String, ArrayList<EachCourse>> tempPair = new Pair<>(key, tempArr);
        course.add(tempPair);
    }



}
