package com.app.aarna.model.singledayorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedProductByOwner {

    @SerializedName("product_id")
    @Expose
    String pro_id="";
    @SerializedName("pro_name")
    @Expose
    String pro_name="";
    @SerializedName("pro_image")
    @Expose
    String pro_image="";
    @SerializedName("qty")
    @Expose
    String pro_qty="";
    @SerializedName("price")
    @Expose
    String pro_price="";
    @SerializedName("total_price")
    @Expose
    String pro_total="";

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_image() {
        return pro_image;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }

    public String getPro_qty() {
        return pro_qty;
    }

    public void setPro_qty(String pro_qty) {
        this.pro_qty = pro_qty;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getPro_total() {
        return pro_total;
    }

    public void setPro_total(String pro_total) {
        this.pro_total = pro_total;
    }
}
