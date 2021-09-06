package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aarna.R;
import com.app.aarna.dialog.ProductListDialog;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.restapi.ApiCall;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class AddNewProductActivity extends AppCompatActivity implements MyInterface, IApiCallback {
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.iv_product)
    ImageView iv_product;
    @BindView(R.id.cv_product)
    CardView cv_product;
    @BindView(R.id.et_product_description)
    EditText et_product_description;
    @BindView(R.id.et_product_qty)
    EditText et_product_qty;
    @BindView(R.id.tv_employe)
    TextView tv_employe;
    String product_name="";
    String product_image="";
    String product_id="";
    String product_type_id="";
    String type="";
    @BindView(R.id.tv_employee)
    TextView tv_title;
    String description="";
    String qty="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product2);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        if (type.equals("2")){
            tv_employe.setText("Save Change");
            tv_title.setText("Change Product Type");
            tv_product_name.setVisibility(View.VISIBLE);
            cv_product.setVisibility(View.VISIBLE);
            iv_product.setVisibility(View.VISIBLE);
            product_id=intent.getStringExtra("id");
            description=intent.getStringExtra("description");
            qty=intent.getStringExtra("qty");
            product_type_id=intent.getStringExtra("product_type_id");
            product_image=intent.getStringExtra("product_image");
            product_name=intent.getStringExtra("product_name");
            et_product_description.setText(description);
            et_product_qty.setText(qty);
            tv_product_name.setText(product_name);
            Glide.with(this).load(product_image).apply(new RequestOptions()).centerCrop().into(iv_product);
            et_product_description.setSelection(et_product_description. getText(). length());
            et_product_qty.setSelection(et_product_qty. getText(). length());
        }else {
            tv_employe.setText("Save");
            tv_title.setText("Select Product Type");
        }

    }

    @OnClick(R.id.rl_login_btn)
    void getproducttype(){
        FragmentManager fm = getSupportFragmentManager();
        ProductListDialog editNameDialogFragment = ProductListDialog.newInstance(AddNewProductActivity.this,"1");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @OnClick(R.id.rl_save)
    void get_save(){
        if (TextUtils.isEmpty(tv_product_name.getText().toString())){
            setToast_message("Please Select Product Type");
        }else if (TextUtils.isEmpty(et_product_description.getText().toString())){
            setToast_message("Please Enter About Product");
        }else if (TextUtils.isEmpty(et_product_qty.getText().toString())){
            setToast_message("Please Enter Product Quantity");
        }else {
            FunctionHelper.disable_user_Intration(AddNewProductActivity.this, getString(R.string.loading), getSupportFragmentManager());
            ApiCall.getInstance(AddNewProductActivity.this).product_add_edit( "1",product_id,product_name,product_image,product_type_id,et_product_description.getText().toString(),et_product_qty.getText().toString(), this);
        }
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("product_add_edit")) {
            Response<ProductTypeData> response = (Response<ProductTypeData>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    Toast.makeText(this, "" + response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
    }

    @Override
    public void oncheck(String name, String image,String id) {
        tv_product_name.setVisibility(View.VISIBLE);
        cv_product.setVisibility(View.VISIBLE);
        iv_product.setVisibility(View.VISIBLE);
        tv_product_name.setText(name);
        product_type_id=id;
        product_name=tv_product_name.getText().toString();
        product_image=image;
        Glide.with(this).load(image).apply(new RequestOptions()).centerCrop().into(iv_product);
    }
    public void setToast_message(String message){
        Toast.makeText(AddNewProductActivity.this,message,Toast.LENGTH_SHORT).show();
    }

}