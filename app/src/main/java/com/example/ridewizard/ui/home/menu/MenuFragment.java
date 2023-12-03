package com.example.ridewizard.ui.home.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.menu.setting.about_us.AboutUsActivity;
import com.example.ridewizard.ui.home.menu.profile.ProfileActivity;


public class MenuFragment extends Fragment {
    LinearLayout profile;
    TextView userName;
    FrameLayout about_us;

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
        return view;
    }
}