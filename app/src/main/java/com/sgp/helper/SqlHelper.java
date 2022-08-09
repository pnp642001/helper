package com.sgp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

public class SqlHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME="NOTETAKER";
    public static final String ID="_id";
    public static final String TITLE="title";
    public static final String DESCRIPTION="description";
    public static final String DB_NAME="NOTETAKER_APPLICATION.DB";

    private static final String CREATE_TABLE="create table "+TABLE_NAME+ "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            TITLE+ " TEXT NOT NULL, "+DESCRIPTION+" TEXT NOT NULL);";


    public SqlHelper(Context context){

        super(context,DB_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
