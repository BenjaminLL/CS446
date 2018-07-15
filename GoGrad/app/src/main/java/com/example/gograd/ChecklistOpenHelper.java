package com.example.gograd;

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
        Toast.makeText(checklistContext, "Create ChecklistDB success!", Toast.LENGTH_SHORT).show();
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
//        System.out.println("Enter CreateUserTable function!");
        String CREATE_TABLE_NEW_LIST = "CREATE TABLE [" + id + "] (" +
                COL_C1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_C2 + TEXT_TYPE + COMMA_SEP +
                COL_C3 + INT_TYPE + COMMA_SEP+
                COL_C4 + TEXT_TYPE + ")";
        db.execSQL(CREATE_TABLE_NEW_LIST);
//        if(tableExists(db, id)){
//            System.out.println("TABLE created for "+id);
//        }else{
//            System.out.println("TABLE create failure for "+id);
//        }
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
            for(int k=0; k<temp.size();k++){
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_C2, temp.get(k));
                contentValues.put(COL_C3, 0);
                db.insert("[" + id + "]", null, contentValues);
            }
        }
        db.close();
    }

    public boolean getIsCheck(String id, String requires){
        System.out.println("getIsCheck called !");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COL_C3+" FROM ["+id+"] WHERE "+COL_C2
                +"=?",new String[]{requires});
        int status = 0;
        while (cursor.moveToNext()) {
            status = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_C3));
        }
        boolean ret = status==1 ? true:false;
        System.out.println("getIsCheck called success!");
        return ret;
    }

    public boolean updateUserTable_Status(String id, String requires, boolean status){
        System.out.println("updateUserTable_Course called update check status " + requires);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int myInt = status ? 1 : 0;
        contentValues.put(COL_C3, myInt);
        String[] args = new String[]{requires};
        int i = db.update("["+id+"]", contentValues, COL_C2+" =?", args);

        System.out.println("updateUserTable_Course success!");

        return i!= -1;
    }
    //Insert the courses added by user
    //return true if success
    //Set checked status to be unchecked by default
    public boolean insertUserTable_Course(String id, String requires, String Category){
        System.out.println("insertUserTable_Course called user add their course " + requires);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_C2,requires);
        contentValues.put(COL_C3, 0);
        contentValues.put(COL_C4, Category);
        long result = db.insert("["+id+"]", null, contentValues);

        System.out.println("insertUserTable_Course success");

        return result!= -1;
    }
    //Usage: getIsOrigin("17/18BCS", "Non-math")
    //Category: "Non-math" or "Elective"
    public String getOriginUnderCategory(String id, String Category){
        System.out.println("getOriginUnderCategory called!");
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
        System.out.println("getOriginUnderCategory success: " + ret);
        return ret;
    }
    //return true if the course is added by user
    //else false
    public boolean getIsOrigin(String id, String requires){
        System.out.println("getIsOrigin called!");
        SQLiteDatabase db = getReadableDatabase();
        String courses = "";
        Cursor cursor = db.rawQuery("SELECT "+COL_C2+" FROM ["+id+"] WHERE "+COL_C4+" IS NULL OR "+COL_C4
                +"=?", new String[]{""});
        boolean exist = (cursor.getCount()==0);
        System.out.println("getIsOriginal success!");
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

    //TEST usage: add default usr check list
    public void TestDefaultChecklist(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, "17/18BCS");
        contentValues.put(COL_3, "17/18BCS");
        db.insert(TABLE_NAME, null, contentValues);
        /*
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL_2, "09/10BCS");
        contentValues2.put(COL_3, "MY SECOND CHECKLIST");
        db.insert(TABLE_NAME, null, contentValues2);
        */
    }

    public void deleteDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_TABLE);
    }
    /*
    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            System.out.println("ERROR: checklist.db open error!");
        }
        return checkDB != null;
    }
    */
    boolean tableExists(SQLiteDatabase db, String tableName)
    {
        System.out.println("Enter checked process");
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
}
