package com.example.arun.inclass07group14v1;

/**
 * Created by Arun on 10/23/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arun on 10/23/2017.
 */

public class ItuneDAO {

    private SQLiteDatabase db;

    public ItuneDAO(SQLiteDatabase db){
        this.db=db;

    }


    public long save(ItuneApp note)
    {

        ContentValues values= new ContentValues();
        values.put(ItuneTable.COLUMN_NAME,note.getName());
        values.put(ItuneTable.COLUMN_PRICE,note.getPrice());

        values.put(ItuneTable.COLUMN_URL,note.getImageURL());
        values.put(ItuneTable.COLUMN_SMALL_URL,note.getSmallImageURL());
        return db.insert(ItuneTable.TABLENAME,null,values);

    }
  /*  public boolean update(Note note)
    {
        ContentValues values= new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT,note.getSubject());
        values.put(NotesTable.COLUMN_TEXT,note.getText());
        return db.update(NotesTable.TABLENAME,values,NotesTable.COLUMN_ID + "=?", new String[]{note.getId()+""}) >0;

    }*/
    public boolean delete(ItuneApp note)
    {


        return db.delete(ItuneTable.TABLENAME,ItuneTable.COLUMN_ID +"=?", new String[]{note.getId()+""}) > 0;



    }
    public ItuneApp get(String name,Double price)
    {

        ItuneApp note=null;
        Cursor c= db.query(true,ItuneTable.TABLENAME, new String[]{ItuneTable.COLUMN_ID,ItuneTable.COLUMN_NAME,ItuneTable.COLUMN_PRICE,ItuneTable.COLUMN_URL,ItuneTable.COLUMN_SMALL_URL}
                ,ItuneTable.COLUMN_NAME +"=?"+" and "+ItuneTable.COLUMN_PRICE +"=?" , new String[]{name,price+" "},null,null,null,null,null
        );
        /*query (boolean distinct,
        String table,
        String[] columns,
        String selection,
        String[] selectionArgs,
        String groupBy,
        String having,
        String orderBy,
        String limit)*/

        if( c != null && c.moveToFirst())
        {
            note= buildNoteFromCursor(c);
            if(!c.isClosed()) {
                c.close();
            }
        }

        return note;
    }
    private ItuneApp buildNoteFromCursor(Cursor c)
    {
        ItuneApp note= null;
        if (c !=null)
        {
            note= new ItuneApp();
            note.setId(c.getInt(0));
            note.setName(c.getString(1));
            note.setPrice(c.getDouble(2));
            note.setImageURL(c.getString(3));
            note.setSmallImageURL(c.getString(4));
        }
        return note;
    }

    public List<ItuneApp> getAll()
    {
        List<ItuneApp> notes= new ArrayList<ItuneApp>();

        Cursor c= db.query(ItuneTable.TABLENAME,new String[]{ItuneTable.COLUMN_ID,ItuneTable.COLUMN_NAME,ItuneTable.COLUMN_PRICE,ItuneTable.COLUMN_URL,ItuneTable.COLUMN_SMALL_URL},null,null,null,null,null);
        if(c !=null && c.moveToFirst())
        {
            do{

                ItuneApp note =buildNoteFromCursor(c);
                if(note !=null)
                {
                    notes.add(note);
                }
            }while(c.moveToNext());

        }
        if(!c.isClosed()) {
            c.close();
        }
        return notes;
    }

}
