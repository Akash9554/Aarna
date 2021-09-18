package com.app.aarna.restapi;


import android.content.Context;

import com.app.aarna.helper.IApiCallback;
import com.app.aarna.model.CustomerResponce;
import com.app.aarna.model.DeliveryBoyResponce;
import com.app.aarna.model.LoginResponce;
import com.app.aarna.model.ProductDataResponce;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.model.ProductTypeDataList;
import com.app.aarna.model.deliverylist.DeliveryBoyOrderResponce;
import com.app.aarna.model.orderlist.OrderlistResponce;
import com.app.aarna.model.singledayorder.Single_Day_Order_Place_Responce;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {

    private static APIService service;

    public static ApiCall getInstance(Context context) {
        if (service == null) {
            service = RestClient.getClient();
        }
        return new ApiCall();
    }

    public void UserLogin( String email,String password, final IApiCallback iApiCallback) {
        Call<LoginResponce> call = service.login( email,password);
        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {
                iApiCallback.onSuccess("manual_login", response, null);
            }

            @Override
            public void onFailure(Call<LoginResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void add_transaction_image(MultipartBody.Part image, RequestBody name, RequestBody id, final IApiCallback iApiCallback) {
        Call<ProductTypeData> call = service.add_transaction_image(image,name,id);

        call.enqueue(new Callback<ProductTypeData>() {
            @Override
            public void onResponse(Call<ProductTypeData> call, Response<ProductTypeData> response) {
                iApiCallback.onSuccess("add_transaction_image",response,null);
            }

            @Override
            public void onFailure(Call<ProductTypeData> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void producttypelist( String user_id, final IApiCallback iApiCallback){
        Call<ProductTypeData> call = service.getproducttypelist(user_id);
        call.enqueue(new Callback<ProductTypeData>() {
            @Override
            public void onResponse(Call<ProductTypeData> call, Response<ProductTypeData> response) {
                iApiCallback.onSuccess("producttypelist",response,null);
            }

            @Override
            public void onFailure(Call<ProductTypeData> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void product_list( String user_id, final IApiCallback iApiCallback){
        Call<ProductDataResponce> call = service.getproductlist(user_id);
        call.enqueue(new Callback<ProductDataResponce>() {
            @Override
            public void onResponse(Call<ProductDataResponce> call, Response<ProductDataResponce> response) {
                iApiCallback.onSuccess("productlist",response,null);
            }

            @Override
            public void onFailure(Call<ProductDataResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void product_add_edit(String user_id,String id,String name,String image, String product_type_id,String description,String qty, final IApiCallback iApiCallback){
        Call<ProductTypeData> call = service.getproductaddedit(user_id,id,name,image,product_type_id,description,qty);
        call.enqueue(new Callback<ProductTypeData>() {
            @Override
            public void onResponse(Call<ProductTypeData> call, Response<ProductTypeData> response) {
                iApiCallback.onSuccess("product_add_edit",response,null);
            }

            @Override
            public void onFailure(Call<ProductTypeData> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void deleteproducttype( String user_id,String id, final IApiCallback iApiCallback){
        Call<ProductTypeData> call = service.getproducttypedelete(user_id,id);
        call.enqueue(new Callback<ProductTypeData>() {
            @Override
            public void onResponse(Call<ProductTypeData> call, Response<ProductTypeData> response) {
                iApiCallback.onSuccess("deleteproducttype",response,null);
            }

            @Override
            public void onFailure(Call<ProductTypeData> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void deliveryBoyData( String user_id,String type, final IApiCallback iApiCallback){
        Call<DeliveryBoyResponce> call = service.deliveryBoyData(user_id,type);
        call.enqueue(new Callback<DeliveryBoyResponce>() {
            @Override
            public void onResponse(Call<DeliveryBoyResponce> call, Response<DeliveryBoyResponce> response) {
                iApiCallback.onSuccess("deliveryBoyData",response,null);
            }

            @Override
            public void onFailure(Call<DeliveryBoyResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void adddeliveryboydata(MultipartBody.Part image, RequestBody name, RequestBody email, RequestBody password, RequestBody phone, RequestBody address, RequestBody type, RequestBody id, final IApiCallback iApiCallback) {
        Call<DeliveryBoyResponce> call = service.user_add_edit(image,name,email,password,phone,address,type,id);

        call.enqueue(new Callback<DeliveryBoyResponce>() {
            @Override
            public void onResponse(Call<DeliveryBoyResponce> call, Response<DeliveryBoyResponce> response) {
                iApiCallback.onSuccess("deliveryboydata",response,null);
            }

            @Override
            public void onFailure(Call<DeliveryBoyResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }


    public void deletedeliveryboy( String user_id, final IApiCallback iApiCallback){
        Call<DeliveryBoyResponce> call = service.deletedeliveryboy(user_id);
        call.enqueue(new Callback<DeliveryBoyResponce>() {
            @Override
            public void onResponse(Call<DeliveryBoyResponce> call, Response<DeliveryBoyResponce> response) {
                iApiCallback.onSuccess("deletedeliveryboy",response,null);
            }

            @Override
            public void onFailure(Call<DeliveryBoyResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void deleteproduct(String product_id, String user_id, final IApiCallback iApiCallback){
        Call<ProductDataResponce> call = service.delete_product(product_id,user_id);
        call.enqueue(new Callback<ProductDataResponce>() {
            @Override
            public void onResponse(Call<ProductDataResponce> call, Response<ProductDataResponce> response) {
                iApiCallback.onSuccess("delete_product",response,null);
            }

            @Override
            public void onFailure(Call<ProductDataResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void customerData( String user_id,String type, final IApiCallback iApiCallback){
        Call<CustomerResponce> call = service.CustomerData(user_id,type);
        call.enqueue(new Callback<CustomerResponce>() {
            @Override
            public void onResponse(Call<CustomerResponce> call, Response<CustomerResponce> response) {
                iApiCallback.onSuccess("customerdata",response,null);
            }

            @Override
            public void onFailure(Call<CustomerResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void deleteCustomer( String user_id, final IApiCallback iApiCallback){
        Call<CustomerResponce> call = service.deletecustomer(user_id);
        call.enqueue(new Callback<CustomerResponce>() {
            @Override
            public void onResponse(Call<CustomerResponce> call, Response<CustomerResponce> response) {
                iApiCallback.onSuccess("deletecustomer",response,null);
            }

            @Override
            public void onFailure(Call<CustomerResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void addcustomerdata(MultipartBody.Part image, RequestBody name, RequestBody phone, RequestBody address, RequestBody type, RequestBody id, final IApiCallback iApiCallback) {
        Call<CustomerResponce> call = service.customer_add_edit(image,name,phone,address,type,id);

        call.enqueue(new Callback<CustomerResponce>() {
            @Override
            public void onResponse(Call<CustomerResponce> call, Response<CustomerResponce> response) {
                iApiCallback.onSuccess("customerdata",response,null);
            }

            @Override
            public void onFailure(Call<CustomerResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void singleDay_place_order( String customer_id,String grand_total,String order_date,String product, final IApiCallback iApiCallback){
        Call<Single_Day_Order_Place_Responce> call = service.singleDay_place_order(customer_id,grand_total,order_date,product);
        call.enqueue(new Callback<Single_Day_Order_Place_Responce>() {
            @Override
            public void onResponse(Call<Single_Day_Order_Place_Responce> call, Response<Single_Day_Order_Place_Responce> response) {
                iApiCallback.onSuccess("singleDay_place_order",response,null);
            }

            @Override
            public void onFailure(Call<Single_Day_Order_Place_Responce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void customer_pay( String customer_id,String amount, final IApiCallback iApiCallback){
        Call<Single_Day_Order_Place_Responce> call = service.customer_pay(customer_id,amount);
        call.enqueue(new Callback<Single_Day_Order_Place_Responce>() {
            @Override
            public void onResponse(Call<Single_Day_Order_Place_Responce> call, Response<Single_Day_Order_Place_Responce> response) {
                iApiCallback.onSuccess("customer_pay",response,null);
            }

            @Override
            public void onFailure(Call<Single_Day_Order_Place_Responce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }


    public void singleDay_assigndeliveryBoy_changeStatus( String order_id,String delivery_boy_id,String status, final IApiCallback iApiCallback){
        Call<DeliveryBoyResponce> call = service.singleDay_assigndeliveryBoy_changeStatus(order_id,delivery_boy_id,status);
        call.enqueue(new Callback<DeliveryBoyResponce>() {
            @Override
            public void onResponse(Call<DeliveryBoyResponce> call, Response<DeliveryBoyResponce> response) {
                iApiCallback.onSuccess("singleDay_assigndeliveryBoy_changeStatus",response,null);
            }

            @Override
            public void onFailure(Call<DeliveryBoyResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }


    public void multiDay_place_order( String customer_id,String grand_total,String order_date,String product, final IApiCallback iApiCallback){
        Call<Single_Day_Order_Place_Responce> call = service.multiDay_place_order(customer_id,grand_total,order_date,product);
        call.enqueue(new Callback<Single_Day_Order_Place_Responce>() {
            @Override
            public void onResponse(Call<Single_Day_Order_Place_Responce> call, Response<Single_Day_Order_Place_Responce> response) {
                iApiCallback.onSuccess("singleDay_place_order",response,null);
            }

            @Override
            public void onFailure(Call<Single_Day_Order_Place_Responce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void multiDay_assigndeliveryBoy_changeStatus( String order_id,String delivery_boy_id,String status, final IApiCallback iApiCallback){
        Call<DeliveryBoyResponce> call = service.multiDay_assigndeliveryBoy_changeStatus(order_id,delivery_boy_id,status);
        call.enqueue(new Callback<DeliveryBoyResponce>() {
            @Override
            public void onResponse(Call<DeliveryBoyResponce> call, Response<DeliveryBoyResponce> response) {
                iApiCallback.onSuccess("singleDay_assigndeliveryBoy_changeStatus",response,null);
            }

            @Override
            public void onFailure(Call<DeliveryBoyResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void customer_multi_order_list( String customer_id, final IApiCallback iApiCallback){
        Call<OrderlistResponce> call = service.getOrderlist_customer(customer_id);
        call.enqueue(new Callback<OrderlistResponce>() {
            @Override
            public void onResponse(Call<OrderlistResponce> call, Response<OrderlistResponce> response) {
                iApiCallback.onSuccess("orderlist",response,null);
            }

            @Override
            public void onFailure(Call<OrderlistResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void customer_order_list( String customer_id, final IApiCallback iApiCallback){
        Call<OrderlistResponce> call = service.getOrderlist(customer_id);
        call.enqueue(new Callback<OrderlistResponce>() {
            @Override
            public void onResponse(Call<OrderlistResponce> call, Response<OrderlistResponce> response) {
                iApiCallback.onSuccess("orderlist",response,null);
            }

            @Override
            public void onFailure(Call<OrderlistResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void deliveryboy_order_list( String customer_id, final IApiCallback iApiCallback){
        Call<DeliveryBoyOrderResponce> call = service.getdeliveryboyorderlist(customer_id);
        call.enqueue(new Callback<DeliveryBoyOrderResponce>() {
            @Override
            public void onResponse(Call<DeliveryBoyOrderResponce> call, Response<DeliveryBoyOrderResponce> response) {
                iApiCallback.onSuccess("deliveryorderlist",response,null);
            }

            @Override
            public void onFailure(Call<DeliveryBoyOrderResponce> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }














}





