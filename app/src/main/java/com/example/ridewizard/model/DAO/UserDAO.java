package com.example.ridewizard.model.DAO;

import android.util.Log;

import com.example.ridewizard.API.APIClient;
import com.example.ridewizard.API.service.UserService;
import com.example.ridewizard.model.change_password.ChangePasswordResponse;
import com.example.ridewizard.model.profile.ProfileResponse;
import com.example.ridewizard.model.uploadImage.ProfileDriver;
import com.example.ridewizard.model.user.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserDAO {
    private static UserDAO instance;
    UserService service;
    private UserDAO() {
        this.service = APIClient.getInstance().getAPI().create(UserService.class);
    }
    public static UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }
    public Call<UserResponse> login(String userName, String password, String type){
        Log.d("loginnnnnn", "login: ");
        return service.login(userName,password,type);
    }
    public Call<ProfileResponse> getProfileById(String token,int id){
        return service.getProfileById(token,id);
    }
    public Call<ChangePasswordResponse> changePassword(String token,String oldPassword, String newPassword) {
        return service.changePassword(token,oldPassword, newPassword);
    }
    public Call<ResponseBody> uploadImage(int type, MultipartBody.Part image)
    {
        return service.uploadImages(type,image);
    }
    public Call<ProfileDriver> getProfileDriverId(String token,int id){
        return service.getProfileDriverById(token,id);
    }
}
