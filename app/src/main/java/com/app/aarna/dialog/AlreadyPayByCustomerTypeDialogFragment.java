package com.app.aarna.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.app.aarna.R;
import com.app.aarna.activity.order.SelectDeliveryBoyActivity;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.singledayorder.Single_Day_Order_Place_Responce;
import com.app.aarna.restapi.ApiCall;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;


public class AlreadyPayByCustomerTypeDialogFragment extends DialogFragment implements IApiCallback {
    @BindView(R.id.et_amount)
    EditText et_amount;
    static String id;
     static Context contexts;
    private MyInterface mListener;
    String amount="";
    static String order_types;


    public AlreadyPayByCustomerTypeDialogFragment newInstance(Context context, String order_id, String order) {
        AlreadyPayByCustomerTypeDialogFragment alertDialog = new AlreadyPayByCustomerTypeDialogFragment();
        contexts=context;
        id=order_id;
        order_types=order;
        return alertDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        getDialog().setCancelable(false);
        View view = inflater.inflate(R.layout.alreadypayedbycustomertypedialoglayout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @OnClick(R.id.tv_single_day)
    void okClickedNo() {
        if (TextUtils.isEmpty(et_amount.getText().toString())) {
            Toast.makeText(contexts, "" + "Please enter amount", Toast.LENGTH_SHORT).show();
        } else {
            amount=et_amount.getText().toString();
            FunctionHelper.disable_user_Intration(contexts, getString(R.string.loading), getChildFragmentManager());
            ApiCall.getInstance(contexts).customer_pay(id, amount, this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(contexts);
        if (type.equals("customer_pay")){
            Response<Single_Day_Order_Place_Responce> response = (Response<Single_Day_Order_Place_Responce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    Toast.makeText(contexts, "" + "Order placed successfully", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(contexts, SelectDeliveryBoyActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("order_types",order_types);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    dismiss();
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(contexts);
    }

}
