package com.example.ridewizard.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.driver.DriverActivity;
import com.example.ridewizard.ui.home.HomeActivity;
import com.example.ridewizard.util.LocalDataUser;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    OnboardingFragment onboardingFragment;
    SlashFragment slashFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String token = LocalDataUser.getInstance(getApplicationContext()).getToken();
        if(token.equals("")){
            fragmentManager = getSupportFragmentManager();
            slashFragment = new SlashFragment();
            onboardingFragment = new OnboardingFragment();
            fragmentManager.beginTransaction().replace(R.id.frag,slashFragment).commit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.right_to_left,R.anim.end).replace(R.id.frag,onboardingFragment).commit();
                }
            },1500);

        }else {
            if(!LocalDataUser.getInstance(getApplicationContext()).isDriverType()){
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, DriverActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }

    }
}