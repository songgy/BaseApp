/*
 * Copyright (c) 2015. zhengjin99.com. All rights reserved.
 */

package com.ly.baseapp.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    public static final int LOADING = -10;
    public static final int FAILURE = -11;
    public static final int DATA_NULL = -12;

    protected final Context mContext;
    protected final int mLayoutId;
    protected List<T> mItems;
    protected boolean mIsDataSplit;

    public BaseAdapter(Context context) {
        this(context, -1, null);
    }

    public BaseAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public BaseAdapter(Context context, int layoutId, List<T> items) {
        mContext = context;
        mLayoutId = layoutId;
        mItems = items == null ? new ArrayList<T>() : new ArrayList<T>(items);
    }

    public BaseAdapter(Context context, int layoutId, List<T> items, boolean isDataSplit) {

        mContext = context;
        mLayoutId = layoutId;
        mItems = items == null ? new ArrayList<T>() : new ArrayList<T>(items);
        mIsDataSplit = isDataSplit;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = getViewHolder(position, convertView, parent);
        convert(holder, position, getItem(position));
        return holder.getConvertView();
    }

    protected ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        if (mLayoutId == -1) throw new IllegalArgumentException("mLayoutId == -1");
        return ViewHolder.get(mContext, convertView, parent, mLayoutId, position);
    }

    protected abstract void convert(ViewHolder holder, int position, T item);

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mIsDataSplit == true ? mItems.size() * 2 : mItems.size();
    }

    @Override
    public T getItem(int position) {

        if (mIsDataSplit) {//是否拆分数据

            return mItems.get(position / 2);//如果拆分数据，则俩条item共享一条数据
        } else {

            if (position >= mItems.size()) {
                return null;
            }
            return mItems.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void add(int position, T item) {
        mItems.add(position, item);
        notifyDataSetChanged();
    }

    public void addAll(List<T> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addAll(int index, List<T> items) {
        mItems.addAll(index, items);
        notifyDataSetChanged();
    }

    public void set(T oldItem, T newItem) {
        set(mItems.indexOf(oldItem), newItem);
    }

    public void set(int index, T item) {
        mItems.set(index, item);
        notifyDataSetChanged();
    }

    public void remove(T item) {
        mItems.remove(item);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mItems.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public boolean contains(T item) {
        return mItems.contains(item);
    }

    /**
     * Clear data list
     */
    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }
}