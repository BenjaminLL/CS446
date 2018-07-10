package com.example.gograd.utli;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitString {

    private List<String> course;
    private List<Pair<String, ArrayList<String>>> addition;

    public SplitString() {
        course = new ArrayList<>();
        addition = new ArrayList<>();
    }

    public List<String> getCourse() {
        return course;
    }

    public List<Pair<String, ArrayList<String>>> getAddition() {
        return addition;
    }

    public void spliteCourses(String s) {

        String[] lines = s.split("\\r?\\n");
        course.addAll(Arrays.asList(lines));

    }

    public void spliteConstraints(String s) {

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
