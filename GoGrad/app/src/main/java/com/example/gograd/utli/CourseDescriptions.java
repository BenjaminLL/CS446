package com.example.gograd.utli;

import android.content.Context;

import com.example.gograd.DatabaseAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseDescriptions {
    private ArrayList<Content> ListOfDescriptions;
    private Context context;
    private String query;

    public class Content{
        private String CourseName;
        private String Title;
        private String Description;
        private String Prereq;
        private String Coreq;

        public Content(String n, String t, String d, String p, String c){
            CourseName = n;
            Title = t;
            Description = d;
            Prereq = p;
            Coreq = c;
        }

        public String getCourseName() {
            return CourseName;
        }

        public String getT() {
            return Title;
        }

        public String getD() {
            return Description;
        }

        public String getPrereq() {
            return Prereq;
        }

        public String getCoreq() {
            return Coreq;
        }
    }

    public CourseDescriptions(Context c, String q){
        query = q;
        context = c;
    }

    public ArrayList<Content> getListOfDescriptions(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        if (true){ //TODO::
            String description = databaseAccess.getAllDescription(query);
            Content temp = getCont(description);
            ListOfDescriptions.add(temp);
        }
        databaseAccess.close();
        return ListOfDescriptions;
    }

    public Content getCont(String RawString){
        List<String> segments = Arrays.asList(RawString.split("\t"));
        String s1,s2,s3,s4,s5;
        s1 = segments.get(0);
        s2 = segments.get(1);
        s3 = segments.get(2);
        s4 = segments.get(3);
        s5 = segments.get(4);
        Content ret = new Content(s1, s2, s3, s4, s5);
        return ret;
    }

}
