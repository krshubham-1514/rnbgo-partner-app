package com.project.rnbgopartner;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {



    @GET("register")

    Call<ResponseBody> create_user(

            @Query("partner_name") String partner_name,
            @Query("partner_mobile_number") String partner_mobile_number,
            @Query("password") String partner_password
    );

    @GET("get_username")
    Call<PartnerInfo> get_username(
            @Query("partner_mobile_number") String partner_mobile_number
    );

    @GET("get_ip")
    Call<IpInfo> get_ipaddress(
            @Query("domain_name") String domain_name
    );


    @GET("get_listed_rooms")
    Call<List<RoomInfo>> get_listed_rooms
            (
                 @Query("partner_uid") String Partner_uid
            );



    @GET("register_room")

    Call<ResponseBody> register_room(

                    @Query("room_owner_name") String room_onwer_name,
                    @Query("room_owner_contact_number") String room_owner_mobile_number,
                    @Query("room_address") String room_address,
                    @Query("room_area") String room_area,
                    @Query("room_coordinates") String room_coordinates,
                    @Query("room_facility") String room_facility,
                    @Query("room_person_count") String room_person_count,
                    @Query("room_condition") String room_condition,
                    @Query("room_gender_type") String room_gender_type,
                    @Query("room_type") String room_type,
                    @Query("room_rent") String room_rent,
                    @Query("room_extra_charge") String extra_charge,
                    @Query("room_electric_bill") String electric_bill,
                    @Query("room_water_bill") String water_bill,
                    @Query("ready_checkin") String ready_checkin,
                    @Query("room_description") String room_description,
                    @Query("partner_uid") String partner_uid
            );


    @Multipart
    @POST("upload_images")
    Call<ResponseBody> uploadMultipleImages(

            @Part List<MultipartBody.Part> files
    );



}
