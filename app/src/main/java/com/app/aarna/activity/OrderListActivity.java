package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.aarna.R;
import com.app.aarna.adapter.AddCustomerListAdapter;
import com.app.aarna.adapter.OrderedListAdapter;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.model.ProductDataResponce;
import com.app.aarna.model.orderlist.OrderListData;
import com.app.aarna.model.orderlist.OrderlistResponce;
import com.app.aarna.restapi.ApiCall;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity implements IRecyclerClickListener, IApiCallback {
    @BindView(R.id.view_pending)
    View view_pending;
    @BindView(R.id.view_delivery)
    View view_delivery;
    @BindView(R.id.product_recycler)
    RecyclerView product_recycler;
    OrderedListAdapter orderedListAdapter;
    String customer_id="19";
    ArrayList<OrderListData>orderListData=new ArrayList<>();
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        type= intent.getStringExtra("type");
        setcategoryadapter();
        get_order_list();

    }


    public void get_order_list() {
        if (type.equals("1")){
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).customer_order_list(customer_id, this);
        }else {
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).customer_multi_order_list(customer_id, this);
        }
    }

    public void setcategoryadapter() {
        product_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false ){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        orderedListAdapter = new OrderedListAdapter(this,orderListData,this);
        product_recycler.setAdapter(orderedListAdapter);
    }


    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }

    @OnClick(R.id.rl_pending)
    void getclick(){

    }

    @Override
    public void clickListener(Object pos, Object data, Object extraData) {

    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("orderlist")) {
            Response<OrderlistResponce> response = (Response<OrderlistResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    if (!response.body().getData().isEmpty()) {
                        orderListData.clear();
                        orderListData.addAll(response.body().getData());
                        orderedListAdapter.notifyDataSetChanged();;
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
    }
}