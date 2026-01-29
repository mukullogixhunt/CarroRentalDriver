package com.carro.chauffeur.api;

import com.carro.chauffeur.api.response.BookingListResponse;
import com.carro.chauffeur.api.response.BranchResponse;
import com.carro.chauffeur.api.response.CarBrandResponse;
import com.carro.chauffeur.api.response.CarListResponse;
import com.carro.chauffeur.api.response.CarNameResponse;
import com.carro.chauffeur.api.response.CarTypeResponse;
import com.carro.chauffeur.api.response.CityResponse;
import com.carro.chauffeur.api.response.DriverListResponse;
import com.carro.chauffeur.api.response.DriverOtpResponse;
import com.carro.chauffeur.api.response.HomePageResponse;
import com.carro.chauffeur.api.response.LoginResponse;
import com.carro.chauffeur.api.response.NotificationResponse;
import com.carro.chauffeur.api.response.RecommendedResponse;
import com.carro.chauffeur.api.response.SliderResponse;
import com.carro.chauffeur.api.response.StateResponse;
import com.carro.chauffeur.api.response.WalletCountResponse;
import com.carro.chauffeur.api.response.WalletTransResponse;
import com.carro.chauffeur.api.response.commonResponse.BaseResponse;
import com.carro.chauffeur.model.CheckBlockModel;
import com.carro.chauffeur.utils.Constant;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @FormUrlEncoded
    @POST(Constant.EndPoint.LOGIN)
    Call<LoginResponse> driverLogin(
            @Field(Constant.ApiKey.USER_MOBILE) String user_mobile
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.SEND_OTP)
    Call<BaseResponse> sendOtp(
            @Field(Constant.ApiKey.USER_MOBILE) String user_mobile
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.VERIFY_OTP)
    Call<LoginResponse> verifyOtp(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.OTP) String otp
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_CURRENT_LOC)
    Call<BaseResponse> updateCurrentLocation(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.LOC_LAT) String lat,
            @Field(Constant.ApiKey.LOC_LNG) String lng
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_ONLINE_OFFLINE_STATUS)
    Call<BaseResponse> onlineOffline(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.ONLINE_OFFLINE_STATUS) String status
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.USER_DETAILS)
    Call<LoginResponse> user_details(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.CAR)
    Call<CarListResponse> car(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.DRIVER)
    Call<DriverListResponse> driver(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @POST(Constant.EndPoint.BRAND)
    Call<CarBrandResponse> carBrand();

    @POST(Constant.EndPoint.CAR_TYPE)
    Call<CarTypeResponse> cartype();

    @FormUrlEncoded
    @POST(Constant.EndPoint.MODEL)
    Call<CarNameResponse> carModel(
            @Field(Constant.ApiKey.BRAND) String brand
    );

    @Multipart
    @POST(Constant.EndPoint.UPDATE_PROFILE)
    Call<LoginResponse> update_profile(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_NAME) RequestBody user_name,
            @Part(Constant.ApiKey.USER_EMAIL) RequestBody user_email,
            @Part(Constant.ApiKey.USER_ADDRESS) RequestBody user_address,
            @Part(Constant.ApiKey.USER_ADHAR_NO) RequestBody user_adhar_no,
            @Part(Constant.ApiKey.USER_PAN_NO) RequestBody user_pan_no,
            @Part(Constant.ApiKey.USER_PINCODE) RequestBody user_pincode,
            @Part(Constant.ApiKey.USER_ALT_MOBILE) RequestBody user_alt_mobile,
            @Part(Constant.ApiKey.USER_BRANCH) RequestBody user_branch,
            @Part(Constant.ApiKey.USER_STATE) RequestBody user_state,
            @Part(Constant.ApiKey.USER_CITY) RequestBody user_city,
            @Part MultipartBody.Part user_pic,
            @Part MultipartBody.Part adhar_front,
            @Part MultipartBody.Part adhar_back,
            @Part MultipartBody.Part pan_img
    );


    @Multipart
    @POST(Constant.EndPoint.UPDATE_BASIC_PROFILE)
    Call<LoginResponse> update_profile(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_NAME) RequestBody user_name,
