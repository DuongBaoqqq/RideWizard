package com.example.ridewizard.ui.home.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.ridewizard.ui.home.menu.setting.SettingFragment;


public class MenuFragment extends Fragment {
    LinearLayout profile;
    TextView userName;
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
        profile = view.findViewById(R.id.profile);
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
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frag, new SettingFragment());
                transaction.addToBackStack(null); // Nếu bạn muốn thêm vào ngăn xếp quay lại (back stack)
                transaction.commit();
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
}