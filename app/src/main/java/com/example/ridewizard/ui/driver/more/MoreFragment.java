package com.example.ridewizard.ui.driver.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.ridewizard.R;
import com.example.ridewizard.ui.home.HomeActivity;
import com.example.ridewizard.util.LocalDataUser;

public class MoreFragment extends Fragment {
    LinearLayout switchUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        switchUser = view.findViewById(R.id.switch_to_user);
        switchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDataUser.getInstance(getContext()).setCurrentType(false);
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}
