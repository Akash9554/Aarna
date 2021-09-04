package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.aarna.R;
import com.app.aarna.adapter.AddProductListAdapter;

import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.model.ProductDataList;
import com.app.aarna.model.ProductDataResponce;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.restapi.ApiCall;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements IRecyclerClickListener, IApiCallback {
    AddProductListAdapter adapter;
    @BindView(R.id.product_recycler)
    RecyclerView recyclerView;
    ArrayList<ProductDataList> productDataLists =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        setcategoryadapter();
        get_product_list();
    }

    public void setcategoryadapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false ));
        adapter = new AddProductListAdapter(this, productDataLists,this);
        recyclerView.setAdapter(adapter);
    }

    public void get_product_list(){
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).product_list(  "1",this);
        }

    @OnClick(R.id.iv_add_product)
    void getadd(){
        Intent intent = new Intent(ProductListActivity.this, AddNewProductActivity.class);
        //  intent.putExtra("number1", number1);
        //   intent.putExtra("number2", number2);
        startActivityForResult(intent, 1);

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



    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("productlist")) {
            Response<ProductDataResponce> response = (Response<ProductDataResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    if (!response.body().getData().isEmpty()){
                        productDataLists.clear();
                        productDataLists.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {

    }



    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }

    @Override
    public void clickListener(Object pos, Object data, Object extraData) {

    }
}