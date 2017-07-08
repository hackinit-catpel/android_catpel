package com.muxistudio.catpel.Other;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.muxistudio.catpel.R;


/**
 * Created by kolibreath on 17-7-8.
 */

public class SplashActivity extends AppCompatActivity{

    Handler handler = new Handler();
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.claw);
        TranslateAnimation translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(SplashActivity.this
                        ,R.anim.claw_move);
        imageView.startAnimation(translateAnimation);
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Log.d("hanlder", "run: ");
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        },3000);
    }
}
