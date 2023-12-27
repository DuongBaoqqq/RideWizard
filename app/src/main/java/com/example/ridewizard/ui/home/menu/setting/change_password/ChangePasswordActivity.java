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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.change_password.ChangePasswordResponse;
import com.example.ridewizard.model.profile.User;
import com.example.ridewizard.ui.welcome.LoginRegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    ImageButton bt_back;
    FrameLayout bg_old;
    FrameLayout bg_new;
    FrameLayout bg_re_new;
    EditText old_password;
    EditText new_password;
    EditText re_new_password;
    TextView txt_error;
    RadioButton bt_save;
    ImageButton eye1;
    ImageButton eye2;
    ImageButton eye3;
    String token;
    static boolean isActive1 = false;
    static boolean isActive2 = false;
    static boolean isActive3 = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        bt_back = findViewById(R.id.bt_back);
        bg_old = findViewById(R.id.bg_old);
        bg_new = findViewById(R.id.bg_new);
        bg_re_new = findViewById(R.id.bg_re_new);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        re_new_password = findViewById(R.id.re_new_password);
        txt_error = findViewById(R.id.txt_error);
        bt_save = findViewById(R.id.bt_save);
        eye1 = findViewById(R.id.eye1);
        eye2 = findViewById(R.id.eye2);
        eye3 = findViewById(R.id.eye3);

        old_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) { changeBorderEditText(bg_old, hasFocus); }
        });
        new_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) { changeBorderEditText(bg_new, hasFocus); }
        });
        re_new_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) { changeBorderEditText(bg_re_new, hasFocus); }
        });

        eye1.setOnClickListener(view -> {
            isActive1 = changeStatusVisibility(isActive1);
            togglePasswordVisibility(isActive1, old_password, eye1);
        });
        eye2.setOnClickListener(view -> {
            isActive2 = changeStatusVisibility(isActive2);
            togglePasswordVisibility(isActive2, new_password, eye2);
        });
        eye3.setOnClickListener(view -> {
            isActive3 = changeStatusVisibility(isActive3);
            togglePasswordVisibility(isActive3, re_new_password, eye3);
        });

        bt_save.setOnClickListener(view -> handleChangePasswordClick());

        bt_back.setOnClickListener(view -> finish());
    }

    private void togglePasswordVisibility(boolean isActive, EditText editText, ImageButton eyeButton) {
        int inputType = isActive ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT;
        editText.setInputType(inputType);
        eyeButton.setBackgroundResource(isActive ? R.drawable.baseline_remove_red_eye_24 : R.drawable.baseline_hide_eye);
        editText.requestFocus();
        editText.setSelection(editText.getText().length());
    }
    private boolean changeStatusVisibility(boolean isActive){
        return !isActive;
    }
    private void changeBorderEditText(FrameLayout frameLayout, boolean hasFocus) {
        if (hasFocus) {
            frameLayout.setBackgroundResource(R.drawable.border_edit_text_focused);
        } else {
            frameLayout.setBackgroundResource(R.drawable.border_edit_text);
        }
    }
    private void handleChangePasswordClick() {
        if (isAnyFieldEmpty()) {
            showError("Please fill the full information");
            return;
        }

        if (!new_password.getText().toString().equals(re_new_password.getText().toString())) {
            showError("The new password doesn't match");
            return;
        }
//        ________________________________________________
        String strOldPassWord = old_password.getText().toString();
        String strNewPassWord = new_password.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        token = "Bearer " + sharedPreferences.getString("accessToken","");
        UserDAO.getInstance().changePassword(token,strOldPassWord,strNewPassWord).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            Log.d("messageChangePassword", "Success");
                            Logout();
                        }
                    }
                    else {
                        showError("Response.body = null1");
                    }
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        JSONObject errorJson = new JSONObject(errorBodyString);
                        int status = errorJson.getInt("status");
                        String errorMessage = errorJson.getString("message");
                        if (status == 400){
                            showError("Your password must contain at least one uppercase letter, one special character (e.g., !, @, #, $), and one number.");
                        } else if (status == 401) {
                            showError(errorMessage);
                        } else{
                            Log.d("access token", String.valueOf(status));
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
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
