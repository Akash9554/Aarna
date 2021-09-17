package com.app.aarna.activity.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aarna.R;
import com.app.aarna.activity.CustomerListActivity;
import com.app.aarna.adapter.AddDelevieryBoyListAdapter;
import com.app.aarna.adapter.SelectDelevieryBoyListAdapter;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.DeliveryBoyData;
import com.app.aarna.model.DeliveryBoyResponce;
import com.app.aarna.restapi.ApiCall;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class SelectDeliveryBoyActivity extends AppCompatActivity implements IRecyclerClickListener, IApiCallback {
    public static String placeId;
    @BindView(R.id.product_recycler)
    RecyclerView product_recycler;
    private MyInterface mListener;
    SelectDelevieryBoyListAdapter adapter;
    ArrayList<DeliveryBoyData> deliveryBoyData=new ArrayList<>();
    @BindView(R.id.tv_title)
    TextView tv_title;
    String type_text="notmain";
    String delivery_boy_id="";
    String order_types="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_boy);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        order_types=intent.getStringExtra("order_types");
        placeId=intent.getStringExtra("id");
        setcategoryadapter();
        getproductlist();
    }

    public void setcategoryadapter() {
        product_recycler.setLayoutManager(new LinearLayoutManager(SelectDeliveryBoyActivity.this,RecyclerView.VERTICAL,false));
        adapter = new SelectDelevieryBoyListAdapter(SelectDeliveryBoyActivity.this,deliveryBoyData,type_text,this);
        product_recycler.setAdapter(adapter);
    }

    public void getproductlist(){
        FunctionHelper.disable_user_Intration(SelectDeliveryBoyActivity.this, getString(R.string.loading), getSupportFragmentManager());
        ApiCall.getInstance(SelectDeliveryBoyActivity.this).deliveryBoyData( "1", "2",this);
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("deliveryBoyData")) {
            Response<DeliveryBoyResponce> response = (Response<DeliveryBoyResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    if (!response.body().getData().isEmpty()){
                        deliveryBoyData.clear();
                        deliveryBoyData.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        }else if (type.equals("singleDay_assigndeliveryBoy_changeStatus")){
            Response<DeliveryBoyResponce> response = (Response<DeliveryBoyResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(this, CustomerListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);

    }

    @OnClick(R.id.tv_single_day)
    public void getselected(){
        FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
        if (order_types.equals("1")){
            ApiCall.getInstance(this).singleDay_assigndeliveryBoy_changeStatus( placeId,delivery_boy_id,"0",this);
        }else {
            ApiCall.getInstance(this).multiDay_assigndeliveryBoy_changeStatus( placeId,delivery_boy_id,"0",this);
        }
    }

    @Override
    public void clickListener(Object pos, Object data, Object extraData) {
        if (data.equals("selected_boy")){
            int position= (int) pos;
            delivery_boy_id=deliveryBoyData.get(position).getId();
            for (int i=0;i<deliveryBoyData.size();i++){
                if (i==position){
                    DeliveryBoyData deliveryBoy=deliveryBoyData.get(i);
                    deliveryBoy.setChecked(true);
                    deliveryBoyData.set(i,deliveryBoy);
                }else {
                    DeliveryBoyData deliveryBoy=deliveryBoyData.get(i);
                    deliveryBoy.setChecked(false);
                    deliveryBoyData.set(i,deliveryBoy);
                }
            }
            adapter.notifyDataSetChanged();
        }

    }



}