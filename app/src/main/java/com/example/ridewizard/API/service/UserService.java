package com.example.ridewizard.API.service;

import com.example.ridewizard.model.change_password.ChangePasswordResponse;
import com.example.ridewizard.model.profile.ProfileResponse;
import com.example.ridewizard.model.user.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @FormUrlEncoded
    @POST("/api/v1/users/signin")
    Call<UserResponse> login(@Field("username") String userName,
                             @Field("password") String password, @Field("type") String type);

    @GET("/api/v1/users/{id}")
    Call<ProfileResponse> getProfileById(@Header("Authorization") String token, @Path("id") int userId);

    @PATCH("/api/v1/users/change-password")
    Call<ChangePasswordResponse> changePassword(@Field("old_password") String oldPassword,
                                                @Field("new_password") String newPassword,
                                                @Field("re_new_password") String reNewPassword);
}
