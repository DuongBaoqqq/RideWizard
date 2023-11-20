package com.example.ridewizard.ui.home.menu.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.profile.ProfileResponse;
import com.example.ridewizard.model.profile.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView userName;
    EditText email;
    EditText phone;
    Spinner gender;
    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);
        String token = "Bearer " + sharedPreferences.getString("accessToken","");
        UserDAO.getInstance().getProfileById(token,userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(response.isSuccessful()){
                    User data = response.body().getData().getUser();
                    userName.setText(data.getFullName());
                    email.setText(data.getEmail());
                    phone.setText(data.getPhNo());
                }
                else {
                    Log.d("access token", "onResponse: " + response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("access token", "onFailure: " + t.getMessage());
            }
        });


    }
}