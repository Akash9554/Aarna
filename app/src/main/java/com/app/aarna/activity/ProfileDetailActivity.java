package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.aarna.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }
}