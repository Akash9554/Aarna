package com.app.aarna.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.app.aarna.R;
import com.app.aarna.helper.MyInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChooseOrderTypeDialogFragment extends DialogFragment {
    @BindView(R.id.tv_multi_day)
    TextView tv_multi_day;
    @BindView(R.id.tv_single_day)
    TextView tv_single_day;
    static String msg;
    static String type;
    @BindView(R.id.ivAlert)
    ImageView ivAlert;
    private MyInterface mListener;


    public static ChooseOrderTypeDialogFragment newInstance() {
        ChooseOrderTypeDialogFragment alertDialog = new ChooseOrderTypeDialogFragment();
        return alertDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        getDialog().setCancelable(false);
        View view = inflater.inflate(R.layout.chooseordertypedialoglayout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.tv_multi_day)
    void okClickedYes(){
        mListener.oncheck("multiday","","","","");
        dismiss();
    }

    @OnClick(R.id.tv_single_day)
    void okClickedNo(){
        mListener.oncheck("singleday","","","","");
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
}
