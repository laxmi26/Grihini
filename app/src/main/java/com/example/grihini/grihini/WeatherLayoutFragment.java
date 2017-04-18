package com.example.grihini.grihini;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherLayoutFragment extends Fragment {
    Handler handler;

    public WeatherLayoutFragment(){
        handler = new Handler();
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView=inflater.inflate(R.layout.weather_layout, container, false);
        Button btn=(Button)myView.findViewById(R.id.btnweather);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i=new Intent(getActivity(), WeatherActivity.class);
                startActivity(i);
            }
        });
        return myView;
    }


}