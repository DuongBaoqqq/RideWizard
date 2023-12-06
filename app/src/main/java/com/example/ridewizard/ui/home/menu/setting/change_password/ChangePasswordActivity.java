package com.example.ridewizard.ui.home.menu.setting.change_password;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.change_password.ChangePasswordResponse;
import com.example.ridewizard.model.profile.User;
import com.example.ridewizard.ui.welcome.LoginRegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    ImageButton bt_back;
    EditText old_password;
    EditText new_password;
    EditText re_new_password;
    TextView txt_error;
    RadioButton bt_save;
    ImageButton eye1;
    ImageButton eye2;
    ImageButton eye3;
    static boolean isActive1 = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        bt_back = findViewById(R.id.bt_back);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        re_new_password = findViewById(R.id.re_new_password);
        txt_error = findViewById(R.id.txt_error);
        bt_save = findViewById(R.id.bt_save);
        eye1 = findViewById(R.id.eye1);
        eye2 = findViewById(R.id.eye2);
        eye3 = findViewById(R.id.eye3);

        eye1.setOnClickListener(view -> togglePasswordVisibility(old_password, eye1));
        eye2.setOnClickListener(view -> togglePasswordVisibility(new_password, eye2));
        eye3.setOnClickListener(view -> togglePasswordVisibility(re_new_password, eye3));

        bt_save.setOnClickListener(view -> handleChangePasswordClick());

        bt_back.setOnClickListener(view -> finish());
    }

    private void togglePasswordVisibility(EditText editText, ImageButton eyeButton) {
        isActive1 = !isActive1;
        int inputType = isActive1 ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT;
        editText.setInputType(inputType);
        eyeButton.setBackgroundResource(isActive1 ? R.drawable.baseline_remove_red_eye_24 : R.drawable.baseline_hide_eye);
    }

    private void handleChangePasswordClick() {
        if (isAnyFieldEmpty()) {
            showError("Please fill the full information.");
            return;
        }

        if (!new_password.getText().toString().equals(re_new_password.getText().toString())) {
            showError("The new password doesn't match.");
            return;
        }
//        ________________________________________________
        String strOldPassWord = old_password.getText().toString();
        String strNewPassWord = new_password.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String token = "Bearer " + sharedPreferences.getString("accessToken","");
        UserDAO.getInstance().changePassword(token,strOldPassWord,strNewPassWord).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus()==401){
                        String message = response.body().getMessage();
                        showError(message);
                    }
                    else {
                        Logout();
                    }

                }
                else {
                    Log.d("access token", "onResponse: " + response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Log.d("changePassword", "onFailure: " + t.getMessage());
            }
        });
    }
    private boolean isAnyFieldEmpty() {
        return old_password.getText().toString().isEmpty()
                || new_password.getText().toString().isEmpty()
                || re_new_password.getText().toString().isEmpty();
    }

    private void showError(String message) {
        txt_error.setText(message);
        txt_error.setVisibility(View.VISIBLE);
    }
    private void Logout()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(this, LoginRegisterActivity.class));
        finish();
    }
}
