package com.app.aarna.model.orderlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderlistResponce {
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("errorMsg")
    @Expose
    private String errorMsg;
    @SerializedName("data")
    @Expose
    private ArrayList<OrderListData> data = null;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ArrayList<OrderListData> getData() {
        return data;
    }

    public void setData(ArrayList<OrderListData> data) {
        this.data = data;
    }
}
