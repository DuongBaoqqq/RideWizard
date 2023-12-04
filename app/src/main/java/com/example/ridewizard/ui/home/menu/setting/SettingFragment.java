package com.example.ridewizard.ui.home.menu.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.example.ridewizard.R;

public class SettingFragment extends Fragment {
    FrameLayout term_of_use;
    FrameLayout about_us;
    FrameLayout contact_us;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        term_of_use = view.findViewById(R.id.term_of_use);
        about_us = view.findViewById(R.id.about_us);
        contact_us = view.findViewById(R.id.contact_us);

        return view;
    }
}
