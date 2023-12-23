package com.example.ridewizard.ui.home.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridewizard.R;


public class OriginFragment extends Fragment {
    LinearLayout searchPickUpPoint;
    LinearLayout searchDestination;
    TextView pickUpLocation;
    String action;
    String SEARCH_DESTINATION = "SEARCH_DESTINATION";
    String SEARCH_PICKUP_POINT = "SEARCH_PICKUP_POINT";
    int REQUEST_CODE = 100;
    MapsFragment mapsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_origin, container, false);
//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        mapsFragment = (MapsFragment) getParentFragmentManager().findFragmentByTag("mapFragment");
        pickUpLocation = view.findViewById(R.id.my_location);
        searchPickUpPoint = view.findViewById(R.id.pick_up_point);
        searchDestination = view.findViewById(R.id.destination_point);
        searchDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SearchMapActivity.class);
                action = SEARCH_DESTINATION;
                intent.setAction(SEARCH_DESTINATION);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        searchPickUpPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SearchMapActivity.class);
                action = SEARCH_PICKUP_POINT;
                intent.setAction(SEARCH_PICKUP_POINT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        return view;
    }


    public void setTextPickupLocation(String location){
        pickUpLocation.setText(location);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == REQUEST_CODE && data!=null){
            mapsFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}