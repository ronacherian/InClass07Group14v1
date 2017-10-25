package com.example.arun.inclass07group14v1;

/**
 * Created by Arun on 10/23/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arun on 10/23/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    static  final String DB_NAME="mytunes.db";
    static final int DB_VERSION=3;

    public DatabaseOpenHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        ItuneTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        ItuneTable.onUpgrade(db,oldVersion,newVersion);
    }
}

