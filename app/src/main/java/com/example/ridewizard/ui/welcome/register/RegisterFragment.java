package com.example.ridewizard.ui.welcome.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.user.UserResponse;
import com.example.ridewizard.ui.welcome.login.VerifyCodeActivity;
import com.example.ridewizard.util.Regex;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    EditText fullName;
    EditText email;
    EditText phone;
    EditText password;
    AppCompatButton btnSignUp;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        fullName = view.findViewById(R.id.full_name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        password = view.findViewById(R.id.password);
        btnSignUp = view.findViewById(R.id.sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Regex.validateEmail(email.getText().toString())){
                    Log.d("register", "onClick: email" );
                }else if(!Regex.validatePhoneNB(phone.getText().toString())){
                    Log.d("register", "onClick: phone" );
                }else if(!Regex.validatePassword(password.getText().toString())) {
                    Log.d("register", "onClick: password" );
                }else {
                    UserDAO.getInstance().signUp(fullName.getText().toString(),email.getText().toString(),
                            phone.getText().toString(),password.getText().toString()).enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            Log.d("register", "onClick: " + response );
                            if(response.isSuccessful()){

                                Intent intent = new Intent(getContext(), VerifyCodeActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Log.d("register", "onClick: not success"  );
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Log.d("register", "onClick: " + t.getMessage() );
                        }
                    });
                }
            }
        });
        return view;
    }
}