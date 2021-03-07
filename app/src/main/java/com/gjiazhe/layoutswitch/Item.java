package com.gjiazhe.layoutswitch;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by gjz on 18/01/2017.
 */

public class Item {
    private int imgResId;
    private String title;
    private int likes;
    private int comments;

    public static void main(String[] args) {
        ListView listView = new ListView(null);
        listView.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
    }

    public Item(int imgResId) {
        this.imgResId = imgResId;
    }

    public Item(int imgResId, String title, int likes, int comments) {
        this.imgResId = imgResId;
        this.title = title;
        this.likes = likes;
        this.comments = comments;
    }

    public int getImgResId() {
        return imgResId;
    }

    public String getTitle() {
        return title;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }
}
