package com.rx.database.db;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class CursorObservable extends Observable<Cursor> {

    private final Observable<Cursor> upstream;

    public CursorObservable(Observable<Cursor> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void subscribeActual(Observer<? super Cursor> observer) {
        upstream.subscribe(observer);
    }

    public final <T> Observable<List<T>> mapToList(@NonNull Function<Cursor, T> mapper) {
        return lift(new ListMapper<>(mapper));
    }
}
