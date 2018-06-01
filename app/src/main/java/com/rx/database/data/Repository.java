package com.rx.database.data;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.rx.database.db.DbDataSource;
import com.rx.database.db.Tables;
import com.rx.database.models.Owner;
import com.rx.database.models.Shop;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private static final String TAG = "Repository";
    private static Disposable disposable;

    //TODO: remove : only for testing

    public static void insertShopData(Context context) {
        DbDataSource dbDataSource = DbDataSource.getInstance(context);
        List<ContentValues> contentValues = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            contentValues.add(new Tables.Shop.Builder().name("Shop " + i).address("Address" + i).build());
        }

        dbDataSource.insert(Tables.Shop.TABLE_NAME, contentValues)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "insert success: " + aBoolean);
                    }
                });
    }

    //insert owner data
    public static void insertOwnerData(Context context) {
        DbDataSource dbDataSource = DbDataSource.getInstance(context);
        List<ContentValues> contentValues = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            contentValues.add(new Tables.Owner.Builder().ownerID(10+i).name("sohil " + i).phone("9098898890").address("Address" + i).build());
        }

        dbDataSource.insert(Tables.Owner.TABLE_NAME_OWNER, contentValues)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "insert success: " + aBoolean);
                    }
                });
    }

    public static Observable<List<Shop>> loadShopData(Context context) {
        DbDataSource dbDataSource = DbDataSource.getInstance(context);
        return dbDataSource.query("select * from " + Tables.Shop.TABLE_NAME)
                .mapToList(Tables.Shop.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }

    public static Observable<List<Owner>> loadOwnerData(Context context) {
        DbDataSource dbDataSource = DbDataSource.getInstance(context);
        return dbDataSource.query("select * from " + Tables.Owner.TABLE_NAME_OWNER)
                .mapToList(Tables.Owner.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }

    public static void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
