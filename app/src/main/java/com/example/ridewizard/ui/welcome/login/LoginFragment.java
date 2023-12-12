package com.example.ridewizard.ui.welcome.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridewizard.API.APIClient;
import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.user.UserResponse;
import com.example.ridewizard.ui.home.HomeActivity;
import com.example.ridewizard.util.LocalDataUser;
import com.example.ridewizard.util.Regex;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    EditText userName;
    EditText password;
    TextView checkMail;
    TextView checkPassword;
    TextView forgotPassword;
    LinearLayout btnSignIn;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        userName = view.findViewById(R.id.user_name);
        password = view.findViewById(R.id.password);
        btnSignIn = view.findViewById(R.id.sign_in);
        checkMail = view.findViewById(R.id.check_mail);
        checkPassword = view.findViewById(R.id.check_password);
        forgotPassword = view.findViewById(R.id.forgot_password);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
                if(!Regex.validateEmail(userName.getText().toString())){
                    checkMail.setVisibility(View.VISIBLE);
                    Log.d("loginnnnnn", "login: validateEmail" );
                }
                else if(!Regex.validatePassword(password.getText().toString())){
                    checkPassword.setVisibility(View.VISIBLE);
                    Log.d("loginnnnnn", "login: validatePassword" );
                }
                else {
                    UserDAO.getInstance().login(userName.getText().toString(),password.getText().toString(),"email").enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            Log.d("loginnnnnn", "login:");

                            if(response.isSuccessful()) {
                                if (response.body().getStatus() == 402) {
                                    checkMail.setVisibility(View.VISIBLE);
                                } else if (response.body().getStatus() == 409) {
                                    checkPassword.setVisibility(View.VISIBLE);
                                } else {

                                    SharedPreferences.Editor editor = LocalDataUser.getInstance(getContext()).getEditor();
                                    editor.putInt("userId", response.body().getData().getUser().getId());
                                    editor.putString("userName", response.body().getData().getUser().getFullName());
                                    editor.putString("accessToken", response.body().getData().getAccessToken());
                                    LocalDataUser.getInstance(getContext()).setCurrentType(false);
                                    APIClient.getInstance().setAccessToken(response.body().getData().getAccessToken());
                                    editor.apply();
                                    checkMail.setVisibility(View.GONE);
                                    checkMail.setVisibility(View.GONE);
                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    Log.d("loginnnnnn", "login: " + response);
                                }
//                                }else{
//                                    Log.d("loginnnnnn", "onResponse: " + response.message());
//                                    checkPassword.setVisibility(View.VISIBLE);
//                                    checkMail.setVisibility(View.VISIBLE);
//                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Log.d("loginnnnnn", "login: " + t.getMessage());
                        }
                    });
                }
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}