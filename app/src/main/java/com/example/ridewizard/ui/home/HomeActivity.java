package com.example.ridewizard.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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
    ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = findViewById(R.id.pager);
        navigation = findViewById(R.id.navigation);
        PagerAdapter adapter = new PagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    viewPager.setCurrentItem(0);
                }else if(item.getItemId() == R.id.order){
                    viewPager.setCurrentItem(1);
                }else if(item.getItemId() == R.id.history){
                    viewPager.setCurrentItem(2);
                }else if(item.getItemId() == R.id.menu){
                    viewPager.setCurrentItem(3);
                }

                return true;
            }
        });
    }
}