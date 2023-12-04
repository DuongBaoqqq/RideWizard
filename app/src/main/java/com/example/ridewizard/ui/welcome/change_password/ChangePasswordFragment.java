package com.example.ridewizard.ui.welcome.change_password;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ridewizard.API.APIClient;
import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.change_password.ChangePasswordResponse;
import com.example.ridewizard.ui.home.menu.MenuFragment;
import com.example.ridewizard.ui.welcome.ChangePasswordActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {
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
    static boolean isActive2 = false;
    static boolean isActive3 = false;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        bt_back = view.findViewById(R.id.bt_back);
        old_password = view.findViewById(R.id.old_password);
        new_password = view.findViewById(R.id.new_password);
        re_new_password = view.findViewById(R.id.re_new_password);
        txt_error = view.findViewById(R.id.txt_error);
        bt_save = view.findViewById(R.id.bt_save);
        eye1 = view.findViewById(R.id.eye1);
        eye2 = view.findViewById(R.id.eye2);
        eye3 = view.findViewById(R.id.eye3);
        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isActive1 = !isActive1;
               if (isActive1){
                   eye1.setBackgroundResource(R.drawable.baseline_remove_red_eye_24);
                   old_password.setInputType(InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_VARIATION_PASSWORD);
               }
               else{
                   eye1.setBackgroundResource(R.drawable.baseline_hide_eye);
                   old_password.setInputType(InputType.TYPE_CLASS_TEXT);
               }
            }
        });
        eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActive2 = !isActive2;
                if (isActive2){
                    eye2.setBackgroundResource(R.drawable.baseline_remove_red_eye_24);
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                else{
                    eye2.setBackgroundResource(R.drawable.baseline_hide_eye);
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        eye3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActive3 = !isActive3;
                if (isActive3){
                    eye3.setBackgroundResource(R.drawable.baseline_remove_red_eye_24);
                    re_new_password.setInputType(InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                else{
                    eye3.setBackgroundResource(R.drawable.baseline_hide_eye);
                    re_new_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            private String getCurrentPassword() {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                return sharedPreferences.getString("currentPassword", "");
            }
            private void updateSharedPreferences(int userId, String userName, String accessToken) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId", userId);
                editor.putString("userName", userName);
                editor.putString("accessToken", accessToken);
                editor.apply();
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (old_password.getText().toString().isEmpty() || new_password.getText().toString().isEmpty() || re_new_password.getText().toString().isEmpty()) {
                    txt_error.setText("Vui lòng nhập đầy đủ thông tin.");
                    txt_error.setVisibility(View.VISIBLE);
                    return;
                }

                if (!new_password.getText().toString().equals(re_new_password.getText().toString())) {
                    txt_error.setText("Mật khẩu mới không trùng khớp.");
                    txt_error.setVisibility(View.VISIBLE);
                    return;
                }
                String currentPassword = getCurrentPassword();
                if (!old_password.getText().toString().equals(currentPassword)) {
                    txt_error.setText("Mật khẩu cũ không chính xác.");
                    txt_error.setVisibility(View.VISIBLE);
                    return;
                }
                UserDAO.getInstance().changePassword(old_password.getText().toString(), new_password.getText().toString(), re_new_password.getText().toString()).enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                            // Xử lý khi thay đổi mật khẩu thành công
                            updateSharedPreferences(response.body().getData().getUser().getId(), response.body().getData().getUser().getFullName(), response.body().getData().getAccessToken());
                        } else {
                            // Xử lý khi thay đổi mật khẩu không thành công
                            if (response.body() != null) {
                                txt_error.setText(response.body().getMessage());
                            } else {
                                txt_error.setText("Có lỗi xảy ra. Vui lòng thử lại sau.");
                            }
                            txt_error.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                        // Xử lý khi gặp lỗi kết nối
                        Log.e("changepassword", "onFailure: " + t.getMessage());
                        txt_error.setText("Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng.");
                        txt_error.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuFragment.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
