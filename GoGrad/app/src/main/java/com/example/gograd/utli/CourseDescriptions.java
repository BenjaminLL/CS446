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

    public static class Content{
        private String CourseName;
        private String Title;
        private String Description;
        private String Prereq;
        private String Coreq;

        public Content(String n, String t, String d, String p, String c){
            this.CourseName = n;
            this.Title = t;
            this.Description = d;
            this.Prereq = p;
            this.Coreq = c;
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
        query = q.trim();
        context = c;
        ListOfDescriptions = new ArrayList<>();
    }

    public ArrayList<Content> getListOfDescriptions(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        List<String> segmentsBracket = Arrays.asList(query.split("[\\[\\]]"));
        List<String> segmentsOR = Arrays.asList(query.split("\\sor\\s"));
        if(segmentsBracket.size()==1 && segmentsOR.size()==1){
            if(query.substring(0,6).equals("(Rec: ")){
                query = query.trim();
                query = query.substring(6, query.length()-1);
                System.out.println("query = "+query);
            }
            String description = databaseAccess.getAllDescription(query);
            ListOfDescriptions.add(getCont(description));
        }else if(segmentsBracket.size()==1){
            for(int i=0; i<segmentsOR.size(); i++){
                System.out.println("segmentOr " + segmentsOR.get(i));
                String description = databaseAccess.getAllDescription(segmentsOR.get(i));
                ListOfDescriptions.add(getCont(description));
            }
        }else{
            String head = segmentsBracket.get(0).substring(0,3);
            String Classes = segmentsBracket.get(1);
            int numClasses = Classes.length();
            for(int i=0; i<numClasses; i++){
                String queryClass = segmentsBracket.get(0);
                queryClass+=Classes.charAt(i);
                queryClass+=segmentsBracket.get(2);
                String description = databaseAccess.getAllDescription(queryClass);
                ListOfDescriptions.add(getCont(description));
            }
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
