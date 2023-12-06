package com.example.ridewizard.ui.home.menu.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.menu.MenuFragment;
import com.example.ridewizard.ui.home.menu.setting.change_password.ChangePasswordActivity;
import com.example.ridewizard.ui.home.menu.setting.contact_us.ContactUsActivity;
import com.example.ridewizard.ui.home.menu.setting.privacy.PrivacyActivity;
import com.example.ridewizard.ui.home.menu.setting.term_of_use.TermOfUseActivity;

public class SettingFragment extends Fragment {
    FrameLayout term_of_use;
    FrameLayout contact_us;
    FrameLayout change_password;
    FrameLayout privacy;
    ImageButton bt_back;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        term_of_use = view.findViewById(R.id.term_of_use);
        contact_us = view.findViewById(R.id.contact_us);
        bt_back = view.findViewById(R.id.bt_back);
        change_password = view.findViewById(R.id.change_password);
        privacy = view.findViewById(R.id.privacy);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frag, new MenuFragment());
                transaction.addToBackStack(null); // Nếu bạn muốn thêm vào ngăn xếp quay lại (back stack)
                transaction.commit();
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrivacyActivity.class);
                startActivity(intent);
            }
        });
        term_of_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermOfUseActivity.class);
                startActivity(intent);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
