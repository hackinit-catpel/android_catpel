package com.muxistudio.catpel.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kolibreath on 17-7-8.
 */

public class MyDataBase extends SQLiteOpenHelper {

    private static final String CREATE_USER=  "create table user ("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "usertime integer)";

    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory
    ,int version){
        super(context,name,cursorFactory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        Log.d("sqlitedatabase create", "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
