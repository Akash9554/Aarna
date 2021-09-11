package com.app.aarna.model.singledayorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Single_Day_Order_Place_Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("delivery_boy_id")
    @Expose
    private String deliveryBoyId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("customer_detail")
    @Expose
    private SingleDayOrderCustomerDetail customerDetail;
    @SerializedName("delivery_boy_detail")
    @Expose
    private SingleDayOrderDeliveryBoyDetailDetail deliveryBoyDetail;
    @SerializedName("product_list")
    @Expose
    private ArrayList<SingleDayOrderProduct> productList = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryBoyId() {
        return deliveryBoyId;
    }

    public void setDeliveryBoyId(String deliveryBoyId) {
        this.deliveryBoyId = deliveryBoyId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SingleDayOrderCustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(SingleDayOrderCustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public SingleDayOrderDeliveryBoyDetailDetail getDeliveryBoyDetail() {
        return deliveryBoyDetail;
    }

    public void setDeliveryBoyDetail(SingleDayOrderDeliveryBoyDetailDetail deliveryBoyDetail) {
        this.deliveryBoyDetail = deliveryBoyDetail;
    }

    public ArrayList<SingleDayOrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<SingleDayOrderProduct> productList) {
        this.productList = productList;
    }
}
