package com.app.aarna.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.aarna.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.cv_product)
    void get_all_Product(){
        getActivityCall(ProductListActivity.class);
    }
    @OnClick(R.id.cv_order_list)
    void get_all_Order(){
        getActivityCall(OrderListActivity.class);
    }

    @OnClick(R.id.cv_customer_list)
    void get_all_Customer_list(){
        getActivityCall(CustomerListActivity.class);
    }

    @OnClick(R.id.cv_delivery_boy_list)
    void get_all_Delivery_boy(){
        getActivityCall(DeliveryManListActivity.class);
    }

    @OnClick(R.id.cv_profile)
    void get_all_Profile(){
        getActivityCall(ProfileDetailActivity.class);
    }

    @OnClick(R.id.cv_add_new_product)
    void get_add_product(){
        getActivityCall(ProductTypeActivity.class);
    }

    @OnClick(R.id.cv_bill)
    void get_all_Bill(){
        getActivityCall(RecordListActivity.class);
    }

    public void getActivityCall(Class activity_call){
        Intent intent= new Intent(HomeActivity.this,activity_call);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}