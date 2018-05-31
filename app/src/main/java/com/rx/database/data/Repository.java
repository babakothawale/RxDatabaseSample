package com.rx.database.data;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.rx.database.db.DbDataSource;
import com.rx.database.db.Tables;
import com.rx.database.models.Shop;

import java.util.ArrayList;
import java.util.List;

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
            contentValues.add(new Tables.Shop.Builder().name("Shop " + 1).address("Address" + i).build());
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

    public static void loadShopData(Context context) {
        DbDataSource dbDataSource = DbDataSource.getInstance(context);
        dbDataSource.query("select * from " + Tables.Shop.TABLE_NAME)
                .mapToList(Tables.Shop.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Shop>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<Shop> shops) {
                        Log.d(TAG, "onNext: ");
                        for (Shop s : shops) {
                            Log.d(TAG, "\n" + s.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public static void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
