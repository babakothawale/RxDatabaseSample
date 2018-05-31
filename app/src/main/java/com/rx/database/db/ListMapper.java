
package com.rx.database.db;

import android.database.Cursor;

import java.util.List;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

final class ListMapper<T> implements ObservableOperator<List<T>, Cursor> {
    private final Function<Cursor, T> mapper;

    ListMapper(Function<Cursor, T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Observer<? super Cursor> apply(Observer<? super List<T>> observer) {
        return new CursorMapObservable<>(observer, mapper);
    }
}
