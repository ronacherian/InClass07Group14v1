package com.example.arun.inclass07group14v1;

/**
 * Created by Arun on 10/23/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Arun on 10/23/2017.
 */

public class DataBaseManager {


    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private ItuneDAO noteDAO;

    public  DataBaseManager(Context mContext)
    {
        this.mContext=mContext;
        dbOpenHelper=new DatabaseOpenHelper(this.mContext);
        db=dbOpenHelper.getWritableDatabase();
        noteDAO= new ItuneDAO(db);
    }

    public void close()
    {
        if(db !=null)
        {
            db.close();
        }
    }
    public  ItuneDAO getNoteDAO()
    {
        return this.noteDAO;
    }
    public long saveNote(ItuneApp note)
    {
        return this.noteDAO.save(note);
    }

  /*//  public boolean updateNote(ItuneApp note)
    {
        return this.noteDAO.update(note);
    }*/
    public boolean deleteNote(ItuneApp note)
    {
        return this.noteDAO.delete(note);
    }
    public ItuneApp getNote(String name,Double price)
    {
        return this.noteDAO.get(name,price);
    }


    public List<ItuneApp> getAllNotes()
    {
        return this.noteDAO.getAll();
    }
}

