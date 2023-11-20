package com.example.ridewizard.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;

import com.example.ridewizard.R;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    OnboardingFragment onboardingFragment;
    SlashFragment slashFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        slashFragment = new SlashFragment();
        onboardingFragment = new OnboardingFragment();
        fragmentManager.beginTransaction().replace(R.id.frag,slashFragment).commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_to_left,R.anim.end).replace(R.id.frag,onboardingFragment).commit();
            }
        },1500);
    }
}