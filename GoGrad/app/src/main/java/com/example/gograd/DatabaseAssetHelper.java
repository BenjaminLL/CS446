package com.example.gograd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAssetHelper extends SQLiteAssetHelper {
    public static final String TABLE_NAME = "CourseDescription";
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

    private static final String DATABASE_NAME = "GoGrad.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    //TODO:: do not use this function
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

}