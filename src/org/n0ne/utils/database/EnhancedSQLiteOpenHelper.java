package org.n0ne.utils.database;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class EnhancedSQLiteOpenHelper extends SQLiteOpenHelper {

    private int mDatabaseRawId;

    protected final Context mContext;

    private static final Pattern sCommandSeparatorPattern = Pattern.compile("[^;]+?;\\s*(?:;*?END;)?");

    public EnhancedSQLiteOpenHelper (Context context, String name, int version, int databaseRawId) {
        this(context.getApplicationContext(), name, version, databaseRawId, null);
    }

    public EnhancedSQLiteOpenHelper (Context context, String name, int version,
                                     int databaseRawId, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, version);
        mContext = context;
        mDatabaseRawId = databaseRawId;
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        final Scanner reader;
        String  command;

        try {
            reader = new Scanner(mContext.getResources().openRawResource(mDatabaseRawId));
        } catch (Resources.NotFoundException e) {
            throw new IllegalStateException(
                    "You should declare an .sql file under /res/raw, containing SQL" +
                            "code for initializing your database."
            );
        }

        while ((command = reader.findWithinHorizon(sCommandSeparatorPattern, 0)) != null) {
            db.execSQL(command);
        }

        reader.close();
    }

}
