package com.example.ridewizard.ui.home.menu.about_us;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.menu.MenuFragment;
import com.example.ridewizard.ui.welcome.MainActivity;

public class AboutUsActivity extends AppCompatActivity{
    ImageButton bt_back;
    WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        bt_back = findViewById(R.id.bt_back);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.ridewizard.pro/about");
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // quay lại trang menu
            }

        });

    }
}
