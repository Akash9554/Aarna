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
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//Password Change Dialog
public class AlertDialogFragment extends DialogFragment {
    @BindView(R.id.tv_yes)
    TextView tv_yes;
    @BindView(R.id.tv_no)
    TextView tv_no;
    static String msg;
    static String type;
    @BindView(R.id.ivAlert)
    ImageView ivAlert;
    private MyInterface mListener;

    public static AlertDialogFragment newInstance() {
        AlertDialogFragment alertDialog = new AlertDialogFragment();
        return alertDialog;
    }


    //Creating view of dialog
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        getDialog().setCancelable(false);
        View view = inflater.inflate(R.layout.alertdialoglayout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.tv_yes)
    void okClickedYes(){
        mListener.oncheck("yes","","","","");
        dismiss();
    }

    @OnClick(R.id.tv_no)
    void okClickedNo(){
        mListener.oncheck("no","","","","");
        dismiss();
    }
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
}
