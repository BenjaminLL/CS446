package com.example.gograd.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseAccess {
    //Tables
    private static final String Course_TABLE = "CourseDescription";
    private static final String Checklist_TABLE = "CheckList";
    private static final String Suggest_TABLE = "Suggest";
    //CourseDescription Columns
    public static final String COL_1 = "_ID";           //TEXT
    private static final String COL_2 = "CourseName";    //TEXT NOT NULL
    private static final String COL_3 = "Title";         //TEXT
    private static final String COL_4 = "Description";   //TEXT NOT NULL
    private static final String COL_5 = "Prereqs";       //TEXT
    public static final String COL_6 = "Successor";     //TEXT
    private static final String COL_7 = "Coreqs";        //TEXT
    private static final String COL_8 = "OpenNonCS";     //Integer(0/1)

    //CheckList Columns
    public static final String COL_C1 = "_ID";           //TEXT
    private static final String COL_C2 = "Name";          //TEXT NOT NULL
    private static final String COL_C3 = "AdditionalConstraints"; //TEXT
    private static final String COL_C4 = "CSUnits";       //TEXT
    private static final String COL_C5 = "ElectiveUnits"; //TEXT
    private static final String COL_C6 = "NonMathUnits";  //TEXT
    private static final String COL_C7 = "MathUnits";     //TEXT
    private static final String COL_C8 = "TotalCS";       //Numeric
    private static final String COL_C9 = "TotalElective"; //Numeric
    private static final String COL_C10 = "TotalMath";    //Numeric
    private static final String COL_C11 = "TotalNonMath"; //Numeric
    public static final String COL_C12 = "WithOption";   //Integer
    public static final String COL_C13 = "Link";         //TEXT

    //Suggest table
    public static final String COL_S1 = "_id";
    public static final String COL_S2 = "CSName";
    public static final String COL_S3 = "NeedCheck";
    public static final String COL_S4 = "OrHave";
    public static final String COL_S5 = "OrHave2";
    public static final String COL_S6 = "OrHave3";
    public static final String COL_S7 = "OrHave4";
    public static final String COL_S8 = "MustHave";
    public static final String COL_S9 = "OrCo";
    public static final String COL_S10 = "MustCo";
    public static final String COL_S11 = "Successor";
    public static final String COL_S12 = "FALL";
    public static final String COL_S13 = "WINTER";
    public static final String COL_S14 = "SPRING";

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private  static DatabaseAccess instance;

    //provide constructor so that object creation from outside the class is avoided
    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseAssetHelper(context);
    }

    //To return the singleton instance of database
    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //To open database
    public void open(){
        this.db = openHelper.getReadableDatabase();
    }

    //Close the database connection
    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    //Method to query and return results
    //Data from table CourseDescription
    public String getAllDescription(String name){
        String description = name + "\t";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Course_TABLE+" where "+COL_2+" = ?",
                params);
        while(c.moveToNext()){
            String temp = c.getString(c.getColumnIndexOrThrow(COL_3));
            if(temp == null){
                temp = "null";
            }
            description += temp+"\t";
            temp = c.getString(c.getColumnIndexOrThrow(COL_4));
            if(temp == null){
                temp = "null";
            }
            description += temp+"\t";
            temp = c.getString(c.getColumnIndexOrThrow(COL_5));
            if(temp == null){
                temp = "null";
            }
            description += temp + "\t";
            temp = c.getString(c.getColumnIndexOrThrow(COL_7));
            if(temp == null){
                temp = "null";
            }
            description += temp;
        }
        c.close();
        return description;
    }

    public int getOpenNonCS(String name){
        int i =-1;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Course_TABLE+" where "+COL_2+" = ?",
                params);
        while(c.moveToNext()){
            i = c.getInt(c.getColumnIndexOrThrow(COL_8));
        }
        c.close();
        return i;
    }

    //Data from table Checklist
    public String getCSUnits(String name){
        String temp = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("SELECT * FROM "+Checklist_TABLE+" WHERE "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getString(c.getColumnIndexOrThrow(COL_C4));
        }
        c.close();
        return temp;
    }

    public String getConstraints(String name){
        String temp = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getString(c.getColumnIndexOrThrow(COL_C3));
        }
        c.close();
        return temp;
    }

    public String getElective(String name){
        String temp = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getString(c.getColumnIndexOrThrow(COL_C5));
        }
        c.close();
        return temp;
    }

    public String getNonMath(String name){
        String temp = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getString(c.getColumnIndexOrThrow(COL_C6));
        }
        c.close();
        return temp;
    }

    public String getMath(String name){
        String temp = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getString(c.getColumnIndexOrThrow(COL_C7));
        }
        c.close();
        return temp;
    }

    public double getTotalCS(String name){
        double temp = 0.0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getDouble(c.getColumnIndexOrThrow(COL_C8));
        }
        c.close();
        return temp;
    }

    public double getTotalElective(String name){
        double temp = 0.0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getDouble(c.getColumnIndexOrThrow(COL_C9));
        }
        c.close();
        return temp;
    }

    public double getTotalMath(String name){
        double temp = 0.0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getDouble(c.getColumnIndexOrThrow(COL_C10));
        }
        c.close();
        return temp;
    }

    public double getTotalNonMath(String name){
        double temp = 0.0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Checklist_TABLE+" where "+COL_C2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getDouble(c.getColumnIndexOrThrow(COL_C11));
        }
        c.close();
        return temp;
    }

    /*
    Suggest TABLE!
    */

    public boolean getNeedCheck(String name){
        int temp = 0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Suggest_TABLE+" where "+COL_S2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getInt(c.getColumnIndexOrThrow(COL_S3));
        }
        boolean ret = temp==1 ? true : false;
        c.close();
        return ret;
    }

    //get all the "OR" requirement
    public ArrayList<List<String>> getOrHave(String name){
        String temp;
        String[] params = new String[]{name};
        ArrayList<List<String>> ret = new ArrayList<>();
        List<String> temp_ret;
        Cursor c = db.rawQuery("select * from "+Suggest_TABLE+" where "+COL_S2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getString(c.getColumnIndexOrThrow(COL_S4));
            if(temp != null && temp != ""){
                temp_ret = Arrays.asList(temp.split("[\\r?\\n]"));
                ret.add(temp_ret);
            }
            temp = c.getString(c.getColumnIndexOrThrow(COL_S5));
            if(temp != null && temp != ""){
                temp_ret = Arrays.asList(temp.split("[\\r?\\n]"));
                ret.add(temp_ret);
            }
            temp = c.getString(c.getColumnIndexOrThrow(COL_S6));
            if(temp != null && temp != ""){
                temp_ret = Arrays.asList(temp.split("[\\r?\\n]"));
                ret.add(temp_ret);
            }
            temp = c.getString(c.getColumnIndexOrThrow(COL_S7));
            if(temp != null && temp != ""){
                temp_ret = Arrays.asList(temp.split("[\\r?\\n]"));
                ret.add(temp_ret);
            }
        }
        c.close();
        return ret;
    }

    public List<String> getMustHave(String name){
        String temp;
        String[] params = new String[]{name};
        List<String> temp_ret = null;
        Cursor c = db.rawQuery("select * from "+Suggest_TABLE+" where "+COL_S2+" = ?",
                params);
        while(c.moveToNext()) {
            temp = c.getString(c.getColumnIndexOrThrow(COL_S8));
            if (temp != null && temp != "") {
                temp_ret = Arrays.asList(temp.split("[\\r?\\n]"));
            }
        }
        return temp_ret;
    }

    public List<String> getSuccessor(String name){
        String temp;
        String[] params = new String[]{name};
        List<String> temp_ret = null;
        Cursor c = db.rawQuery("select * from "+Suggest_TABLE+" where "+COL_S2+" = ?",
                params);
        while(c.moveToNext()) {
            temp = c.getString(c.getColumnIndexOrThrow(COL_S11));
            if (temp != null && temp != "") {
                temp_ret = Arrays.asList(temp.split("[\\r?\\n]"));
            }
        }
        return temp_ret;
    }

    // ie: getFALL("CS 136")
    public double getWINTER(String name){
        double temp = 0.0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Suggest_TABLE+" where "+COL_S2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getDouble(c.getColumnIndexOrThrow(COL_S13));
        }
        c.close();
        return temp;
    }

    public double getSPRING(String name){
        double temp = 0.0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Suggest_TABLE+" where "+COL_S2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getDouble(c.getColumnIndexOrThrow(COL_S14));
        }
        c.close();
        return temp;
    }

    public double getFALL(String name){
        double temp = 0.0;
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Suggest_TABLE+" where "+COL_S2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getDouble(c.getColumnIndexOrThrow(COL_S12));
        }
        c.close();
        return temp;
    }
}
