package com.example.grihini.grihini;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by laxmee on 5/22/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {



    private static final String DATABASE_NAME="EXPENSES.DB";
    private static final int DATABASE_VERSION=1;
    public static final String CREATE_QUERY =
            "CREATE TABLE " + ExpensesContract.NewExpensesInfo.TABLE_NAME +"("+
                    ExpensesContract.NewExpensesInfo.COL_2+" TEXT,"+  ExpensesContract.NewExpensesInfo.COL_3+" TEXT,"+  ExpensesContract.NewExpensesInfo.COL_4+" INTEGER,"+
                    ExpensesContract.NewExpensesInfo.COL_5+" TEXT);";



    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("Database Creation","Database created");

    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.e("Database Creation", "table created");

    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }

    public void insertData(String date,String category, String amount, String description,SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ExpensesContract.NewExpensesInfo.COL_2,date);
        contentValues.put(ExpensesContract.NewExpensesInfo.COL_3,category);
        contentValues.put(ExpensesContract.NewExpensesInfo.COL_4,amount);
        contentValues.put(ExpensesContract.NewExpensesInfo.COL_5, description);

        long result=db.insert(ExpensesContract.NewExpensesInfo.TABLE_NAME,null,contentValues);
        Log.e("Database Creation","one row is inserted");

     }


    public Cursor getAllData(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={ExpensesContract.NewExpensesInfo.COL_2, ExpensesContract.NewExpensesInfo.COL_3,
                ExpensesContract.NewExpensesInfo.COL_4, ExpensesContract.NewExpensesInfo.COL_5};
        cursor=db.query(ExpensesContract.NewExpensesInfo.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }


}
