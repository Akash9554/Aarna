package com.app.aarna.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.aarna.R;
import com.app.aarna.adapter.AddProductListTypeAdapter;
import com.app.aarna.helper.CheckConnection;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.model.ProductTypeDataList;
import com.app.aarna.restapi.ApiCall;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

//Check-in Dialog
public class ProductListDialog extends DialogFragment implements IApiCallback, IRecyclerClickListener {
    public static String placeId;
    @BindView(R.id.product_recycler)
    RecyclerView product_recycler;
    private MyInterface mListener;
    AddProductListTypeAdapter adapter;
    Context context;
    ArrayList<ProductTypeDataList> productTypeDataLists=new ArrayList<>();

    //Creating instance of dialog
    public static ProductListDialog newInstance(Context context, String id) {
        ProductListDialog productListDialog = new ProductListDialog();
        context=context;
        placeId = id;
        return productListDialog;
    }

    //Creating view og dialog
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        View view = inflater.inflate(R.layout.producttypelistdialog, container, false);
        ButterKnife.bind(this, view);
        setcategoryadapter();
        getproductlist();
        return view;
    }

    public void setcategoryadapter() {
        product_recycler.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new AddProductListTypeAdapter(context,productTypeDataLists, this);
        product_recycler.setAdapter(adapter);
    }

    public void getproductlist(){
        if (CheckConnection.isConnection(context)) {
            FunctionHelper.disable_user_Intration(context, getString(R.string.loading), getChildFragmentManager());
            ApiCall.getInstance(context).producttypelist( "1", this);
        }else {
            Toast.makeText(context, "please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }




    //OK button click
    @OnClick(R.id.iv_back)
    void okClicked() {
        mListener.oncheck("","");
        dismiss();

    }
    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(context);
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
    //API Responce Failure
    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(getContext());

    }

    //Attaching listiner to dialog
    @Override
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    //Detaching listener from dailog
    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void clickListener(Object pos, Object data, Object extraData) {

    }
}


