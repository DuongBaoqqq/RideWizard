package com.example.ridewizard.model.DAO;

import com.example.ridewizard.API.APIClient;
import com.example.ridewizard.API.service.UserService;
import com.example.ridewizard.model.change_password.ChangePasswordResponse;
import com.example.ridewizard.model.uploadavatar.AvatarResponse;
import com.example.ridewizard.model.user.UserResponse;
import com.example.ridewizard.model.verify.Verify;

import okhttp3.MultipartBody;
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
//        Log.d("loginnnnnn", "login: ");
        return service.login(userName,password,type);
    }

    public Call<UserResponse> signUp(String userName, String email, String phone, String password){
        return service.signUp(userName,email,phone,password);
    }

    public Call<UserResponse> getProfileById(String token,int id){
        return service.getProfileById(token,id);
    }

    public Call<Verify> verifyOTP(String token, String otp){
        return service.verifyOTP(token, otp);
    }
    public Call<Verify> requestVerifyOTP(String token){
        return service.requestVerifyOTP(token);
    }
    public Call<AvatarResponse> uploadAvatar(String token,
//                                             String content,
                                             MultipartBody.Part file) {
        return service.uploadAvatar(token,
//                content,
                file);
    }
    public Call<ChangePasswordResponse> changePassword(String token,String oldPassword, String newPassword) {
        return service.changePassword(token,oldPassword, newPassword);

    }
}
