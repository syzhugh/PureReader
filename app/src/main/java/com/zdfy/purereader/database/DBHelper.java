package com.zdfy.purereader.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yaozong on 2016/9/26.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "PureReader.db";

    public static final String TAB1NAME = "videofav";
    public static final String TAB1COLUME1_ID = "_id";
    public static final String TAB1COLUME2_REMOTEID = "_remoteid";
    public static final String TAB1COLUME3_DATA = "_data";


    private Context context;
    private String sqlCreate = "CREATE TABLE IF NOT EXISTS videofav (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "_remoteid INTEGER NOT NULL," +
            "_data TEXT NOT NULL" +
            ")";


    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
