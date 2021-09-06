package com.app.aarna.restapi;



import com.app.aarna.model.DeliveryBoyResponce;
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

    @POST("product_add_edit")
    @FormUrlEncoded
    Call<ProductTypeData> getproductaddedit(
            @Field("user_id") String user_id,
            @Field("id") String id,
            @Field("name") String name,
            @Field("image") String image,
            @Field("product_type_id") String product_type_id,
            @Field("description") String description,
            @Field("qty") String qty
    );

    @POST("product_type_delete")
    @FormUrlEncoded
    Call<ProductTypeData> getproducttypedelete(
            @Field("user_id") String user_id,
            @Field("id") String id
    );

    @POST("user_list")
    @FormUrlEncoded
    Call<DeliveryBoyResponce> deliveryBoyData(
            @Field("user_id") String user_id,
            @Field("type") String type
    );

    @POST("user_add_edit")
    @Multipart
    Call<DeliveryBoyResponce> user_add_edit(@Part MultipartBody.Part image,
                                                @Part("name") RequestBody name,
                                                @Part("email") RequestBody email,
                                            @Part("password") RequestBody password,
                                            @Part("phone") RequestBody phone,
                                            @Part("address") RequestBody address,
                                            @Part("type") RequestBody type,
                                            @Part("id") RequestBody id);

    @POST("user_delete")
    @FormUrlEncoded
    Call<DeliveryBoyResponce> deletedeliveryboy(
            @Field("user_id") String user_id
    );

    @POST("user_delete")
    @FormUrlEncoded
    Call<ProductDataResponce> delete_product(
            @Field("product_id") String product_id,
            @Field("user_id") String user_id
    );


}
