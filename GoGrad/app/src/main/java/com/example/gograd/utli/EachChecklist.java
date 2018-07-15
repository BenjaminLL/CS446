package com.example.gograd.utli;

import android.content.Context;
import android.util.Pair;

import com.example.gograd.ChecklistOpenHelper;
import com.example.gograd.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

public class EachChecklist {

    private List<Pair<String, ArrayList<EachCourse>>> courses;
    private List<Pair<EachConstraints, ArrayList<EachConstraints>>> constraint;
    private List<Double> courseUnits;
    private String whichPlan;
    private Context context;
    private ChecklistOpenHelper checklistOpenHelper;


    public EachChecklist(String whichPlan, Context context) {

        courses = new ArrayList<>();
        constraint = new ArrayList<>();
        this.whichPlan = whichPlan;
        this.context = context;
        checklistOpenHelper = new ChecklistOpenHelper(context, "checklist.db", null, 1);
    }

    public List<Pair<String, ArrayList<EachCourse>>> getCourses() {

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
        for (Pair<String, ArrayList<EachCourse>> course : courses) {
            if (course.first.equals(key3)) {
                String value = checklistOpenHelper.getOriginUnderCategory(whichPlan, key3);
                if (value != null) {
                    String[] lines = value.split("\\r?\\n");
                    for (String line : lines) {
                        System.out.println("-------------------not null----------------");
                        EachCourse eachCourse = new EachCourse(whichPlan, line,
                                checklistOpenHelper.getIsCheck(whichPlan, line), checklistOpenHelper.getIsOrigin(whichPlan, line));
                        course.second.add(eachCourse);
                    }
                }

            }
        }


        String key4 = "Non-Math Units";
        String value4 = databaseAccess.getNonMath(whichPlan);
        splitCourses(key4, value4);for (Pair<String, ArrayList<EachCourse>> course : courses) {
            if (course.first.equals(key3)) {
                String value = checklistOpenHelper.getOriginUnderCategory(whichPlan, key4);
                if (value != null) {
                    String[] lines = value.split("\\r?\\n");
                    for (String line : lines) {
                        System.out.println("-----------Non-Math------------");
                        EachCourse eachCourse = new EachCourse(whichPlan, line,
                                checklistOpenHelper.getIsCheck(whichPlan, line), checklistOpenHelper.getIsOrigin(whichPlan, line));
                        course.second.add(eachCourse);
                    }
                }

            }
        }

        return courses;
    }

    public List<Double> getCourseUnits() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        Double unit1 = databaseAccess.getTotalCS(whichPlan);
        courseUnits.add(unit1);

        Double unit2 = databaseAccess.getTotalMath(whichPlan);
        courseUnits.add(unit2);

        Double unit3 = databaseAccess.getTotalElective(whichPlan);
        courseUnits.add(unit3);

        Double unit4 = databaseAccess.getTotalNonMath(whichPlan);
        courseUnits.add(unit4);

        databaseAccess.close();
        return courseUnits;
    }

    private void splitCourses(String key, String value) {

        ArrayList<EachCourse> tempArr = new ArrayList<>();
        if (value == null) {
            Pair<String, ArrayList<EachCourse>> tempPair = new Pair<>(key, tempArr);
            courses.add(tempPair);
            return;
        }
        String[] lines = value.split("\\r?\\n");
        for (String line : lines) {
            EachCourse eachCourse = new EachCourse(whichPlan, line,
                    checklistOpenHelper.getIsCheck(whichPlan, line), checklistOpenHelper.getIsOrigin(whichPlan, line));
            tempArr.add(eachCourse);
        }
        Pair<String, ArrayList<EachCourse>> tempPair = new Pair<>(key, tempArr);
        courses.add(tempPair);
    }


}
