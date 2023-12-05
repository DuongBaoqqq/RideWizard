package com.example.ridewizard.ui.driver.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.order.OrderFragment;
public class OrderDriverFragment extends OrderFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_driver, container, false);
    }

}
