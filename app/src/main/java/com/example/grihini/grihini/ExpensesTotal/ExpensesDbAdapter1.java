package com.example.grihini.grihini.ExpensesTotal;

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
public class ExpensesDbAdapter1 {

    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_1 = "date";
    public static final String COL_2="category";
    public static final String COL_3="amount";
    public static final String COL_4="desc";
    //public static final String COL_IMPORTANT = "important";
    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_DATE = INDEX_ID + 1;
    public static final int INDEX_CATEGORY = INDEX_ID + 2;
    public static final int INDEX_AMOUNT = INDEX_ID + 3;
    public static final int INDEX_DESC = INDEX_ID + 4;
    //used for logging
    private static final String TAG = "RemindersDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdrs";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " + COL_1+ " TEXT, " +
                    COL_2 + " TEXT, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " TEXT );";
    public ExpensesDbAdapter1(Context ctx) {
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
    public void createExpenses(String date, String category, String amount, String desc) {
        ContentValues values = new ContentValues();
        values.put(COL_1, date);
        values.put(COL_2, category);
        values.put(COL_3, amount);
        values.put(COL_4, desc);
        //values.put(COL_IMPORTANT, important ? 1 : 0);
        mDb.insert(TABLE_NAME, null , values);
    }
    //overloaded to take a expenses1
    public long createExpenses(Expenses1 expenses1) {
        ContentValues values = new ContentValues();
        values.put(COL_1, expenses1.getDate());
        values.put(COL_2, expenses1.getCategory());
        values.put(COL_3, expenses1.getAmount());
        values.put(COL_4, expenses1.getDesc());


        // Contact Name// Contact Phone Number
        // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);
    }
    //READ
    public Expenses1 fetchExpensesById(int id) {

        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_1,COL_2,COL_3,COL_4 }, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        return new Expenses1(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_DATE),
                cursor.getString(INDEX_CATEGORY),
                cursor.getString(INDEX_AMOUNT),
                cursor.getString(INDEX_DESC)

        );
    }
    public Cursor fetchAllExpenses() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_1,COL_2,COL_3,COL_4},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //UPDATE
    public void updateExpenses(Expenses1 expenses1) {
        ContentValues values = new ContentValues();
        values.put(COL_1, expenses1.getDate());
        values.put(COL_2, expenses1.getCategory());
        values.put(COL_3, expenses1.getAmount());
        values.put(COL_4, expenses1.getDesc());

        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(expenses1.getId())});
    }

    public Cursor rawQuery(String string, Object object) {
        // TODO Auto-generated method stub
        return null;
    }


//    public int getTotal() {
//
//        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_HITS };
//        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
//                null, null);
//        int total = 0;
//
//        int iHits = c.getColumnIndex(KEY_HITS);
//
//        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
//            total = total + iHits;
//        }
//        return total;
//    }




    public int getTotal() {

        String[] columns = new String[] { COL_ID, COL_1, COL_2,COL_3,COL_4 };
        Cursor c = mDb.query(TABLE_NAME, columns, null, null, null,
                null, null);
        int total = 0;

        int iHits = c.getColumnIndex(COL_3);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            total= total + c.getInt(iHits);
        }
        return total;
    }


//


    //DELETE
    public void deleteExpensesById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }
//    public void deleteAllReminders() {
//        mDb.delete(TABLE_NAME, null, null);
//    }

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
