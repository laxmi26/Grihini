package com.example.grihini.grihini.Shopping;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.grihini.grihini.R;

/**
 * Created by ag on 5/1/2015.
 */
public class ShoppingSimpleCursorAdapter extends SimpleCursorAdapter {
    public ShoppingSimpleCursorAdapter(Context context, int layout, Cursor c, String[]
            from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
    //to use a viewholder, you must override the following two methods and define a ViewHolder class
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.listTab = view.findViewById(R.id.row_tab);
            view.setTag(holder);
        }

    }
    static class ViewHolder {

        View listTab;
    }
}