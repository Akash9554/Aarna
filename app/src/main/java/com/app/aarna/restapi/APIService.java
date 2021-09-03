package com.app.aarna.restapi;



import com.app.aarna.model.LoginResponce;
import com.app.aarna.model.ProductDataResponce;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.model.ProductTypeDataList;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface APIService {

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponce> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("product_type_add_edit")
    @Multipart
    Call<ProductTypeData> add_transaction_image(@Part MultipartBody.Part image,
                                                @Part("name") RequestBody name,
                                                @Part("id") RequestBody id);

    @POST("product_type_list")
    @FormUrlEncoded
    Call<ProductTypeData> getproducttypelist(
            @Field("user_id") String user_id
    );

    @POST("product_list")
    @FormUrlEncoded
    Call<ProductDataResponce> getproductlist(
            @Field("user_id") String user_id
    );

}
