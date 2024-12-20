package com.example.crudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Userdata.db";
    public static final String TABLE_NAME = "USER_DETAILS";
    public static final String COLUMN_USER_ID = "ID";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_CONTACT = "USER_CONTACT";
    public static final String COLUMN_USER_DOB = "USER_DOB";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_CONTACT + " TEXT, " + COLUMN_USER_DOB + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    public boolean insert_data(String name, String contact, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, name);
        cv.put(COLUMN_USER_CONTACT, contact);
        cv.put(COLUMN_USER_DOB, dob);
        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor view_data(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        return cursor;

    }

    public boolean update_data(String id, String name, String contact, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, name);
        cv.put(COLUMN_USER_CONTACT, contact);
        cv.put(COLUMN_USER_DOB, dob);
        long insert = db.update(TABLE_NAME,cv,"ID = ? ", new String[]{id} );

        return true;
    }

   public Integer delete_data(String id){
        SQLiteDatabase db = this.getWritableDatabase();
       int delete = db.delete(TABLE_NAME, "ID = ?", new String[]{id});
       return delete;

   }
}
