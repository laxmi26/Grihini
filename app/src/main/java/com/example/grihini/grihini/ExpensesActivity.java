package com.example.grihini.grihini;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grihini.grihini.ExpensesTotal.Expenses_Total;
import com.example.grihini.grihini.ExpensesTotal.Expenses_input;


public class ExpensesActivity extends AppCompatActivity {

    private ListView mListView;
    private ExpensesDbAdapter mDbAdapter;
    private ExpensesSimpleCursorAdapter mCursorAdapter;

    public void addExpenses(View view){
        Intent intent=new Intent(this,Expenses_input.class);
        startActivity(intent);

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);


       ActionBar actionBar = getSupportActionBar();
         actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mListView = (ListView) findViewById(R.id.expenses_list_view);
        mListView.setDivider(null);
        mDbAdapter = new ExpensesDbAdapter(this);
        mDbAdapter.open();
        if (savedInstanceState == null) {
            //Clear all data
//           mDbAdapter.deleteAllReminders();
//////            //Add some data
         // insertSomeReminders();
        }


        Cursor cursor = mDbAdapter.fetchAllExpenses();
        //from columns defined in the db
        String[] from = new String[]{
                ExpensesDbAdapter.COL_CONTENT};
        //to the ids of views in the layout
        int[] to = new int[]{R.id.row_text};

        mCursorAdapter = new ExpensesSimpleCursorAdapter(
                //context
                ExpensesActivity.this,
                //the layout of the row
                R.layout.expenses_row,
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

        //when we longclick an individual item in the listview
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpensesActivity.this);
                ListView modeListView = new ListView(ExpensesActivity.this);
                String[] modes = new String[]{"Edit Expenses", "Delete Expenses"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(ExpensesActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //edit reminder
                        if (position == 0) {
                            int nId = getIdFromPosition(masterListPosition);
                            Expenses expenses = mDbAdapter.fetchExpensesById(nId);
                            fireCustomDialog(expenses);
                            //delete reminder
                        } else {
                            mDbAdapter.deleteExpensesById(getIdFromPosition(masterListPosition));
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllExpenses());
                        }
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });


       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int masterListPosition, long id) {

                 //Cursor cursor = (Cursor) mListView.getItemAtPosition(masterListPosition);
                TextView c = (TextView) view.findViewById(R.id.row_text);
                String playerChanged = c.getText().toString();

               Intent intent = new Intent(ExpensesActivity.this, Expenses_input.class);

                intent.putExtra("name",playerChanged);
                startActivity(intent);

            }
        });


    }


    private int getIdFromPosition(int nC) {
        return (int) mCursorAdapter.getItemId(nC);
    }

    private void insertSomeReminders() {
        mDbAdapter.createExpenses("Food");
       mDbAdapter.createExpenses("Clothing");
     mDbAdapter.createExpenses("HealthCare");
//      mDbAdapter.createReminder("String squash racket", false);
//      mDbAdapter.createReminder("Shovel and salt walkways", false);
////     mDbAdapter.createReminder("Call the Dalai Lama back", true);


    }

    private void fireCustomDialog(final Expenses expenses) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        TextView titleView = (TextView) dialog.findViewById(R.id.custom_title);
        final EditText editCustom = (EditText) dialog.findViewById(R.id.custom_edit_reminder);
        Button commitButton = (Button) dialog.findViewById(R.id.custom_button_commit);
        //final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.custom_check_box);
        LinearLayout rootLayout = (LinearLayout) dialog.findViewById(R.id.custom_root_layout);
        final boolean isEditOperation = (expenses != null);
        //this is for an edit
        if (isEditOperation) {
            titleView.setText("Edit Expenses");

            editCustom.setText(expenses.getContent());
            rootLayout.setBackgroundColor(getResources().getColor(R.color.green));
        }
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reminderText = editCustom.getText().toString();
                if (isEditOperation) {
                    Expenses expensesEdited = new Expenses(expenses.getId(),
                            reminderText);
                    mDbAdapter.updateExpenses(expensesEdited);
                    //this is for new reminder
                } else {
                    mDbAdapter.createExpenses(reminderText);
                }
                mCursorAdapter.changeCursor(mDbAdapter.fetchAllExpenses());
                dialog.dismiss();
            }
        });
        Button buttonCancel = (Button) dialog.findViewById(R.id.custom_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expenses, menu);
        return true;
    }


    public void viewExpenses(View view){
        Intent intent=new Intent(this,Expenses_Total.class);
        startActivity(intent);

    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                //create new Reminder
                fireCustomDialog(null);
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;
        }
    }
}
