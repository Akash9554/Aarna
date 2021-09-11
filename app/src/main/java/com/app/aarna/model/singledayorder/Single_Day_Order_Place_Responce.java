package com.app.aarna.model.singledayorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Single_Day_Order_Place_Responce {
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("errorMsg")
    @Expose
    private String errorMsg;
    @SerializedName("data")
    @Expose
    private ArrayList<Single_Day_Order_Place_Data> data = null;

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

    public ArrayList<Single_Day_Order_Place_Data> getData() {
        return data;
    }

    public void setData(ArrayList<Single_Day_Order_Place_Data> data) {
        this.data = data;
    }
}
