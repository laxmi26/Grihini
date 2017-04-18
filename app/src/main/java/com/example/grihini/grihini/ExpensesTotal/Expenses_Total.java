package com.example.grihini.grihini.ExpensesTotal;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grihini.grihini.R;

public class Expenses_Total extends AppCompatActivity {

    private ListView mListView;
    private ExpensesDbAdapter1 mDbAdapter;
    private ExpensesSimpleCursorAdapter1 mCursorAdapter;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses__total);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.drawable.mom1);
        mListView = (ListView) findViewById(R.id.expenses_list_view);
        txt=(TextView) findViewById(R.id.text_total);
        // mListView.setDivider(null);
        mDbAdapter = new ExpensesDbAdapter1(this);
        mDbAdapter.open();
        if (savedInstanceState == null) {
            //Clear all data
//           mDbAdapter.deleteAllReminders();
//////            //Add some data
            // insertSomeReminders();
        }


        //mDbAdapter.sum();


        Cursor cursor = mDbAdapter.fetchAllExpenses();
        //from columns defined in the db
        String[] from = new String[]{
                ExpensesDbAdapter1.COL_1,
                ExpensesDbAdapter1.COL_2,
                ExpensesDbAdapter1.COL_3,
                ExpensesDbAdapter1.COL_4,
        };
        //to the ids of views in the layout
        int[] to = new int[]{R.id.row_date,R.id.row_category,R.id.row_amount,R.id.row_desc};

        mCursorAdapter = new ExpensesSimpleCursorAdapter1(
                //context
                Expenses_Total.this,
                //the layout of the row
                R.layout.expenses_row1,
                //cursor
                cursor,
                //from columns defined in the db
                from,
                //to the ids of views in the layout
                to,
                //flag - not used
                0);
        //the cursorAdapter (controller) is now updating the listView (view)
        //with data from the db(model)
        mListView.setAdapter(mCursorAdapter);




        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Expenses_Total.this);
                ListView modeListView = new ListView(Expenses_Total.this);
                String[] modes = new String[]{"Delete Expenses"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(Expenses_Total.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //edit reminder

                        mDbAdapter.deleteExpensesById(getIdFromPosition(masterListPosition));
                        mCursorAdapter.changeCursor(mDbAdapter.fetchAllExpenses());

                        dialog.dismiss();
                    }
                });
                return true;
            }
        });




    }



    private int getIdFromPosition(int nC) {
        return (int) mCursorAdapter.getItemId(nC);
    }

    public void findtotal(View view) {
        int total1= mDbAdapter.getTotal();
        String s = ""+total1;
        txt.setText(s);

    }
}
