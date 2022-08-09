package com.sgp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SqlManager {

    private SqlHelper sqlHelper;
    private Context context;
    private SQLiteDatabase database;

    public SqlManager(Context context){

        this.context=context;

    }

    public SqlManager openDataBase() throws SQLException{

        sqlHelper=new SqlHelper(context);

        database=sqlHelper.getWritableDatabase();

        return this;
    }

    public void closeDataBase() {
        sqlHelper.close();
    }

    public void insert(String title,String description){
        ContentValues contentValues=new ContentValues();


        contentValues.put(sqlHelper.TITLE,title);
        contentValues.put(sqlHelper.DESCRIPTION,description);
        database.insert(sqlHelper.TABLE_NAME,null,contentValues);

    }

    public Cursor fetchData(){

        String columnNames[]=new String[]{sqlHelper.ID,sqlHelper.TITLE,sqlHelper.DESCRIPTION};
        Cursor cursor= database.query(sqlHelper.TABLE_NAME,
                columnNames,
                null,
                null,
                null,
                null,
                null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public int update(long id,String title,String description){

        ContentValues contentValues=new ContentValues();

        contentValues.put(sqlHelper.TITLE,title);
        contentValues.put(sqlHelper.DESCRIPTION,description);

        int status=database.update(sqlHelper.TABLE_NAME,contentValues,sqlHelper.ID+"="+id,null);

        return status;
    }

    public void delete(long id){

        database.delete(sqlHelper.TABLE_NAME,sqlHelper.ID+"="+id,null);
    }
}
