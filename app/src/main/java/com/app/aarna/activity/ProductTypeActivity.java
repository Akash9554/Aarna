package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.aarna.R;

import com.app.aarna.adapter.AddProductListTypeAdapter;
import com.app.aarna.helper.CheckConnection;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.model.ProductTypeDataList;
import com.app.aarna.restapi.ApiCall;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class ProductTypeActivity extends AppCompatActivity implements IRecyclerClickListener, IApiCallback {
    @BindView(R.id.product_type_recycler)
    RecyclerView product_type_recyclerView;
    AddProductListTypeAdapter adapter;
    String user_id="1";
    ArrayList<ProductTypeDataList>productTypeDataLists=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        ButterKnife.bind(this);
        setcategoryadapter();
        getproductlist();

    }

    public void setcategoryadapter() {
        product_type_recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new AddProductListTypeAdapter(this,productTypeDataLists, this);
        product_type_recyclerView.setAdapter(adapter);
    }

    public void getproductlist(){
        if (CheckConnection.isConnection(this)) {
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(this).producttypelist( user_id, this);
        }else {
            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }

    @OnClick(R.id.iv_add_product)
    void getadd(){
        Intent intent = new Intent(ProductTypeActivity.this, AddNewProductTypeActivity.class);
      //  intent.putExtra("number1", number1);
     //   intent.putExtra("number2", number2);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                getproductlist();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("producttypelist")) {
            Response<ProductTypeData> response = (Response<ProductTypeData>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    if (!response.body().getData().isEmpty()){
                        productTypeDataLists.clear();
                        productTypeDataLists.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {

    }


    @Override
    public void clickListener(Object pos, Object data, Object extraData) {

    }
}