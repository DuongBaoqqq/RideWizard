package com.example.ridewizard.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.welcome.login.LoginFragment;
import com.example.ridewizard.ui.welcome.register.RegisterFragment;

public class LoginRegisterActivity extends AppCompatActivity {
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    RadioGroup toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        toggle = findViewById(R.id.toggle);

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_to_left,R.anim.end)
                .replace(R.id.frag,loginFragment).commit();

        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.sign_in){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_to_left,R.anim.end)
                            .replace(R.id.frag,loginFragment).commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_to_left,R.anim.end)
                            .replace(R.id.frag,registerFragment).commit();
                }
            }
        });

    }
}