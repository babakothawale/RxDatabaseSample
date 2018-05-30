package com.rx.database.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DbDataSource {
    private Database db;

    public DbDataSource(Context context) {
        db = new Database(context);
    }

    public Observable<Boolean> insert(final String tableName, final List<ContentValues> data) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                db.insert(tableName, data);
                return true;
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Integer> update(final String tableName, final ContentValues data, final String where) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return db.update(tableName, data, where);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Integer> delete(final String tableName, final String where) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return db.delete(tableName, where);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Cursor> query(final String query) {
        return Observable.fromCallable(new Callable<Cursor>() {
            @Override
            public Cursor call() throws Exception {
                return db.query(query);
            }
        }).subscribeOn(Schedulers.io());
    }
}
