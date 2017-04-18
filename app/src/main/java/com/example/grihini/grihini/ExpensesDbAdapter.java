package com.example.grihini.grihini;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ag on 5/1/2015.
 */
public class ExpensesDbAdapter {

    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";

    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;

    //used for logging
    private static final String TAG = "ExpensesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "EXPENSES";
    private static final String TABLE_NAME = "Categories";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_CONTENT + " TEXT );";
    public ExpensesDbAdapter(Context ctx)
    {
        this.mCtx = ctx;
    }
    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }
    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //CREATE
    //note that the id will be created for you automatically
    public void createExpenses(String name) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, name);
        mDb.insert(TABLE_NAME, null, values);
    }
    //overloaded to take a expenses
    public long createExpenses(Expenses expenses) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, expenses.getContent()); // Contact Name// Contact Phone Number
        // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);
    }
    //READ
    public Expenses fetchExpensesById(int id) {

        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_CONTENT }, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        return new Expenses(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_CONTENT)
        );
    }
    public Cursor fetchAllExpenses() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_CONTENT},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //UPDATE
    public void updateExpenses(Expenses expenses) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, expenses.getContent());

        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(expenses.getId())});
    }
    //DELETE
    public void deleteExpensesById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }
    public void deleteAllExpenses() {
        mDb.delete(TABLE_NAME, null, null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
