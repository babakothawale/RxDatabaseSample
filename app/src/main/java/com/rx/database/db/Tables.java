package com.rx.database.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.rx.database.models.Owner;
import com.rx.database.models.Shop;

import io.reactivex.functions.Function;

public class Tables {

    private static class BaseColumn {
        static String _ID = "_id";
    }

    public static class Owner extends BaseColumn {
        //owner information table
        public static String TABLE_NAME_OWNER = "owner";

        //owner table column name
        public static String OWNER_ID = "owner_id";
        public static String OWNER_NAME = "name";
        public static String OWNER_PHONE ="phone";
        public static String OWNER_ADD ="address";

        //create owner table
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_OWNER + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + OWNER_ID + " INTEGER,"
                + OWNER_NAME + " TEXT,"
                + OWNER_PHONE + " TEXT,"
                + OWNER_ADD + " TEXT" + ")";

        public static final Function<Cursor, com.rx.database.models.Owner> MAPPER = new Function<Cursor, com.rx.database.models.Owner>() {
            @Override public com.rx.database.models.Owner apply(Cursor cursor) {
                long id = ColumnValue.getLong(cursor, _ID);
                long ownerId = ColumnValue.getLong(cursor, OWNER_ID);
                String name = ColumnValue.getString(cursor, OWNER_NAME);
                String phone = ColumnValue.getString(cursor, OWNER_PHONE);
                String address = ColumnValue.getString(cursor, OWNER_ADD);
                return new com.rx.database.models.Owner(id,ownerId, name,phone, address);
            }
        };



        public static class Builder {
            private ContentValues values;
            public Builder(){
                values = new ContentValues();
            }

            public Builder ownerID(long ownerId){
                values.put(OWNER_ID, ownerId);
                return this;
            }


            public Builder name(String shopName){
                values.put(OWNER_NAME, shopName);
                return this;
            }

            public Builder phone(String phone){
                values.put(OWNER_PHONE, phone);
                return this;
            }


            public Builder address(String shopAddress){
                values.put(OWNER_ADD, shopAddress);
                return this;
            }



            public ContentValues build(){
                return values;
            }
        }
    }

    public static class Shop extends BaseColumn {
        public static String TABLE_NAME = "shop";
        public static String NAME = "shop_name";
        public static String ADDRESS = "shop_address";


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



        public static class Builder {
            private ContentValues values;
            public Builder(){
                values = new ContentValues();
            }

            public Builder name(String shopName){
                values.put(NAME, shopName);
                return this;
            }


            public Builder address(String shopAddress){
                values.put(ADDRESS, shopAddress);
                return this;
            }



            public ContentValues build(){
                return values;
            }
        }
    }
}
