package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aarna.R;
import com.app.aarna.adapter.AddCustomerListAdapter;
import com.app.aarna.dialog.AlertDialogFragment;
import com.app.aarna.dialog.ChooseOrderTypeDialogFragment;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.CustomerData;
import com.app.aarna.model.CustomerResponce;
import com.app.aarna.restapi.ApiCall;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class CustomerListActivity extends AppCompatActivity implements IRecyclerClickListener, IApiCallback, MyInterface {
    @BindView(R.id.tv_hint)
    TextView tv_hint;
    @BindView(R.id.customer_type_recycler)
    RecyclerView recyclerView;
    ArrayList<CustomerData> customerData=new ArrayList<>();
    String type="0";
    String pro_id="";
    AddCustomerListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        ButterKnife.bind(this);
        setcategoryadapter();
        get_product_list();
    }
    public void setcategoryadapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false ));
        adapter = new AddCustomerListAdapter(this, customerData,this);
        recyclerView.setAdapter(adapter);
    }

    public void get_product_list(){
        FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
        ApiCall.getInstance(this).customerData(  "1",type,this);
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("customerdata")) {
            Response<CustomerResponce> response = (Response<CustomerResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    if (!response.body().getData().isEmpty()){
                        tv_hint.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        customerData.clear();
                        customerData.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }else{
                        tv_hint.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        customerData.clear();
                        customerData.addAll(response.body().getData());
                    }
                }
            }
        }else if (type.equals("deletecustomer")){
            Response<CustomerResponce> response = (Response<CustomerResponce>) data;
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
            String name=customerData.get(position).getName();
            String image=customerData.get(position).getImage();
            String id=customerData.get(position).getId();
            String number=customerData.get(position).getPhone();
            String address=customerData.get(position).getAddress();
            Intent intent = new Intent(CustomerListActivity.this, AddNewCustomerActivity.class);
            intent.putExtra("type", "2");
            intent.putExtra("id", id);
            intent.putExtra("cus_name", name);
            intent.putExtra("cus_image", image);
            intent.putExtra("cus_id", id);
            intent.putExtra("cus_number", number);
            intent.putExtra("cus_address", address);
            startActivityForResult(intent, 1);
        }else if (data.equals("delete")){
            int position= (int) pos;
            pro_id=customerData.get(position).getId();
            FragmentManager fm = getSupportFragmentManager();
            AlertDialogFragment editNameDialogFragment = AlertDialogFragment.newInstance();
            editNameDialogFragment.show(fm, "fragment_edit_name");
        }else if(data.equals("order_type")){
            FragmentManager fm = getSupportFragmentManager();
            ChooseOrderTypeDialogFragment chooseOrderTypeDialogFragment = ChooseOrderTypeDialogFragment.newInstance();
            chooseOrderTypeDialogFragment.show(fm, "fragment_edit_name");
        }

    }

    @OnClick(R.id.iv_add_product)
    void getadd(){
        Intent intent = new Intent(CustomerListActivity.this, AddNewCustomerActivity.class);
        intent.putExtra("type", "1");
        startActivityForResult(intent, 1);
    }
    @Override
    public void oncheck(String data, String type, String id) {
        if (data.equals("yes")) {
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).deleteCustomer(pro_id, this);
        }else if (data.equals("singleday")){
            Toast.makeText(CustomerListActivity.this, "Single", Toast.LENGTH_SHORT).show();
        }else if(data.equals("multiday")){
            Toast.makeText(CustomerListActivity.this, "Multi", Toast.LENGTH_SHORT).show();

        }
    }
}