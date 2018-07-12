package com.example.gograd;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "GoGrad.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Course_TABLE);
        onCreate(db);
    }
    */
}