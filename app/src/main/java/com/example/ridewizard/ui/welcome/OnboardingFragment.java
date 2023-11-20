package com.example.ridewizard.ui.welcome;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridewizard.R;


public class OnboardingFragment extends Fragment {

    Onboarding2Fragment onboarding2Fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding_1, container, false);
        AppCompatButton button = view.findViewById(R.id.next);
        onboarding2Fragment = new Onboarding2Fragment();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.right_to_left,R.anim.end)
                        .replace(R.id.frag,onboarding2Fragment).commit();
            }
        });

        return view;
    }
}