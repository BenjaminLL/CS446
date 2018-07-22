package com.example.gograd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Pair;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChecklistOpenHelper extends SQLiteOpenHelper {
    //Local database info
    private static final String DATABASE_NAME = "checklist.db";
    private static final int DATABASE_VERSION = 1;
    //Table name
    private static final String TABLE_NAME = "checklist";
    //Column name
    private static final String COL_1 = "_id";
    private static final String COL_2 = "entryid";
    private static final String COL_3 = "title";
    //checklist column name
    private static final String COL_C1 = "_id";
    private static final String COL_C2 = "requires";
    private static final String COL_C3 = "status";
    private static final String COL_C4 = "AddUnderCategory"; //TEXT

    //SQL
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                                                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                    COL_2 + TEXT_TYPE + COMMA_SEP +
                                                    COL_3 + TEXT_TYPE + ")";
    private static final String DELETE_TABLE = "DELETE FROM " + TABLE_NAME; //TODO::
    //member
    private Context checklistContext;

    public ChecklistOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory , int version) {
        super(context, DATABASE_NAME, null, version);
        checklistContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void createUserTable(String id,List<Pair<String, ArrayList<String>>> course, List<Pair<String, ArrayList<String>>> add) {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_TABLE_NEW_LIST = "CREATE TABLE [" + id + "] (" +
                COL_C1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_C2 + TEXT_TYPE + COMMA_SEP +
                COL_C3 + INT_TYPE + COMMA_SEP+
                COL_C4 + TEXT_TYPE + ")";
        db.execSQL(CREATE_TABLE_NEW_LIST);
        for(int i=0; i<course.size(); i++){
            Pair<String, ArrayList<String>> tempPair = course.get(i);
            ArrayList<String> temp = tempPair.second;
            for(int n=0; n<temp.size(); n++){
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_C2, temp.get(n));
                contentValues.put(COL_C3, 0);
                db.insert("[" + id + "]", null, contentValues);
            }
        }
        for(int j=0; j<add.size(); j++){
            Pair<String, ArrayList<String>> tempPair = add.get(j);
            ArrayList<String> temp = tempPair.second;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_C2, tempPair.first);
            contentValues.put(COL_C3, 0);
            db.insert("[" + id + "]", null, contentValues);
            for(int k=0; k<temp.size();k++){
                contentValues = new ContentValues();
                contentValues.put(COL_C2, temp.get(k));
                contentValues.put(COL_C3, 0);
                db.insert("[" + id + "]", null, contentValues);
            }
        }
        db.close();
    }

    public boolean getIsCheck(String id, String requires){
        SQLiteDatabase db = getReadableDatabase();
        String last = requires.substring(requires.length()-1);

        if(requires.equals("CS 115")||requires.equals("CS 135")||requires.equals("CS 145")){
            requires = "CS 1[134]5";
        }else if(requires.equals("CS 136")||requires.equals("CS 146")){
            requires = "CS 1[34]6";
        }
        if(requires.length() == 8){
            if(requires.substring(0,6).equals("MATH 1")){
                if(last.equals("5")){
                    requires = "MATH 1[34]5";
                }else if(last.equals("6")) {
                    requires = "MATH 1[34]6";
                }else if(last.equals("7")) {
                    requires = "MATH 1[234]7";
                }else if(last.equals("8")) {
                    requires = "MATH 1[234]8";
                }
            }else if(requires.substring(0,6).equals("MATH 2")){
                if(last.equals("9")){
                    requires = "MATH 2[34]9";
                }
            }else if(requires.substring(0,6).equals("STAT 2")){
                if(last.equals("0")){
                    requires = "STAT 2[34]0";
                }else if(last.equals("1")){
                    requires = "STAT 2[34]1";
                }
            }
        }

        Cursor cursor = db.rawQuery("SELECT "+COL_C3+" FROM ["+id+"] WHERE "+COL_C2
                +"=?",new String[]{requires});
        int status = 0;
        if(cursor.getCount() == 0){
            status = 0;
        }else{
            while (cursor.moveToNext()) {
                status = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_C3));
            }
        }
        boolean ret = status==1 ? true:false;
        return ret;
    }

    public ArrayList<String> getUncheck(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ["+id+"] WHERE "+COL_C3
                +"=?",new String[]{String.valueOf(0)});
        ArrayList<String> ret = new ArrayList<>();
        if(cursor.getCount() == 0){
            return ret;
        }else{
            String temp;
            while (cursor.moveToNext()) {
                temp = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_C2));
                ret.add(temp);
            }
        }
        return ret;
    }

    public boolean updateUserTable_Status(String id, String requires, boolean status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int myInt = status ? 1 : 0;
        contentValues.put(COL_C3, myInt);
        String[] args = new String[]{requires};
        int i = db.update("["+id+"]", contentValues, COL_C2+" =?", args);
        return i!= -1;
    }
    //Insert the courses added by user
    //return true if success
    //Set checked status to be unchecked by default
    public boolean insertUserTable_Course(String id, String requires, String Category){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_C2,requires);
        contentValues.put(COL_C3, 0);
        contentValues.put(COL_C4, Category);
        long result = db.insert("["+id+"]", null, contentValues);
        return result!= -1;
    }

    public int deleteUserTable_Course(String id, String requires, String Category){
        SQLiteDatabase db = getWritableDatabase();
        String selection = COL_C4+ "=? and "+COL_C2 + "=?";
        int deletedRows = db.delete("["+id+"]", selection, new String[]{Category,requires});
        return deletedRows;
    }

    //Usage: getIsOrigin("17/18BCS", "Non-math")
    //Category: "Non-math" or "Elective"
    public String getOriginUnderCategory(String id, String Category){
        SQLiteDatabase db = getReadableDatabase();
        String courses = "";
        Cursor cursor = db.rawQuery("SELECT "+COL_C2+" FROM ["+id+"] WHERE "+COL_C4
                +"=?",new String[]{Category});
        if(cursor.getCount() == 0){
            return null;
        }
        while (cursor.moveToNext()) {
            String temp = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_C2));
            courses += temp + "\n";
        }
        String ret = courses.trim();
        return ret;
    }
    //return true if the course is origin
    //else false
    public boolean getIsOrigin(String id, String requires){
        SQLiteDatabase db = getReadableDatabase();
        String courses = "";
        Cursor cursor = db.rawQuery("SELECT "+COL_C2+" FROM ["+id+"] WHERE ("+COL_C4+" IS NULL OR "+COL_C4
                +"=?) AND ("+COL_C2+"=?)" , new String[]{"", requires});
        boolean exist = (cursor.getCount()>0);
        return exist;
    }

    //eg: insertChecklist("07/08BCS","CustomizedName")
    public boolean insertChecklist(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, id);
        contentValues.put(COL_3, id);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    //GET list of customized checklist name
    public ArrayList<String> getList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        ArrayList<String> itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_3));
            itemIds.add(itemId);
        }
        cursor.close();
        return itemIds;
    }

    public boolean checkExistPlan(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COL_3+" FROM "+TABLE_NAME+" WHERE "+COL_3
                +"=?",new String[]{id});
        boolean exist = (cursor.getCount()>0);
        return exist;
    }

    // number id in Checklist database
    public ArrayList<Integer> getID(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        ArrayList<Integer> itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_1));
            itemIds.add(itemId + 200);
        }
        cursor.close();
        return itemIds;
    }

    //Delete by _id
    public void deleteCheckRecord(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE = "DROP TABLE " + "[" + id + "]";
        db.execSQL(DELETE);
    }

    //Delete by _id
    public int deleteChecklist(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_3 + "=?";
        int deletedRows = db.delete(TABLE_NAME, selection, new String[]{id});
        deleteCheckRecord(id);
        return deletedRows;
    }

    public void deleteDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_TABLE);
    }
}
