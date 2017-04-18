package com.example.grihini.grihini;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.grihini.grihini.Shopping.ShoppingActivity;

/**
 * Created by dell on 6/4/2016.
 */
public class ShoppinglistFragment extends Fragment {
    Animation Fade_in, Fade_out;
    ViewFlipper viewFlipper;





    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View myView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.shoppinglist_layout, container, false);

        viewFlipper=(ViewFlipper) this.myView.findViewById(R.id.bckgrndViewFlipper1);

        Fade_in= AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        Fade_out=AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
        viewFlipper.setInAnimation(Fade_in);
        viewFlipper.setOutAnimation(Fade_out);

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();

        FloatingActionButton fb= (FloatingActionButton) myView.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShoppingActivity.class);
                startActivity(i);
            }
        });
        return myView;
    }

}