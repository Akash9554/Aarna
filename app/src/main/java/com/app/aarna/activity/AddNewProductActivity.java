package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.app.aarna.R;
import com.app.aarna.dialog.ProductListDialog;
import com.app.aarna.helper.MyInterface;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewProductActivity extends AppCompatActivity implements MyInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_login_btn)
    void getproducttype(){
        FragmentManager fm = getSupportFragmentManager();
        ProductListDialog editNameDialogFragment = ProductListDialog.newInstance(AddNewProductActivity.this,"1");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void oncheck(String data, String type) {

    }
}