package com.app.aarna.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.aarna.R;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.model.LoginResponce;
import com.app.aarna.restapi.ApiCall;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class LoginAdminActivity extends AppCompatActivity implements IApiCallback {
    @BindView(R.id.iv_eye_icon)
    ImageView iv_eye_icon;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_username)
    EditText et_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_forget)
    void getadmin(){
        Intent intent= new Intent(LoginAdminActivity.this,ForgetPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.rl_login_btn)
    void getlogin(){
        if (et_username.getText().toString().equals("")){
            Toast.makeText(LoginAdminActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
        }else if (et_password.getText().toString().equals("")){
            Toast.makeText(LoginAdminActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }else {
            FunctionHelper.disable_user_Intration(this,"", getSupportFragmentManager());

            ApiCall.getInstance(this).UserLogin(et_username.getText().toString(), et_password.getText().toString(), this);

        }

    }

    @OnClick(R.id.iv_eye_icon)
    void getshow(View view){
        ShowHidePass(view);
    }

    public void ShowHidePass(View view) {
        if (et_password.getText().equals("")) {
            Toast.makeText(LoginAdminActivity.this,"Please enter password first",Toast.LENGTH_SHORT).show();
        } else {

            if (et_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                iv_eye_icon.setImageResource(R.drawable.eye_close);
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                iv_eye_icon.setImageResource(R.drawable.open_eye);
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
            et_password.setSelection(et_password.getText().length());
        }
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        Response<LoginResponce> response = (Response<LoginResponce>) data;
        if (response.isSuccessful()) {
            if (!response.body().getData().isEmpty()) {
                if(response.body().getErrorCode().equals("0")){
                Toast.makeText(this,response.body().getErrorMsg(),Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent= new Intent(LoginAdminActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, 2000);
            }else {
                    Toast.makeText(this,response.body().getErrorMsg(),Toast.LENGTH_SHORT).show();
                }
            }else {

            }
        }



    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
        //Toast.makeText(this,response.message(),Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }
}