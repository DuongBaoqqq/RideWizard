package com.example.ridewizard.ui.welcome;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridewizard.R;

public class Onboarding2Fragment extends Fragment {

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding2, container, false);
        AppCompatButton button = view.findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_to_left,R.anim.end).replace(R.id.frag,new OnBoarding3Fragment()).commit();
            }
        });
        return view;
    }
}