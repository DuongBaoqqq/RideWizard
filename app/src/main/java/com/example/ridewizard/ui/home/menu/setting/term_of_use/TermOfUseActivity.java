package com.example.ridewizard.ui.home.menu.setting.term_of_use;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ridewizard.R;

public class TermOfUseActivity extends AppCompatActivity {
    ImageButton bt_back;
    WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_of_use);
        bt_back = findViewById(R.id.bt_back);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.ridewizard.pro/terms");
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

    }
}
