package com.example.gograd.util;

import android.content.Context;
import android.util.Pair;

import com.example.gograd.database.ChecklistOpenHelper;
import com.example.gograd.database.DatabaseAccess;
import com.example.gograd.util.constraints.Constraints;
import com.example.gograd.util.constraints.EachConstraints;
import com.example.gograd.util.constraints.EachConstraintsChild;

import java.util.ArrayList;
import java.util.List;

public class EachChecklist {

    private List<Pair<String, ArrayList<EachCourse>>> courses;
    private List<Double> courseUnits;
    private List<Constraints> constraints;
    private List<String> suggestCourses;
    private String whichPlan;
    private ChecklistOpenHelper checklistOpenHelper;

    public EachChecklist(String whichPlan, Context context) {
        courses = new ArrayList<>();
        courseUnits = new ArrayList<>();
        constraints = new ArrayList<>();
        suggestCourses = new ArrayList<>();
        this.whichPlan = whichPlan;
        checklistOpenHelper = new ChecklistOpenHelper(context, "checklist.db", null, 1);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        /* courses initializer */
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
                        EachCourse eachCourse = new EachCourse(whichPlan, line,
                                checklistOpenHelper.getIsCheck(whichPlan, line),
                                checklistOpenHelper.getIsOrigin(whichPlan, line),
                                checklistOpenHelper);
                        course.second.add(eachCourse);
                    }
                }

            }
        }


        String key4 = "Non-Math Units";
        String value4 = databaseAccess.getNonMath(whichPlan);
        splitCourses(key4, value4);
        for (Pair<String, ArrayList<EachCourse>> course : courses) {
            if (course.first.equals(key4)) {
                String value = checklistOpenHelper.getOriginUnderCategory(whichPlan, key4);
                if (value != null) {
                    String[] lines = value.split("\\r?\\n");
                    for (String line : lines) {
                        EachCourse eachCourse = new EachCourse(whichPlan, line,
                                checklistOpenHelper.getIsCheck(whichPlan, line),
                                checklistOpenHelper.getIsOrigin(whichPlan, line),
                                checklistOpenHelper);
                        course.second.add(eachCourse);
                    }
                }

            }
        }
        /* end courses initializer */

        /* course unit initializer */
        Double unit1 = databaseAccess.getTotalCS(whichPlan);
        courseUnits.add(unit1);

        Double unit2 = databaseAccess.getTotalMath(whichPlan);
        courseUnits.add(unit2);

        Double unit3 = databaseAccess.getTotalElective(whichPlan);
        courseUnits.add(unit3);

        Double unit4 = databaseAccess.getTotalNonMath(whichPlan);
        courseUnits.add(unit4);
        /* end course unit initializer */

        /* constraints initializer */
        splitConstraints(databaseAccess.getConstraints(whichPlan));
        /* end constraints initializer */

        /* suggest courses initializer */

        /*
        for (EachCourse eachCourse : courses.get(0).second) {
            String name = eachCourse.getName();
            List<String> successors = databaseAccess.getSuccessor(name);
            for (String successor : successors) {
                Boolean flag = true; // false cannot add to suggestCourses
                Boolean innerFlag1 = false;
                Boolean innerFlag2 = false;
                Boolean innerFlag3 = false;

                List<String> mustHave = databaseAccess.getMustHave(successor);
                if (mustHave != null) {
                    for (String s : mustHave) {
                        for (int i = 0; i < 2; i++) {
                            for (EachCourse eachCourse1 : courses.get(i).second) {
                                if (s.equals(eachCourse1.getName()) && !eachCourse1.getIscheck()) {
                                    flag = false;
                                }
                            }
                        }
                    }
                }

                List<String> orHave1 = databaseAccess.getOrHave(successor).get(0);
                if (orHave1 != null) {
                    for (String s : orHave1) {
                        for (int i = 0; i < 2; i++) {
                            for (EachCourse eachCourse1 : courses.get(i).second) {
                                if (s.equals(eachCourse1.getName()) && eachCourse1.getIscheck()) {
                                    innerFlag1 = true;
                                }
                            }
                        }
                    }
                }
                else {
                    innerFlag1 = true;
                }

                List<String> orHave2 = databaseAccess.getOrHave(successor).get(0);
                if (orHave2 != null) {
                    for (String s : orHave2) {
                        for (int i = 0; i < 2; i++) {
                            for (EachCourse eachCourse1 : courses.get(i).second) {
                                if (s.equals(eachCourse1.getName()) && eachCourse1.getIscheck()) {
                                    innerFlag2 = true;
                                }
                            }
                        }
                    }
                }
                else {
                    innerFlag2 = true;
                }

                List<String> orHave3 = databaseAccess.getOrHave(successor).get(0);
                if (orHave3 != null) {
                    for (String s : orHave3) {
                        for (int i = 0; i < 2; i++) {
                            for (EachCourse eachCourse1 : courses.get(i).second) {
                                if (s.equals(eachCourse1.getName()) && eachCourse1.getIscheck()) {
                                    innerFlag3 = true;
                                }
                            }
                        }
                    }
                }
                else {
                    innerFlag3 = true;
                }

                if (flag && innerFlag1 && innerFlag2 && innerFlag3) {
                    suggestCourses.add(successor);
                }
            }
        }

        for (String s : suggestCourses) {
            System.out.println(s);
        }

        */

        /* end suggest courses initializer */

        databaseAccess.close();
    }

    public List<Pair<String, ArrayList<EachCourse>>> getCourses() {
        return courses;
    }

    public List<Double> getCourseUnits() {
        return courseUnits;
    }

    public List<Constraints> getConstraints() {
        return constraints;
    }

    public List<String> getSuggestCourses() {
        return suggestCourses;
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
                    checklistOpenHelper.getIsCheck(whichPlan, line),
                    checklistOpenHelper.getIsOrigin(whichPlan, line),
                    checklistOpenHelper);
            tempArr.add(eachCourse);
        }
        Pair<String, ArrayList<EachCourse>> tempPair = new Pair<>(key, tempArr);
        courses.add(tempPair);
    }

    private void splitConstraints(String s) {

        String[] lines = s.split("\\r?\\n");

        int layer = 1;
        String temp = "";
        int limitation = 0;
        int current = 0;
        int child = 0;
        boolean flag = false;
        ArrayList<Pair<String, Boolean>> tempList = new ArrayList<>();


        for (String line : lines) {

            if (line.equals("Non-math units that satisfy breadth and depth requirement:")) {
                continue;
            }

            if (layer == 1) {

                if (line.substring(0, 1).equals("!") && !line.substring(1, 2).equals("!")) {

                    //System.out.println(line.substring(1) + " : " + checklistOpenHelper.getIsCheck(whichPlan, line.substring(1)));

                    ArrayList<Pair<String, Boolean>> blank = new ArrayList<>();
                    Boolean pischeck = checklistOpenHelper.getIsCheck(whichPlan, line.substring(1));
                    Constraints tempCons = new Constraints(whichPlan, line.substring(1), pischeck, 0, 0, blank, checklistOpenHelper);
                    constraints.add(tempCons);
                    tempList.clear();
                } else {
                    if (line.contains("One of")) {
                        limitation = 1;
                    } else if (line.contains("Two of")) {
                        limitation = 2;
                    } else if (line.contains("Six of")) {
                        limitation = 6;
                    } else if (line.contains("All of")) {
                        flag = true;
                    }
                    layer = 2;
                    temp = line.substring(2);
                }

            } else {
                if (line.substring(0, 1).equals("!") && !line.substring(1, 2).equals("!")) {

                    Pair<String, Boolean> tempPair = new Pair<>(line.substring(1), checklistOpenHelper.getIsCheck(whichPlan, line.substring(1)));
                    tempList.add(tempPair);
                    ArrayList<Pair<String, Boolean>> newArray = new ArrayList<>();
                    for (int i = 0; i < tempList.size(); i++) {
                        newArray.add(i, tempList.get(i));
                    }
                    if (checklistOpenHelper.getIsCheck(whichPlan, line.substring(1))) {
                        current++;
                    }
                    child++;
                    if (flag) {
                        limitation = child;
                    }
                    Boolean pischeck = checklistOpenHelper.getIsCheck(whichPlan, temp);
                    Constraints tempCons = new Constraints(whichPlan, temp, pischeck, limitation, current, newArray, checklistOpenHelper);
                    constraints.add(tempCons);

                    layer = 1;
                    temp = "";
                    limitation = 0;
                    child = 0;
                    current = 0;
                    flag = false;
                    tempList.clear();

                } else {
                    Pair<String, Boolean> tempPair = new Pair<>(line.substring(2), checklistOpenHelper.getIsCheck(whichPlan, line.substring(2)));
                    tempList.add(tempPair);
                    child++;
                    if (checklistOpenHelper.getIsCheck(whichPlan, line.substring(2))) {
                        current++;
                    }
                }
            }
        }
    }

    public void insertCourses(String name, String whichUnit) {
        for (Pair<String, ArrayList<EachCourse>> course : courses) {
            if (course.first.equals(whichUnit)) {
                EachCourse temp = new EachCourse(whichPlan, name, false, false, checklistOpenHelper);
                course.second.add(temp);
                checklistOpenHelper.insertUserTable_Course(whichPlan, name, whichUnit);
            }
        }
    }

    public void deleteCourses(String name, String whichUnit) {
        for (Pair<String, ArrayList<EachCourse>> course : courses) {
            if (course.first.equals(whichUnit)) {
                for (int i = 0; i < course.second.size(); ++i) {
                    EachCourse tmpCourse = course.second.get(i);
                    String courseName = tmpCourse.getName();
                    if (name.equals(courseName)) {
                        course.second.remove(i);
                        checklistOpenHelper.deleteUserTable_Course(whichPlan, name, whichUnit);
                        return;
                    }
                }
            }
        }
    }

    public void changeCourseIsCheck(String name, String whichUnit) {
        for (Pair<String, ArrayList<EachCourse>> course : courses) {
            if (course.first.equals(whichUnit)) {
                for (EachCourse eachcourse : course.second) {
                    if (eachcourse.getName().equals(name)) {
                        eachcourse.changeIscheck();
                        return;
                    }
                }
            }
        }
    }

    public ArrayList<String> changeConstraintsIsCheck(String name) {
        ArrayList<String> retval = new ArrayList<>();
        for (Constraints constraints : constraints) {
            EachConstraints eachConstraints = constraints.findConstraint(name);
            if (eachConstraints != null) {
                retval.add(eachConstraints.getName());
                eachConstraints.setIsChecked((!eachConstraints.getIsChecked()));
                if (constraints.getParOrChild()) {
                    if (!eachConstraints.getIsChecked()) {
                        constraints.getRelation().first.setCurrent(0);
                    } else {
                        constraints.getRelation().first.setCurrent(constraints.getRelation().second.size());
                    }
                    for (EachConstraintsChild ecc : constraints.getRelation().second) {
                        if (ecc.getUpdated()) {
                            retval.add(ecc.getName());
                            ecc.setUpdated(false);
                        }
                    }
                    break;
                } else {
                    if (constraints.getRelation().first.getUpdated()) {
                        retval.add(constraints.getRelation().first.getName());
                        constraints.getRelation().first.setUpdated(false);
                        break;
                    }
                }
            }
        }
        /*
        for (String s : retval) {
            System.out.println(s);
        }
        */
        return retval;
    }
}
