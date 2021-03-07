package com.gjiazhe.layoutswitch.view2;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * 适配器
 */
public abstract class GridAdapter implements IAdapter {
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    protected abstract int getItemCount();

    public abstract int getItemId(int position);

    public abstract View onCreateView(int position, ViewGroup viewGroup);

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }
}
