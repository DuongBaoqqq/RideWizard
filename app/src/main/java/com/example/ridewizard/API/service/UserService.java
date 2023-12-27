package com.example.ridewizard.API.service;

import com.example.ridewizard.model.change_password.ChangePasswordResponse;

import com.example.ridewizard.model.uploadImage.LoadImageResponse;
import com.example.ridewizard.model.uploadImage.ProfileDriver;

import com.example.ridewizard.model.uploadavatar.UploadAvatar;

import com.example.ridewizard.model.user.UserResponse;
import com.example.ridewizard.model.verify.Verify;
import com.google.gson.JsonObject;


import okhttp3.MultipartBody;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;

import retrofit2.http.POST;

import retrofit2.http.PUT;

import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @FormUrlEncoded
    @POST("/api/v1/users/signin")
    Call<UserResponse> login(@Field("username") String userName,
                             @Field("password") String password, @Field("type") String type);
    @FormUrlEncoded
    @POST("/api/v1/users/signup")
    Call<UserResponse> signUp(@Field("fullName") String userName, @Field("email") String email,
                              @Field("phNo") String phoneNB, @Field("password") String password);
    @FormUrlEncoded
    @POST("/api/v1/auth/otp/phone/verify")
    Call<Verify> verifyOTP(@Header("Authorization") String token, @Field("otp") String otp);
    @Headers("Content-Type: multipart/form-data")
    @Multipart
    @POST("/api/v1/users/upload/avatar")
    Call<UploadAvatar> uploadAvatar(@Header("Authorization") String token, @Part MultipartBody.Part image);

    @GET("/api/v1/auth/otp/email")
    Call<Verify> requestVerifyOTP(@Header("Authorization") String token);
    @GET("/api/v1/users/{id}")
    Call<UserResponse> getProfileById(@Header("Authorization") String token,@Path("id") int userId);
    @FormUrlEncoded
    @PUT("/api/v1/users/change-password")
    Call<ChangePasswordResponse> changePassword(
            @Header("Authorization") String token,
            @Field("currentPassword") String oldPassword,
            @Field("newPassword") String newPassword);
    @GET("/api/v1/drivers/drive")
    Call<JsonObject> checkDriver(
            @Header("Authorization") String token
    );
    @Multipart
    @PUT("/api/v1/drivers/identification/upload")
    Call<LoadImageResponse> uploadImages(
            @Header("Authorization") String token,
            @Query("type") int type,
            @Part MultipartBody.Part image
    );

    @GET("api/v1/drivers/identification/{user_id}")
    Call<ProfileDriver> getProfileDriverById(@Header("Authorization") String token,
                                             @Path("user_id") int userId);
}
