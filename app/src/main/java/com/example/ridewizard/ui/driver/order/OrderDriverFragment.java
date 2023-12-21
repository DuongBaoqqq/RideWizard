package com.example.ridewizard.ui.driver.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ridewizard.R;
import com.example.ridewizard.model.OrderDriver.OrderItem;
import com.example.ridewizard.ui.home.order.OrderFragment;

import java.util.Arrays;
import java.util.List;

public class OrderDriverFragment extends OrderFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_driver, container, false);

        ListView listView = view.findViewById(R.id.order); // Replace with your ListView ID
        List<OrderItem> listOrder = Arrays.asList(
                new OrderItem("From1","To1"),
                new OrderItem("From2","To2"),
                new OrderItem("From3","To3"),
                new OrderItem("From4","To4"),
                new OrderItem("From5","To5"),
                new OrderItem("From6","To6"),
                new OrderItem("From7","To7")
                );
        CustomAdapter customAdapter = new CustomAdapter(requireContext(), R.layout.custom_list_item,listOrder);
        listView.setAdapter(customAdapter);

        return view;
    }

}
