package com.example.ridewizard.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.driver.DriverActivity;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    OnboardingFragment onboardingFragment;
    SlashFragment slashFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Kiểm tra trạng thái hiện tại
        SharedPreferences preferences = getSharedPreferences("app_state", MODE_PRIVATE);
        String currentActivity = preferences.getString("current_activity", "");

        // Mở Activity tương ứng
        if ("DriverActivity".equals(currentActivity)) {
            Intent intent = new Intent(this, DriverActivity.class);
            startActivity(intent);
        } else {
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
}