package com.example.ridewizard.ui.welcome.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.HomeActivity;

public class VerifyCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}