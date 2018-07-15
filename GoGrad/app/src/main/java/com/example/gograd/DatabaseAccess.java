package com.example.gograd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    //Tables
    public static final String Course_TABLE = "CourseDescription";
    public static final String Checklist_TABLE = "CheckList";
    //CourseDescription Columns
    public static final String COL_1 = "_ID";           //TEXT
    public static final String COL_2 = "CourseName";    //TEXT NOT NULL
    public static final String COL_3 = "Title";         //TEXT
    public static final String COL_4 = "Description";   //TEXT NOT NULL
    public static final String COL_5 = "Prereqs";       //TEXT
    public static final String COL_6 = "Successor";     //TEXT
    public static final String COL_7 = "Coreqs";        //TEXT
    public static final String COL_8 = "OpenNonCS";     //Integer(0/1)
    public static final String COL_9 = "FALL";          //Integer(0/1/null)
    public static final String COL_10 = "WINTER";       //Integer(0/1/null)
    public static final String COL_11 = "SPRING";       //Integer(0/1/null)
    //CheckList Columns
    public static final String COL_C1 = "_ID";           //TEXT
    public static final String COL_C2 = "Name";          //TEXT NOT NULL
    public static final String COL_C3 = "AdditionalConstraints"; //TEXT
    public static final String COL_C4 = "CSUnits";       //TEXT
    public static final String COL_C5 = "ElectiveUnits"; //TEXT
    public static final String COL_C6 = "NonMathUnits";  //TEXT
    public static final String COL_C7 = "MathUnits";     //TEXT
    public static final String COL_C8 = "TotalCS";       //Numeric
    public static final String COL_C9 = "TotalElective"; //Numeric
    public static final String COL_C10 = "TotalMath";    //Numeric
    public static final String COL_C11 = "TotalNonMath"; //Numeric
    public static final String COL_C12 = "WithOption";   //Integer
    public static final String COL_C13 = "Link";         //TEXT

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
            description += temp;
            description += "\t";
            temp = c.getString(c.getColumnIndexOrThrow(COL_4));
            if(temp == null){
                temp = "null";
            }
            description += temp;
            description += "\t";
            temp = c.getString(c.getColumnIndexOrThrow(COL_5));
            if(temp == null){
                temp = "null";
            }
            description += temp;
            description += "\t";
            temp = c.getString(c.getColumnIndexOrThrow(COL_7));
            if(temp == null){
                temp = "null";
            }
            description += temp;
        }
        c.close();
        return description;
    }

    public String getDescription(String name){
        String description = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Course_TABLE+" where "+COL_2+" = ?",
                params);
        while(c.moveToNext()){
            description = c.getString(c.getColumnIndexOrThrow(COL_4));
        }
        c.close();
        return description;
    }

    public String getTitle(String name){
        String title = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Course_TABLE+" where "+COL_2+" = ?",
                params);
        while(c.moveToNext()){
            title = c.getString(c.getColumnIndexOrThrow(COL_3));
        }
        c.close();
        return title;
    }

    public String getPrereqs(String name){
        String temp = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Course_TABLE+" where "+COL_2+" = ?",
                params);
        while(c.moveToNext()){
            temp = c.getString(c.getColumnIndexOrThrow(COL_5));
        }
        c.close();
        return temp;
    }

    public String getSuccessor(String name){
        String description = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Course_TABLE+" where "+COL_2+" = ?",
                params);
        while(c.moveToNext()){
            description = c.getString(c.getColumnIndexOrThrow(COL_6));
        }
        c.close();
        return description;
    }

    public String getCoreqs(String name){
        String description = "";
        String[] params = new String[]{name};
        Cursor c = db.rawQuery("select * from "+Course_TABLE+" where "+COL_2+" = ?",
                params);
        while(c.moveToNext()){
            description = c.getString(c.getColumnIndexOrThrow(COL_7));
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
}
