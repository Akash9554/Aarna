package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.aarna.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_admin)
    void getadmin(){
        Intent intent= new Intent(LoginTypeActivity.this,LoginAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.rl_employee)
    void getemployee(){
        Intent intent= new Intent(LoginTypeActivity.this,LoginAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}