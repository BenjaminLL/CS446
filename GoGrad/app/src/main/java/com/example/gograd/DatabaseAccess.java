package com.example.gograd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private  static DatabaseAccess instance;
    Cursor c = null;

    //provide constructor so that object creation from outside the class is avoided
    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseAssetHelper(context);
    }

    //To return the single instance of database
    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //To open database
    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    //Close the database connection
    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    //Method to query and return results
    public String getDescription(String name){
        c = db.rawQuery("select Description from CourseDescription where Name = '"+name+"'",
                new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String description = c.getString(0);
            buffer.append(""+description);
        }
        return buffer.toString();
    }
}
