package com.rx.database.db;

import android.database.Cursor;

public final class ColumnValue {

  public static String getString(Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
  }

  public static boolean getBoolean(Cursor cursor, String columnName) {
    return getInt(cursor, columnName) == 1;
  }

  public static long getLong(Cursor cursor, String columnName) {
    return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
  }

  public static int getInt(Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
  }

}
