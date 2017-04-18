package com.example.grihini.grihini.Shopping;

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

import com.example.grihini.grihini.ExpensesTotal.Expenses_input;
import com.example.grihini.grihini.R;
import com.example.grihini.grihini.Reminders.ReminderAddActivity;


public class ShoppingActivity extends AppCompatActivity {


    Bundle bundle;
    private ListView mListView;
    private ShoppingDbAdapter mDbAdapter;
    private ShoppingSimpleCursorAdapter mCursorAdapter;



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);


       ActionBar actionBar = getSupportActionBar();
         actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mListView = (ListView) findViewById(R.id.expenses_list_view);
        mListView.setDivider(null);
        mDbAdapter = new ShoppingDbAdapter(this);
        mDbAdapter.open();
        if (savedInstanceState == null) {

        }


        Cursor cursor = mDbAdapter.fetchAllShopping();
        //from columns defined in the db
        String[] from = new String[]{
                ShoppingDbAdapter.COL_CONTENT};
        //to the ids of views in the layout
        int[] to = new int[]{R.id.row_text};

        mCursorAdapter = new ShoppingSimpleCursorAdapter(
                //context
                ShoppingActivity.this,
                //the layout of the row
                R.layout.shopping_row,
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingActivity.this);
                ListView modeListView = new ListView(ShoppingActivity.this);
                String[] modes = new String[]{"Edit Shopping", "Delete Shopping", "Add item to Expenses", "Add item to Reminder"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(ShoppingActivity.this,
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
                            Shopping shopping = mDbAdapter.fetchShoppingById(nId);
                            fireCustomDialog(shopping);
                            //delete reminder
                        }

                        else if(position==1){
                            mDbAdapter.deleteShoppingById(getIdFromPosition(masterListPosition));
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllShopping());
                        }

                        else if(position==2) {
                            Intent appInfo = new Intent(getApplicationContext(), Expenses_input.class);
                            startActivity(appInfo);
                        }

                        else if(position==3){

                            Intent appInfo = new Intent(getApplicationContext(), ReminderAddActivity.class);
                            startActivity(appInfo);

                        }

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

    private void insertSomeShopping() {
    }

    private void fireCustomDialog(final Shopping shopping) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_shopping);
        TextView titleView = (TextView) dialog.findViewById(R.id.custom_title);
        final EditText editCustom = (EditText) dialog.findViewById(R.id.custom_edit_reminder);
        Button commitButton = (Button) dialog.findViewById(R.id.custom_button_commit);
        //final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.custom_check_box);
        LinearLayout rootLayout = (LinearLayout) dialog.findViewById(R.id.custom_root_layout);
        final boolean isEditOperation = (shopping != null);
        //this is for an edit
        if (isEditOperation) {
            titleView.setText("Edit Shopping");

            editCustom.setText(shopping.getContent());
            rootLayout.setBackgroundColor(getResources().getColor(R.color.green));
        }
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shoppingText = editCustom.getText().toString();
                if (isEditOperation) {
                    Shopping shoppingEdited = new Shopping(shopping.getId(),
                            shoppingText);
                    mDbAdapter.updateShopping(shoppingEdited);
                    //this is for new reminder
                } else {
                    mDbAdapter.createShopping(shoppingText);
                }
                mCursorAdapter.changeCursor(mDbAdapter.fetchAllShopping());
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
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        return true;
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
