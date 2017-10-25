package com.example.arun.inclass07group14v1;

/**
 * Created by Arun on 10/23/2017.
 */

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Arun on 10/23/2017.
 */

public class ItuneTable {


    static final String TABLENAME="Filter";
    static final String COLUMN_ID="_id";
    static final String COLUMN_NAME="name";
    static final String COLUMN_PRICE="price";
    static final String COLUMN_URL="thumbURL";
    static final String COLUMN_SMALL_URL="smallURL";


    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb= new StringBuilder();
        sb.append("CREATE TABLE " +TABLENAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_NAME + " text , ");
        sb.append(COLUMN_URL + " text , ");
        sb.append(COLUMN_SMALL_URL + " text , ");
        sb.append(COLUMN_PRICE+ " REAL );");

        try{
            db.execSQL(sb.toString());
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        ItuneTable.onCreate(db);
    }

}

