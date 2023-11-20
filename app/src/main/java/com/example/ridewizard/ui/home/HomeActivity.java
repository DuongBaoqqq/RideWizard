package com.example.ridewizard.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.history.HistoryFragment;
import com.example.ridewizard.ui.home.map.MapsFragment;
import com.example.ridewizard.ui.home.menu.MenuFragment;
import com.example.ridewizard.ui.home.order.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    MapsFragment mapsFragment;
    HistoryFragment historyFragment;
    MenuFragment menuFragment;
    OrderFragment orderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        historyFragment = new HistoryFragment();
        mapsFragment = new MapsFragment();
        menuFragment = new MenuFragment();
        orderFragment = new OrderFragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.frag,mapsFragment).commit();

        navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,mapsFragment).commit();
                }else if(item.getItemId() == R.id.order){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,orderFragment).commit();
                }else if(item.getItemId() == R.id.history){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,historyFragment).commit();
                }else if(item.getItemId() == R.id.menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag,menuFragment).commit();
                }

                return true;
            }
        });
    }
}