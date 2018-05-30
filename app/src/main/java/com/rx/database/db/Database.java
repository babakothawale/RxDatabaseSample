package com.rx.database.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "shopsInfo";

    // Contacts table name
    private static final String TABLE_SHOPS = "shops";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tables.Shop.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
        // Creating tables again
        onCreate(db);
    }

    public void insert(String table, List<ContentValues> data) {
        //TODO: implement bulk insert
        SQLiteDatabase db = getWritableDatabase();
        for (ContentValues v : data) {
            db.insert(table, null, v);
        }
    }

    public int update(String table, ContentValues values, String where) {
        return getWritableDatabase().update(table, values, where, null);
    }

    public int delete(String table, String where) {
        return getWritableDatabase().delete(table, where, null);
    }

    public Cursor query(String query) {
        return getReadableDatabase().rawQuery(query, null);
    }
}

