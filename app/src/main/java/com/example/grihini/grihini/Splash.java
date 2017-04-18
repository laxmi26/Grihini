package com.example.grihini.grihini;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by dell on 6/4/2016.
 */
public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final ImageView iv=(ImageView) findViewById(R.id.imageView);
        final Animation an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.fade_in);
        //final Animation an2= AnimationUtils.loadAnimation(getBaseContext(),R.anim.fade_out);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener(){
        public void onAnimationStart (Animation animation)
        {

        }
        public void onAnimationEnd(Animation animation){
       // iv.startAnimation(an2);
            finish();
        Intent i=new Intent(Splash.this,MainActivity.class);
        Splash.this.startActivity(i);
    }
    public void onAnimationRepeat(Animation animation){

    }
});
        }
}
