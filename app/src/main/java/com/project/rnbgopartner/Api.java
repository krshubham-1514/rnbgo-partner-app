package com.project.rnbgopartner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {



    @GET("register")

    Call<ResponseBody> create_user(

            @Query("partner_name") String partner_name,
            @Query("partner_mobile_number") String partner_mobile_number,
            @Query("password") String partner_password
    );



    @GET("register_room")

    Call<ResponseBody> register_room(

                    @Query("room_owner_name") String partner_name,
                    @Query("room_owner_contact_number") String partner_mobile_number
            );
}
