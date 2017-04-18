package com.example.grihini.grihini;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dell on 6/4/2016.
 */
public class ExpensesFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    View myView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.expenses_layout, container, false);

        FloatingActionButton fb= (FloatingActionButton) myView.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ExpensesActivity.class);
                startActivity(i);
            }
        });
        return myView;
    }

}