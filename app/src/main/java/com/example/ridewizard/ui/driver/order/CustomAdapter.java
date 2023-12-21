package com.example.ridewizard.ui.driver.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ridewizard.R;
import com.example.ridewizard.model.OrderDriver.OrderItem;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<OrderItem> {
    TextView address_from;
    TextView address_to;
    TextView time;
    ImageButton menu;
    public CustomAdapter(Context context, int resource, List<OrderItem> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OrderItem currentItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }
        address_from = convertView.findViewById(R.id.address_from);
        address_from.setText(currentItem.getAddress_from());
        address_to = convertView.findViewById(R.id.address_to);
        address_to.setText(currentItem.getAddress_to());
        time = convertView.findViewById(R.id.time);
        if (currentItem.getTime()!=null){
            time.setText(currentItem.getTime());
        }
        menu = convertView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = position+1;
                Toast.makeText(getContext(), "Clicked for item: " + index, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
