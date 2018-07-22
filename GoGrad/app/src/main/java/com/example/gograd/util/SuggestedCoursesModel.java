package com.example.gograd.util;

import android.content.Context;

import com.example.gograd.database.ChecklistOpenHelper;
import com.example.gograd.database.DatabaseAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestedCoursesModel {

    private DatabaseAccess databaseAccess;
    private ChecklistOpenHelper checklistOpenHelper;
    private String plan;

    private List<String> resultCourses;

    public SuggestedCoursesModel(String plan, Context context) {

        this.plan = plan;
        System.out.println(plan);

        checklistOpenHelper = new ChecklistOpenHelper(context, "checklist.db", null, 1);
        resultCourses = new ArrayList<>();

        ArrayList<String> unCheckedCourses = checklistOpenHelper.getUncheck(plan);

        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        boolean constraints = false;

        for (int i = 0; i < unCheckedCourses.size(); ++i) {

            String courseName = unCheckedCourses.get(i);

            boolean isValid = databaseAccess.getIsValid(courseName);

            if (isValid) {

                boolean isSatisfies = testConditions(courseName);

                if (isSatisfies) {
                    resultCourses.add(courseName);
                } else {
                    continue;
                }

            } else {

                if (courseName.contains(":")) {
                    constraints = true;
                    continue;
                }

                if (constraints) {

                    List<String> names = splitString(courseName);

                    for (int j = 0; j < names.size(); ++j) {

                        String name = names.get(j);
                        isValid = databaseAccess.getIsValid(name);

                        if (isValid) {

                            boolean isSatisfies = testConditions(name);

                            if (isSatisfies) {
                                resultCourses.add(name);
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }

        databaseAccess.close();
    }

    /**
     * Test if the course satisfies all the prerequisite
     */
    private boolean testConditions(String courseName) {

        if (!databaseAccess.getNeedCheck(courseName)) {
            return true;
        }

        ArrayList<List<String>> orHave = databaseAccess.getOrHave(courseName);
        List<String> mustHave = databaseAccess.getMustHave(courseName);

        if (mustHave != null && !checkMustHave(mustHave) || !checkOrHave(orHave, courseName)) {
            return false;
        }

        return true;
    }

    /**
     * split the string to a array of course names
     */
    private List<String> splitString(String courseName) {

        List<String> courses = new ArrayList<>();

        List<String> tmpStore = Arrays.asList(courseName.split(", "));

        String courseTag = "";
        String courseNum = "";

        if (tmpStore.size() != 0) {

            for (int i = 0; i < tmpStore.size(); ++i) {
                String tmpName = tmpStore.get(i);
                String[] splited = tmpName.split("\\s+");

                if (splited.length > 1) {
                    courseTag = splited[splited.length - 2];
                    courseNum = splited[splited.length - 1];
                } else if (splited.length == 1) {
                    courseNum = splited[0];
                }

                if (!courseTag.equals("") && isNumeric(courseNum)) {
                    courses.add(courseTag + " " + courseNum);
                }

                courseNum = "";
            }
        }

        return courses;
    }

    /**
     * Test if the string is a valid course num
     */
    private boolean isNumeric(String courseNum) {

        try {
            int num = Integer.parseInt(courseNum);
        } catch (NumberFormatException ex) {

            try {
                int num = Integer.parseInt(courseNum.substring(0, courseNum.length()-1));
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private boolean checkOrHave(ArrayList<List<String>> orHave, String Name) {

        for (int i = 0; i < orHave.size(); ++i) {
            List<String> choices = orHave.get(i);

            for (int j = 0; j < choices.size(); ++j) {

                String courseName = choices.get(j);
                boolean checked = checklistOpenHelper.getIsCheck(plan, courseName);

                if (checked) break;
                if (!checked && j == choices.size() - 1) return false;
            }
        }

        return true;
    }

    private boolean checkMustHave(List<String> mustHave) {

        for (int i = 0; i < mustHave.size(); ++i) {
            String courseName = mustHave.get(i);
            boolean checked = checklistOpenHelper.getIsCheck(plan, courseName);
            if (!checked) return false;
        }

        return true;
    }


    /**
     * getter and setter
     */
    public List<String> getResultCourses() {
        return resultCourses;
    }

    public void setResultCourses(List<String> resultCourses) {
        this.resultCourses = resultCourses;
    }
}
