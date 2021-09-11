package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.app.aarna.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.iv_bottom)
    ImageView iv_bottom;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation_top= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        iv_top.setAnimation(animation_top);


        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein_animation);
        iv_logo.startAnimation(fadeInAnimation);

        Animation animation_bottom= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        iv_bottom.setAnimation(animation_bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class).setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                ));
                finish();
            }
        },3000);

    }
}