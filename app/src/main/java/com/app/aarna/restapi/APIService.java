package com.app.aarna.restapi;



import com.app.aarna.model.CustomerPaymentResponce;
import com.app.aarna.model.CustomerResponce;
import com.app.aarna.model.DeliveryBoyResponce;
import com.app.aarna.model.LoginResponce;
import com.app.aarna.model.ProductDataResponce;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.model.ProductTypeDataList;
import com.app.aarna.model.ProfileResponce;
import com.app.aarna.model.deliverylist.DeliveryBoyOrderResponce;
import com.app.aarna.model.orderlist.OrderlistResponce;
import com.app.aarna.model.singledayorder.Single_Day_Order_Place_Responce;


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
    @Multipart
    Call<ProductTypeData> getproductaddedit(@Part MultipartBody.Part image,
                                                @Part("user_id") RequestBody user_id,
                                                @Part("id") RequestBody id,
                                            @Part("name") RequestBody name,
                                            @Part("product_type_id") RequestBody product_type_id,
                                            @Part("description") RequestBody description,
                                            @Part("qty") RequestBody qty);

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

    @POST("product_delete")
    @FormUrlEncoded
    Call<ProductDataResponce> delete_product(
            @Field("product_id") String product_id,
            @Field("user_id") String user_id
    );

    @POST("user_list")
    @FormUrlEncoded
    Call<CustomerResponce> CustomerData(
            @Field("user_id") String user_id,
            @Field("type") String type
    );

    @POST("user_delete")
    @FormUrlEncoded
    Call<CustomerResponce> deletecustomer(
            @Field("user_id") String user_id
    );

    @POST("user_add_edit")
    @Multipart
    Call<CustomerResponce> customer_add_edit(@Part MultipartBody.Part image,
                                            @Part("name") RequestBody name,
                                            @Part("phone") RequestBody phone,
                                            @Part("address") RequestBody address,
                                            @Part("type") RequestBody type,
                                            @Part("id") RequestBody id);

    @POST("singleDay_place_order")
    @FormUrlEncoded
    Call<Single_Day_Order_Place_Responce> singleDay_place_order(
            @Field("customer_id") String customer_id,
            @Field("grand_total") String grand_total,
            @Field("order_date") String order_date,
            @Field("product") String product
    );

    @POST("customer_pay")
    @FormUrlEncoded
    Call<Single_Day_Order_Place_Responce> customer_pay(
            @Field("customer_id") String customer_id,
            @Field("amount") String amount
    );

    @POST("singleDay_assigndeliveryBoy_changeStatus")
    @FormUrlEncoded
    Call<DeliveryBoyResponce> singleDay_assigndeliveryBoy_changeStatus(
            @Field("order_id") String order_id,
            @Field("delivery_boy_id") String delivery_boy_id,
            @Field("status") String status
    );

    @POST("multiDay_place_order")
    @FormUrlEncoded
    Call<Single_Day_Order_Place_Responce> multiDay_place_order(
            @Field("customer_id") String customer_id,
            @Field("grand_total") String grand_total,
            @Field("order_date") String order_date,
            @Field("product") String product
    );

    @POST("multiDay_assigndeliveryBoy_changeStatus")
    @FormUrlEncoded
    Call<DeliveryBoyResponce> multiDay_assigndeliveryBoy_changeStatus(
            @Field("order_id") String order_id,
            @Field("delivery_boy_id") String delivery_boy_id,
            @Field("status") String status
    );


    @POST("customer_multiDay_order_set_list")
    @FormUrlEncoded
    Call<OrderlistResponce> getOrderlist_customer(
            @Field("customer_id") String user_id
    );

    @POST("customer_order_list")
    @FormUrlEncoded
    Call<OrderlistResponce> getOrderlist(
            @Field("customer_id") String user_id
    );

    @POST("deliveryboy_order_list")
    @FormUrlEncoded
    Call<DeliveryBoyOrderResponce> getdeliveryboyorderlist(
            @Field("delivery_boy_id") String user_id
    );

    @POST("get_profile")
    @FormUrlEncoded
    Call<ProfileResponce> getprofile(
            @Field("user_id") String user_id
    );


    @POST("customer_pay_list")
    @FormUrlEncoded
    Call<CustomerPaymentResponce> customer_pay_list(
            @Field("customer_id") String customer_id
    );









}

