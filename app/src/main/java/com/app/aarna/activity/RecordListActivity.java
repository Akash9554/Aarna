package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.aarna.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }
}
