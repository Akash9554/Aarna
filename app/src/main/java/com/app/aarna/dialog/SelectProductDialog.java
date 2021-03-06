package com.app.aarna.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.app.aarna.R;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.singledayorder.SelectedProductByOwner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class



SelectProductDialog extends DialogFragment implements MyInterface   {
    static Context context;
    MyInterface mListener;
    @BindView(R.id.cv_product_image)
    CardView cv_product_image;
    @BindView(R.id.iv_product)
    ImageView iv_product;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.iv_minus)
    ImageView iv_minus;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.iv_plus)
    ImageView iv_plus;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.tv_employe)
    TextView tv_employe;
    String product_type_id="";
    String product_name="";
    String product_image="";
    String product_qty="1";
    String product_price="";
    String product_grand_price="";
    String enterd_price="";
    static SelectedProductByOwner selectedProductByOwner;
    static String enterType="";


    public static SelectProductDialog newInstance(Context conte,String enter_type, SelectedProductByOwner selectedProduct) {
        SelectProductDialog productListDialog = new SelectProductDialog();
        selectedProductByOwner=selectedProduct;
        enterType=enter_type;
        context=conte;
        return productListDialog;
    }

    //Creating view og dialog
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        View view = inflater.inflate(R.layout.selectproductdialoglayout, container, false);
        ButterKnife.bind(this, view);
        if (enterType.equals("edit")){
            tv_employe.setText("Save Change");
            cv_product_image.setVisibility(View.VISIBLE);
            tv_product_name.setVisibility(View.VISIBLE);
            Glide.with(context).load(selectedProductByOwner.getPro_image()).apply(new RequestOptions()).centerCrop().placeholder(R.drawable.place_holder).into(iv_product);
            tv_product_name.setText(selectedProductByOwner.getPro_name());
            tv_number.setText(selectedProductByOwner.getPro_qty());
            et_price.setText(selectedProductByOwner.getPro_total());
            product_name=selectedProductByOwner.getPro_name();
            product_type_id=selectedProductByOwner.getPro_id();
            product_image="";
            product_qty=selectedProductByOwner.getPro_qty();
            product_price=selectedProductByOwner.getPro_total();
        }else{
            tv_employe.setText("Add Product");
            tv_number.setText("1");
        }

        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                product_price=s.toString();
                tv_number.setText("1");

            }
        });
        return view;
    }
    @OnClick(R.id.iv_plus)
    void getplus(){
        if (TextUtils.isEmpty(et_price.getText().toString())){
            Toast.makeText(getContext(), "please enter product price", Toast.LENGTH_SHORT).show();
        }else {
            int actual_price=Integer.parseInt(et_price.getText().toString())/Integer.parseInt(tv_number.getText().toString());
            int num = Integer.parseInt(tv_number.getText().toString());
            num = num + 1;
            product_qty= String.valueOf(num);
            int price = Integer.parseInt(et_price.getText().toString());
            int grand_price=actual_price * num;
            et_price.setText(String.valueOf(grand_price));
            tv_number.setText(String.valueOf(num));
        }
    }

    @OnClick(R.id.iv_minus)
    void getminus(){
        int num= Integer.parseInt(tv_number.getText().toString());
        if (!(num<=1)) {
            int actual_price=Integer.parseInt(et_price.getText().toString())/Integer.parseInt(tv_number.getText().toString());
            num = num - 1;
            product_qty= String.valueOf(num);
            tv_number.setText(String.valueOf(num));
            int price = Integer.parseInt(et_price.getText().toString());
            int grand_price = actual_price * num;
            et_price.setText(String.valueOf(grand_price));
            tv_number.setText(String.valueOf(num));
        }
    }

    @OnClick(R.id.iv_back)
    void okClicked() {
        dismiss();

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

    @OnClick(R.id.rl_save)
    void getsave(){
        if (TextUtils.isEmpty(et_price.getText().toString())){
            Toast.makeText(getContext(), "please enter product price", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(product_name)) {
            Toast.makeText(getContext(), "please enter select product", Toast.LENGTH_SHORT).show();
        }else {
            mListener.oncheck(product_type_id, product_name, product_image, product_qty, product_price);
            dismiss();
        }
    }

    @OnClick(R.id.rl_select_product)
    void getproducttype(){
        FragmentManager fm = getChildFragmentManager();
        AllProductListDialog editNameDialogFragment = AllProductListDialog.newInstance(getContext(), "1");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void oncheck(String id, String name,String image,String img, String pr) {
        tv_product_name.setVisibility(View.VISIBLE);
        cv_product_image.setVisibility(View.VISIBLE);
        iv_product.setVisibility(View.VISIBLE);
        tv_product_name.setText(name);
        product_type_id=id;
        product_name=tv_product_name.getText().toString();
        product_image=image;
        Glide.with(getContext()).load(image).apply(new RequestOptions()).centerCrop().into(iv_product);
    }
}


