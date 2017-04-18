package com.example.grihini.grihini.ExpensesTotal;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.grihini.grihini.R;

/**
 * Created by ag on 5/1/2015.
 */
public class ExpensesSimpleCursorAdapter1 extends SimpleCursorAdapter {
    public ExpensesSimpleCursorAdapter1(Context context, int layout, Cursor c, String[]
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
            //holder.colImp = cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_IMPORTANT);
            holder.date=cursor.getColumnIndexOrThrow(ExpensesDbAdapter1.COL_1);
            holder.category=cursor.getColumnIndexOrThrow(ExpensesDbAdapter1.COL_2);
            holder.amount=cursor.getColumnIndexOrThrow(ExpensesDbAdapter1.COL_3);
            holder.desc=cursor.getColumnIndexOrThrow(ExpensesDbAdapter1.COL_4);
            holder.listTab = view.findViewById(R.id.row_tab1);
            view.setTag(holder);
        }

    }
    static class ViewHolder {
        //store the column index
        int date;
        int category;
        int amount;
        int desc;
        //store the view
        View listTab;
    }
}