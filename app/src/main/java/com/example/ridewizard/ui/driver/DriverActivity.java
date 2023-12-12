package com.example.ridewizard.ui.driver;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.driver.chat.ChatFragment;
import com.example.ridewizard.ui.driver.current.CurrentFragment;
import com.example.ridewizard.ui.driver.map.MapFragment;
import com.example.ridewizard.ui.driver.more.MoreFragment;
import com.example.ridewizard.ui.driver.order.OrderDriverFragment;
import com.example.ridewizard.util.LocalDataUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DriverActivity extends AppCompatActivity {
    CurrentFragment currentFragment;
    ChatFragment chatFragment;
    OrderDriverFragment orderDriverFragment;
    MapFragment mapFragment;
    MoreFragment moreFragment;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        currentFragment = new CurrentFragment();
        chatFragment = new ChatFragment();
        orderDriverFragment = new OrderDriverFragment();
        mapFragment = new MapFragment();
        moreFragment = new MoreFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag,currentFragment).commit();
        navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.current){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,currentFragment).commit();
                }else if(item.getItemId() == R.id.map){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,mapFragment).commit();
                }else if(item.getItemId() == R.id.order){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,orderDriverFragment).commit();
                }else if(item.getItemId() == R.id.chat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,chatFragment).commit();
                }else if(item.getItemId() == R.id.more){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,moreFragment).commit();
                }

                return true;
            }
        });


    }

}
