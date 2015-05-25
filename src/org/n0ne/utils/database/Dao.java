package org.n0ne.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public abstract class Dao<T> {

    protected Context mContext;
    protected SQLiteOpenHelper mHelper;

    public Dao (Context context, SQLiteOpenHelper helper) {
        mContext = context.getApplicationContext();
        mHelper = helper;
    }

    public abstract List<T> read (QueryFilter filter);

    public abstract boolean create (T data);

    public abstract int update (ContentValues values, String whereClause, String[] whereArgs);

    public abstract int delete (String whereClause, String[] whereArgs);

    public abstract T createOrUpdate (T data) throws UnsupportedOperationException;

}
