package com.example.grihini.grihini.ExpensesTotal;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grihini.grihini.DatePickerFragment;
import com.example.grihini.grihini.ExpensesTotal.ExpensesDbAdapter1;
import com.example.grihini.grihini.ExpensesTotal.ExpensesSimpleCursorAdapter1;
import com.example.grihini.grihini.ExpensesTotal.Expenses_Total;
import com.example.grihini.grihini.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Expenses_input extends AppCompatActivity {

    EditText editDate;
    EditText editCategory;
    EditText editAmount, editDescription;
    Context context=this;
    SQLiteDatabase sqLiteDatabase;

    private ListView mListView;
    private ExpensesDbAdapter1 mDbAdapter;
    private ExpensesSimpleCursorAdapter1 mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_input);

        mDbAdapter = new ExpensesDbAdapter1(this);
        mDbAdapter.open();

        editDate = (EditText) findViewById(R.id.editText_date);
        editCategory= (EditText) findViewById(R.id.Text_category);
        editAmount = (EditText) findViewById(R.id.editText_amount);
        editDescription = (EditText) findViewById(R.id.editText_desc);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("name");
            //Toast.makeText(this,value,Toast.LENGTH_LONG).show();
            editCategory.setText(value);
        }



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }
    public boolean check(){

        String date= editDate.getText().toString();
        String category=editCategory.getText().toString();
        String amount=editAmount.getText().toString();
        String description=editDescription.getText().toString();
        if (date.matches("")) {
            //Toast.makeText(this, "You did not enter a date", Toast.LENGTH_SHORT).show();
            editDate.setError("Date is empty");
            editDate.requestFocus();
            return false;
        }
        else if (category.matches("")) {
            //Toast.makeText(this, "Category is Empty", Toast.LENGTH_SHORT).show();
            editCategory.setError("Category is empty");
            editCategory.requestFocus();
            return false;
        }
        else if (amount.matches("")) {
           // Toast.makeText(this, "Amount is empty", Toast.LENGTH_SHORT).show();
            editAmount.setError("Amount is empty");
            editAmount.requestFocus();
            return false;
        }
        else if (description.matches("")) {
            //Toast.makeText(this, "Date is empty", Toast.LENGTH_SHORT).show();
            editDescription.setError("Description is empty");
            editDescription.requestFocus();
            return false;
        }
        else
            return  true;
    }






    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }






    public void viewExpenses(View view){
        Intent intent=new Intent(this,Expenses_Total.class);
        startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addExpenses(View view){

        if(check()) {


            String date = editDate.getText().toString();
            String category = editCategory.getText().toString();
            String amount = editAmount.getText().toString();
            String description = editDescription.getText().toString();
//            mDbAdapter = new ExpensesDbAdapter1(context);
            mDbAdapter.createExpenses(date, category, amount, description);
            Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_LONG).show();
            mDbAdapter.close();
            hello();
        }

    }
    public void hello(){
        editDate.setText(" ");
        editCategory.setText(" ");
        editAmount.setText(" ");
        editDescription.setText(" ");
    }
}
