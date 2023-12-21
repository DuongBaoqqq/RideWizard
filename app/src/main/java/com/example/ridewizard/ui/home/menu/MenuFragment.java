package com.example.ridewizard.ui.home.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.driver.DriverActivity;
import com.example.ridewizard.ui.home.menu.about_us.AboutUsActivity;
import com.example.ridewizard.ui.home.menu.profile.ProfileActivity;

import com.example.ridewizard.ui.home.menu.setting.SettingActivity;
import com.example.ridewizard.ui.home.menu.setting.contact_us.ContactUsActivity;
import com.example.ridewizard.ui.welcome.LoginRegisterActivity;


public class MenuFragment extends Fragment {
    LinearLayout profile;
    TextView userName;
    AppCompatButton btnLogout;
    @SuppressLint("MissingInflatedId")
    FrameLayout about_us;
    FrameLayout setting;
    FrameLayout work_as_driver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        userName = view.findViewById(R.id.user_name);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userName.setText(sharedPreferences.getString("userName","User").toString());
        String token = "Bearer " + sharedPreferences.getString("accessToken","");
        int userId = sharedPreferences.getInt("userId",0);
        profile = view.findViewById(R.id.profile);
        btnLogout = view.findViewById(R.id.log_out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("accessToken");
                editor.apply();
                Intent intent = new Intent(getContext(), LoginRegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        about_us = view.findViewById(R.id.about_us);
        setting = view.findViewById(R.id.setting);
        work_as_driver = view.findViewById(R.id.work_as_driver);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openLink("https://www.ridewizard.pro/about");
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                assert getFragmentManager() != null;
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.frag, new SettingFragment());
//                transaction.addToBackStack(null); // Nếu bạn muốn thêm vào ngăn xếp quay lại (back stack)
//                transaction.commit();
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        work_as_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DriverActivity.class);
                Log.d("DriverActivity","DriverActivity1245345785555");
                startActivity(intent);
            }
        });
        return view;
    }
    private void openLink(String link) {
        // Mở liên kết trong trình duyệt hoặc thực hiện hành động mong muốn
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}