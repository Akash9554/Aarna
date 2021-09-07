package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.aarna.R;
import com.app.aarna.adapter.AddDelevieryBoyListAdapter;
import com.app.aarna.dialog.AlertDialogFragment;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.DeliveryBoyData;
import com.app.aarna.model.DeliveryBoyResponce;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.restapi.ApiCall;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class DeliveryManListActivity extends AppCompatActivity implements IApiCallback, IRecyclerClickListener , MyInterface {
    @BindView(R.id.product_recycler)
    RecyclerView recyclerView;
    AddDelevieryBoyListAdapter adapter;
    ArrayList<DeliveryBoyData> deliveryBoyData=new ArrayList<>();
    String type="2";
    String pro_id="";
    @BindView(R.id.tv_hint)
    TextView tv_hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_man_list);
        ButterKnife.bind(this);
        setcategoryadapter();
        get_product_list();
    }

    public void setcategoryadapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false ));
        adapter = new AddDelevieryBoyListAdapter(this, deliveryBoyData,this);
        recyclerView.setAdapter(adapter);
    }

    public void get_product_list(){
        FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
        ApiCall.getInstance(this).deliveryBoyData(  "1",type,this);
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("deliveryBoyData")) {
            Response<DeliveryBoyResponce> response = (Response<DeliveryBoyResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    if (!response.body().getData().isEmpty()){
                        tv_hint.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        deliveryBoyData.clear();
                        deliveryBoyData.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }else{
                        tv_hint.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        deliveryBoyData.clear();
                        deliveryBoyData.addAll(response.body().getData());
                    }
                }
            }
        }else if (type.equals("deletedeliveryboy")){
            Response<DeliveryBoyResponce> response = (Response<DeliveryBoyResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    get_product_list();
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                get_product_list();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }


    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }

    @Override
    public void clickListener(Object pos, Object data, Object extraData) {
        if (data.equals("edit")){
            int position= (int) pos;
            String name=deliveryBoyData.get(position).getName();
            String image=deliveryBoyData.get(position).getImage();
            String id=deliveryBoyData.get(position).getId();
            String number=deliveryBoyData.get(position).getPhone();
            String email=deliveryBoyData.get(position).getEmail();
            String address=deliveryBoyData.get(position).getAddress();
            Intent intent = new Intent(DeliveryManListActivity.this, AddDeliveryBoyActivity.class);
            intent.putExtra("type", "2");
            intent.putExtra("id", id);
            intent.putExtra("div_name", name);
            intent.putExtra("div_image", image);
            intent.putExtra("div_id", id);
            intent.putExtra("div_number", number);
            intent.putExtra("div_email", email);
            intent.putExtra("div_address", address);
            startActivityForResult(intent, 1);
        }else if (data.equals("delete")){
            int position= (int) pos;
            pro_id=deliveryBoyData.get(position).getId();
            FragmentManager fm = getSupportFragmentManager();
            AlertDialogFragment editNameDialogFragment = AlertDialogFragment.newInstance();
            editNameDialogFragment.show(fm, "fragment_edit_name");
        }

    }

    @OnClick(R.id.iv_add_product)
    void getadd(){
        Intent intent = new Intent(DeliveryManListActivity.this, AddDeliveryBoyActivity.class);
        intent.putExtra("type", "1");
        startActivityForResult(intent, 1);

    }

    @Override
    public void oncheck(String data, String type, String id) {
        if (data.equals("yes")) {
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).deletedeliveryboy(pro_id, this);
        }
    }

}