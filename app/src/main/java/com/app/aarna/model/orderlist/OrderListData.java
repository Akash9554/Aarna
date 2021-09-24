package com.app.aarna.model.orderlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderListData implements Parcelable {
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
    private CustomerDetailOrder customerDetail;
    @SerializedName("delivery_boy_detail")
    @Expose
    private DeliveryBoyDetailOrder deliveryBoyDetail;
    @SerializedName("product_list")
    @Expose
    private ArrayList<ProductOrder> productList = null;

    public OrderListData(Parcel in) {
        id = in.readString();
        customerId = in.readString();
        deliveryBoyId = in.readString();
        orderDate = in.readString();
        orderType = in.readString();
        grandTotal = in.readString();
        createdAt = in.readString();
        status = in.readString();
    }
    public OrderListData(){}

    public static final Creator<OrderListData> CREATOR = new Creator<OrderListData>() {
        @Override
        public OrderListData createFromParcel(Parcel in) {
            return new OrderListData(in);
        }

        @Override
        public OrderListData[] newArray(int size) {
            return new OrderListData[size];
        }
    };

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

    public CustomerDetailOrder getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetailOrder customerDetail) {
        this.customerDetail = customerDetail;
    }

    public DeliveryBoyDetailOrder getDeliveryBoyDetail() {
        return deliveryBoyDetail;
    }

    public void setDeliveryBoyDetail(DeliveryBoyDetailOrder deliveryBoyDetail) {
        this.deliveryBoyDetail = deliveryBoyDetail;
    }

    public ArrayList<ProductOrder> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductOrder> productList) {
        this.productList = productList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(customerId);
        parcel.writeString(deliveryBoyId);
        parcel.writeString(orderDate);
        parcel.writeString(orderType);
        parcel.writeString(grandTotal);
        parcel.writeString(createdAt);
        parcel.writeString(status);
    }
}
