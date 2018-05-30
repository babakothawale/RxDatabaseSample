package com.rx.database.db;

import android.database.Cursor;

import com.rx.database.models.Shop;

import io.reactivex.functions.Function;

public class Tables {

    private static class BaseColumn {
        static String _ID = "_id";
    }

    static class Shop extends BaseColumn {
        static String TABLE_NAME = "shop";

        static String NAME = "shop_name";
        static String ADDRESS = "shop_address";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + ADDRESS + " TEXT" + ")";

        public static final Function<Cursor, com.rx.database.models.Shop> MAPPER = new Function<Cursor, com.rx.database.models.Shop>() {
            @Override public com.rx.database.models.Shop apply(Cursor cursor) {
                long id = ColumnValue.getLong(cursor, _ID);
                String name = ColumnValue.getString(cursor, NAME);
                String address = ColumnValue.getString(cursor, ADDRESS);
                return new com.rx.database.models.Shop(id, name, address);
            }
        };
    }
}
