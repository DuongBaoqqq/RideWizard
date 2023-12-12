package com.example.ridewizard.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ridewizard.ui.home.history.HistoryFragment;
import com.example.ridewizard.ui.home.map.MapsFragment;
import com.example.ridewizard.ui.home.menu.MenuFragment;
import com.example.ridewizard.ui.home.order.OrderFragment;

public class PagerAdapter extends FragmentStateAdapter {
    MenuFragment menuFragment;
    OrderFragment orderFragment;
    MapsFragment mapsFragment;
    HistoryFragment historyFragment;
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        historyFragment = new HistoryFragment();
        orderFragment = new OrderFragment();
        mapsFragment = new MapsFragment();
        menuFragment = new MenuFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return mapsFragment;
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
