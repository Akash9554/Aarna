package com.app.aarna.restapi;


import android.content.Context;

import com.app.aarna.helper.IApiCallback;
import com.app.aarna.model.LoginResponce;
import com.app.aarna.model.ProductDataResponce;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.model.ProductTypeDataList;

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

    public void product_add_edit(String user_id,String name,String image, String product_type_id,String description,String qty, final IApiCallback iApiCallback){
        Call<ProductTypeData> call = service.getproductaddedit(user_id,name,image,product_type_id,description,qty);
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


}