//            @Part(Constant.ApiKey.USER_ALT_MOBILE) RequestBody user_alt_mobile,
            @Part MultipartBody.Part user_pic
    );


    @Multipart
    @POST(Constant.EndPoint.UPDATE_DOCUMENT)
    Call<LoginResponse> update_doc_details(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.LIC_EXPDATE) RequestBody lic_expdate,
            @Part MultipartBody.Part lic_img
    );

    @Multipart
    @POST(Constant.EndPoint.INSERT_CAR)
    Call<BaseResponse> insert_car(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part(Constant.ApiKey.CAR_STATUS) RequestBody car_status,
            @Part(Constant.ApiKey.CAR_TC) RequestBody car_tc,
            @Part(Constant.ApiKey.CAR_EXCLUSION) RequestBody car_exclusion,
            @Part(Constant.ApiKey.CAR_INCLUSION) RequestBody car_inclusion,
            @Part(Constant.ApiKey.CAR_BRANCH) RequestBody car_branch,
            @Part(Constant.ApiKey.CAR_TITLE) RequestBody car_name,
            @Part(Constant.ApiKey.CAR_DRIVER_TYPE) RequestBody car_drivetype,
            @Part(Constant.ApiKey.CAR_NUMBER) RequestBody car_number,
            @Part(Constant.ApiKey.CAR_FUEL) RequestBody car_fuel,
            @Part(Constant.ApiKey.CAR_SEATS) RequestBody car_seat,
            @Part(Constant.ApiKey.CAR_PRICE) RequestBody car_price,
            @Part(Constant.ApiKey.CAR_AC) RequestBody car_AC,
            @Part(Constant.ApiKey.CAR_LUGGAGE) RequestBody car_luggage,
            @Part(Constant.ApiKey.CAR_SERV_TYPE) RequestBody car_servtype,
            @Part MultipartBody.Part car_img

    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.CHECK_BLOCK_WITH_ID)
    Call<CheckBlockModel> check_block_with_id(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @Multipart
    @POST(Constant.EndPoint.INSERT_DRIVER)
    Call<BaseResponse> insert_driver(
            @Part(Constant.ApiKey.DRIVER_NAME) RequestBody driver_name,
            @Part(Constant.ApiKey.DRIVER_MOBILE) RequestBody driver_mobile,
            @Part(Constant.ApiKey.LIC_EXP_DATE) RequestBody lic_expdate,
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part MultipartBody.Part lic_img
    );

    @Multipart
    @POST(Constant.EndPoint.UPDATE_DRIVER)
    Call<BaseResponse> upd_driver(
            @Part(Constant.ApiKey.DRIVER_ID) RequestBody driver_id,
            @Part(Constant.ApiKey.DRIVER_NAME) RequestBody driver_name,
            @Part(Constant.ApiKey.DRIVER_MOBILE) RequestBody driver_mobile,
            @Part(Constant.ApiKey.LIC_EXP_DATE) RequestBody lic_expdate,
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part MultipartBody.Part lic_img
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.SEND_DRIVER_OTP)
    Call<DriverOtpResponse> send_driver_otp(
            @Field(Constant.ApiKey.DRIVER_MOBILE) String driver_mobile
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.VERIFY_DRIVER_OTP)
    Call<BaseResponse> verify_driver_otp(
            @Field(Constant.ApiKey.DRIVER_ID) String driver_id,
            @Field(Constant.ApiKey.OTP) String otp
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.NOTIFICATION)
    Call<NotificationResponse> notification(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_NOTIFY_BOOKING)
    Call<BaseResponse> update_notify_booking(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );

    @Multipart
    @POST(Constant.EndPoint.UPDATE_DRIVER)
    Call<BaseResponse> update_driver(
            @Part(Constant.ApiKey.DRIVER_ID) RequestBody driver_id,
            @Part MultipartBody.Part lic_img,
            @Part MultipartBody.Part driver_img,
            @Part(Constant.ApiKey.EXP_DATE) RequestBody exp_date
    );

    @Multipart
    @POST(Constant.EndPoint.UPD_REG_CERTI)
    Call<BaseResponse> upd_reg_certi(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part reg_certi
    );
    @Multipart
    @POST(Constant.EndPoint.UPD_ISS_CERTI)
    Call<BaseResponse> upd_iss_certi(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part iss_certi
    );
    @Multipart
    @POST(Constant.EndPoint.UPD_CAR_PERMIT)
    Call<BaseResponse> upd_car_permit(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part permit
    );
    @Multipart
    @POST(Constant.EndPoint.UPD_OWNER_CAR)
    Call<BaseResponse> upd_ownercar(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part ownercar_img
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.WALLET)
    Call<WalletTransResponse> wallet(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.COUNT_WALLET)
    Call<WalletCountResponse> countWallet(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.NEW_BOOKING)
    Call<BookingListResponse>new_booking(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.ACTIVE_BOOKING)
    Call<BookingListResponse>active_booking(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.BOOKING_HISTORY)
    Call<BookingListResponse>booking_history(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_CAR_DRIVER)
    Call<BaseResponse>update_car_driver(
            @Field(Constant.ApiKey.CAR_ID) String car_id,
            @Field(Constant.ApiKey.DRIVER_ID) String driver_id,
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_CANCEL_BOOKING)
    Call<BaseResponse>cancel_booking(
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_COMPLETE_BOOKING)
    Call<BaseResponse>complete_booking(
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );


    @FormUrlEncoded
    @POST(Constant.EndPoint.INSERT_WALLET)
    Call<BaseResponse>insert_wallet(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.AMOUNT) String amount
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_BANK_DETAILS)
    Call<BaseResponse>update_bank_details(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.BANK_HOLDER_NAME) String bank_holder_name,
            @Field(Constant.ApiKey.BANK_NAME) String bank_name,
            @Field(Constant.ApiKey.BANK_ACC_NO) String bank_acc_no,
            @Field(Constant.ApiKey.BANK_IFSC_CODE) String bank_ifsc_code,
            @Field(Constant.ApiKey.BANK_UPI) String bank_upi,
            @Field(Constant.ApiKey.BANK_PP) String bank_pp,
            @Field(Constant.ApiKey.BANK_GP) String bank_gp
    );

    @POST(Constant.EndPoint.HOME_PAGE)
    Call<HomePageResponse> home_page();



    @Multipart
    @POST(Constant.EndPoint.UPDATE_BUSINESS_DETAILS)
    Call<BaseResponse> update_business_details(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_BUSINESS_NAME) RequestBody user_business_name,
            @Part(Constant.ApiKey.USER_BUSINESS_ADDRESS) RequestBody user_business_address,
            @Part(Constant.ApiKey.USER_BUSINESS_EMAIL) RequestBody user_business_email,
            @Part(Constant.ApiKey.USER_BUSINESS_PHONE) RequestBody user_business_phone,
            @Part(Constant.ApiKey.USER_BUSINESS_LIC_NO) RequestBody user_business_lic_no,
            @Part MultipartBody.Part b_lic_img_front,
            @Part MultipartBody.Part b_lic_img_back
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.RECENT_PAID_WALLET)
    Call<RecommendedResponse>recent_paid_wallet(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.GET_COUNTRY_STATE)
    Call<StateResponse> get_country_state(
            @Field(Constant.ApiKey.COUNTRY_CODE) String country_code
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.GET_STATE_CITIES)
    Call<CityResponse> get_state_cities(
            @Field(Constant.ApiKey.COUNTRY_CODE) String country_code,
            @Field(Constant.ApiKey.STATE_CODE) String state_code
    );

    @POST(Constant.EndPoint.BRANCH)
    Call<BranchResponse> get_branch();

    @POST(Constant.EndPoint.SLIDER)
    Call<SliderResponse> get_slider();


    @Multipart
    @POST(Constant.EndPoint.UPDATE_CAR_DOCUMENTS)
    Call<BaseResponse> uploadCarDocuments(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part reg_certi,
            @Part MultipartBody.Part iss_certi,
            @Part MultipartBody.Part permit,
            @Part MultipartBody.Part ownercar_img
    );
}
