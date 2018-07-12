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
    /*
    private List<String> course;
    private List<Pair<String, ArrayList<String>>> addition;
    */
    public void createUserTable(String id,List<String> course, List<Pair<String, ArrayList<String>>> add) {
        final SQLiteDatabase db = getWritableDatabase();
        String CREATE_TABLE_NEW_LIST = "CREATE TABLE " + id + " (" +
                COL_C1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_C2 + TEXT_TYPE + COMMA_SEP +
                COL_C3 + INT_TYPE + ")";
        db.execSQL(CREATE_TABLE_NEW_LIST);
        //TODO::
        for(int i=0; i<course.size(); i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, course.get(i));
            contentValues.put(COL_3, 0);
            db.insert(id, null, contentValues);
        }
        //TODO::
        for(int j=0; j<add.size(); j++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, course.get(j));
            contentValues.put(COL_3, 0);
            db.insert(id, null, contentValues);
        }
        db.close();
    }

    public void updateUserTable_Status(String id, String requires, int status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_C3, status);
        String[] args = new String[]{requires};
        db.update(id, contentValues, COL_C2+" =?", args);
    }

    //eg: insertChecklist("07/08BCS","CustomizedName")
    public boolean insertChecklist(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, id);
        contentValues.put(COL_3, name);
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
    //TODO::
    public boolean updateData(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, id);
        contentValues.put(COL_3, name);
        String selection = COL_3 + " LIKE ?";
        String[] selectionArgs = {"MyOldTitle"};
        int count = db.update(
                COL_3,
                contentValues,
                selection,
                selectionArgs);
        return count != -1;
    }

    //Delete by _id
    public int deleteChecklist(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_3 + "=?";
        int deletedRows = db.delete(COL_3, selection, new String[]{Integer.toString(id)});
        return deletedRows;
    }

    //TEST usage: add default usr check list
    public void TestDefaultChecklist(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, "17/18BCS");
        contentValues.put(COL_3, "My 17/18BCS");
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
}
