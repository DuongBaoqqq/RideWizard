package com.example.ridewizard.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ridewizard.ui.home.history.HistoryFragment;
import com.example.ridewizard.ui.home.listener.ContactActivity;
import com.example.ridewizard.ui.home.map.MapManagerFragment;
import com.example.ridewizard.ui.home.map.MapsFragment;
import com.example.ridewizard.ui.home.menu.MenuFragment;
import com.example.ridewizard.ui.home.order.OrderFragment;

public class PagerAdapter extends FragmentStateAdapter {
    MenuFragment menuFragment;
    OrderFragment orderFragment;
    MapManagerFragment mapManagerFragment;
    HistoryFragment historyFragment;
    FragmentActivity fragmentActivity;
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity,ContactActivity contactActivity) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
        historyFragment = new HistoryFragment();
        orderFragment = new OrderFragment();
        mapManagerFragment = new MapManagerFragment();
        menuFragment = new MenuFragment(contactActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return mapManagerFragment;
            case 1:
                return orderFragment;
            case 2:
                return historyFragment;
            default:
                return menuFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
