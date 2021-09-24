package com.app.aarna.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.aarna.R;
import com.app.aarna.activity.order.AddSingleDayOrderActivity;
import com.app.aarna.activity.order.SelectDeliveryBoyActivity;
import com.app.aarna.adapter.OrderedListAdapter;
import com.app.aarna.dialog.AlertDialogFragment;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.orderlist.OrderListData;
import com.app.aarna.model.orderlist.OrderlistResponce;
import com.app.aarna.restapi.ApiCall;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity implements IRecyclerClickListener, IApiCallback, MyInterface {
    @BindView(R.id.view_pending)
    View view_pending;
    @BindView(R.id.view_delivery)
    View view_delivery;
    @BindView(R.id.product_recycler)
    RecyclerView product_recycler;
    OrderedListAdapter orderedListAdapter;
    ArrayList<OrderListData>orderListData=new ArrayList<>();
    String type="";
    @BindView(R.id.rl_delivery)
    RelativeLayout rl_delivery;
    String order_type="1";
    String cus_id="";
    String cus_name="";
    String cus_number="";
    String pro_id="";
    String deleviry_boy_id="";
    String selected_pos="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        view_delivery.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        type= intent.getStringExtra("type");
        cus_id=intent.getStringExtra("id");
        cus_name=intent.getStringExtra("name");
        cus_number=intent.getStringExtra("number");
        setcategoryadapter();
        get_order_list();

    }


    public void get_order_list() {
        if (type.equals("1")){
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).customer_order_list(cus_id, this);
        }else {
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).customer_multi_order_list(cus_id, this);
        }
    }

    public void setcategoryadapter() {
        product_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false ));
        orderedListAdapter = new OrderedListAdapter(this,orderListData,order_type,this);
        product_recycler.setAdapter(orderedListAdapter);
    }


    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }

    @Override
    public void clickListener(Object pos, Object data, Object extraData) {
        if (data.equals("edit")){
            int position= (int) pos;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<OrderListData>>() {}.getType();
            String json = gson.toJson(orderListData, type);
            Intent intent = new Intent(OrderListActivity.this, AddSingleDayOrderActivity.class);
            intent.putExtra("mylist",json);
            intent.putExtra("order_type","edit");
            intent.putExtra("position",String.valueOf(position));
            startActivity(intent);

        }else if (data.equals("delete")){
            int position= (int) pos;
            selected_pos= String.valueOf(position);
            pro_id=orderListData.get(position).getId();
            deleviry_boy_id=orderListData.get(position).getDeliveryBoyId();
            FragmentManager fm = getSupportFragmentManager();
            AlertDialogFragment editNameDialogFragment = AlertDialogFragment.newInstance();
            editNameDialogFragment.show(fm, "fragment_edit_name");
        }else if(data.equals("change_delivery_boy")){
            int position= (int) pos;
            selected_pos= String.valueOf(position);
            pro_id=orderListData.get(position).getId();
            deleviry_boy_id=orderListData.get(position).getDeliveryBoyId();
            Intent intent = new Intent(OrderListActivity.this, SelectDeliveryBoyActivity.class);
            intent.putExtra("id", pro_id);
            intent.putExtra("order_types", type);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,1);

        }
    }

    @OnClick(R.id.rl_pending)
    void getpendind(){
        order_type="1";
        view_pending.setVisibility(View.VISIBLE);
        view_delivery.setVisibility(View.INVISIBLE);
        setcategoryadapter();
    }

    @OnClick(R.id.rl_delivery)
    void getdelivery(){
        order_type="2";
        view_pending.setVisibility(View.INVISIBLE);
        view_delivery.setVisibility(View.VISIBLE);
        setcategoryadapter();
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
                        orderedListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }else if (type.equals("singleDay_assigndeliveryBoy_changeStatus")){
            Response<OrderlistResponce> response = (Response<OrderlistResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    orderListData.remove(Integer.parseInt(selected_pos));
                    orderedListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        get_order_list();
    }

    @Override
    public void oncheck(String data, String type, String id, String price, String image) {
        if (data.equals("yes")) {
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).singleDay_assigndeliveryBoy_changeStatus(pro_id, deleviry_boy_id,"2",this);
        }
    }
}