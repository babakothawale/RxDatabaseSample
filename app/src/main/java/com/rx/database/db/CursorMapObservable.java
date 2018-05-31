package com.rx.database.db;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;

final class CursorMapObservable<T> extends DisposableObserver<Cursor> {
    private final Observer<? super List<T>> downstream;
    private final Function<Cursor, T> mapper;

    CursorMapObservable(Observer<? super List<T>> downstream, Function<Cursor, T> mapper) {
        this.downstream = downstream;
        this.mapper = mapper;
    }

    @Override
    protected void onStart() {
        downstream.onSubscribe(this);
    }

    @Override
    public void onNext(Cursor cursor) {
        try {
            if (cursor == null || isDisposed()) {
                return;
            }
            List<T> items = new ArrayList<>(cursor.getCount());
            try {
                while (cursor.moveToNext()) {
                    items.add(mapper.apply(cursor));
                }
            } finally {
                cursor.close();
            }
            if (!isDisposed()) {
                downstream.onNext(items);
            }
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            onError(e);
        }
    }

    @Override
    public void onComplete() {
        if (!isDisposed()) {
            downstream.onComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (isDisposed()) {
            RxJavaPlugins.onError(e);
        } else {
            downstream.onError(e);
        }
    }
}
