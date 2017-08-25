package com.cognitive.face.recognition.api.helper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nellipc on 8/22/17.
 */

//primitive :int, double, long, short,
//objects: String, Integer,

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users_database.db";
    public static final String TABLE_NAME = "users";
    private static final int VERSION = 1;
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    public static final String COLUMN_USER_LAST_NAME = "user_last_name";
    public static final String COLUMN_USER_DOB = "user_dob";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + TABLE_NAME + "(id integer primary key autoincrement, "
                + COLUMN_USER_FIRST_NAME + " text not null, "
                + COLUMN_USER_LAST_NAME + " text not null, "
                + COLUMN_USER_DOB + " text not null, "
                + COLUMN_USER_EMAIL + " text not null, "
                + COLUMN_USER_PASSWORD  + " text not null );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
