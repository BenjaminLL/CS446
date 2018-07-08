package com.example.gograd.utli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SplitString {

    private ArrayList<String> course;
    private Map<String, ArrayList<String>> addition;

    public SplitString() {
        course = new ArrayList<>();
        addition = new HashMap<>();
    }

    public ArrayList<String> getCourse() {
        return course;
    }

    public Map<String, ArrayList<String>> getAddition() {
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

        ArrayList<String> temparrlist = new ArrayList<>();

        for (String line : lines) {

            if (line.equals("Non-math units that satisfy breadth and depth requirement:")) {
                continue;
            }

            if (layer == 1) {

                if (line.substring(0, 1).equals("!") && !line.substring(1, 2).equals("!")) {
                    temparrlist.add(line.substring(1));
                    addition.put("bottom", temparrlist);
                    continue;
                }
                else {
                    layer = 2;
                    temp = line.substring(2);
                    continue;
                }

            }
            else {
                if (line.substring(0, 1).equals("!") && !line.substring(1, 2).equals("!")) {
                    temparrlist.add(line.substring(1));
                    layer = 1;
                    ArrayList<String> newArray = new ArrayList<>();
                    for(int i = 0; i < temparrlist.size(); i++) {
                        newArray.add(i, temparrlist.get(i));
                    }
                    addition.put(temp, newArray);
                    temparrlist.clear();
                    temp = "";
                }
                else {
                    temparrlist.add(line.substring(2));
                    continue;
                }
            }
        }
    }
}
