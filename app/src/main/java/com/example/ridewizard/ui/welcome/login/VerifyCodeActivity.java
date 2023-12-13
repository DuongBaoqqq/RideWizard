package com.example.ridewizard.ui.welcome.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import com.chaos.view.PinView;
import com.chaos.view.PinView;
import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.verify.Verify;
import com.example.ridewizard.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCodeActivity extends AppCompatActivity {
    PinView otp;
    AppCompatButton send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        otp = findViewById(R.id.pinview);
        send = findViewById(R.id.send);
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String token = "Bearer " + sharedPreferences.getString("accessToken","");

        UserDAO.getInstance().requestVerifyOTP(token).enqueue(new Callback<Verify>() {
            @Override
            public void onResponse(Call<Verify> call, Response<Verify> response) {
                if(response.isSuccessful()){
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            UserDAO.getInstance().verifyOTP(token,otp.getText().toString()).enqueue(new Callback<Verify>() {
                                @Override
                                public void onResponse(Call<Verify> call, Response<Verify> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().isSuccess()){
                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            startActivity(intent);
                                        }
                                        Log.d("verifyyyyy", "onClick: " + response );
                                    }else {
                                        Log.d("verifyyyyy", "onClick: " + response );

                                    }
                                }

                                @Override
                                public void onFailure(Call<Verify> call, Throwable t) {
                                    Log.d("verifyyyyy", "onClick: " + t.getMessage() );
                                }
                            });
                        }
                    });

                }
                else {
                }
            }

            @Override
            public void onFailure(Call<Verify> call, Throwable t) {
                Log.d("register", "onClick: " + t.getMessage() );
            }
        });



    }
}