package com.example.ridewizard.ui.home.menu.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.menu.MenuFragment;
import com.example.ridewizard.ui.home.menu.setting.change_password.ChangePasswordActivity;
import com.example.ridewizard.ui.home.menu.setting.contact_us.ContactUsActivity;
import com.example.ridewizard.ui.home.menu.setting.privacy.PrivacyActivity;
import com.example.ridewizard.ui.home.menu.setting.term_of_use.TermOfUseActivity;

public class SettingActivity extends AppCompatActivity {
    FrameLayout term_of_use;
    FrameLayout contact_us;
    FrameLayout change_password;
    FrameLayout privacy;
    ImageButton bt_back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        term_of_use = findViewById(R.id.term_of_use);
        contact_us = findViewById(R.id.contact_us);
        bt_back = findViewById(R.id.bt_back);
        change_password = findViewById(R.id.change_password);
        privacy = findViewById(R.id.privacy);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(intent);
            }
        });
        term_of_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermOfUseActivity.class);
                startActivity(intent);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}