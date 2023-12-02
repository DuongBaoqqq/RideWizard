package com.example.ridewizard.ui.welcome;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.welcome.change_password.ChangePasswordFragment;
import com.example.ridewizard.ui.welcome.login.LoginFragment;
import com.example.ridewizard.ui.welcome.register.RegisterFragment;

public class ChangePasswordActivity extends AppCompatActivity {
    ChangePasswordFragment changePasswordFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changePasswordFragment = new ChangePasswordFragment();

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_to_left,R.anim.end)
                .replace(R.id.frag,changePasswordFragment).commit();

    }

}
