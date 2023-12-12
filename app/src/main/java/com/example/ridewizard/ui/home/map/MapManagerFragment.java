package com.example.ridewizard.ui.home.map;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridewizard.R;


public class MapManagerFragment extends Fragment {
    FragmentTransaction transaction;
    MapsFragment mapsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_manager, container, false);
        mapsFragment = new MapsFragment();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_manager,mapsFragment,"mapFragment").addToBackStack(null).commit();
        return view;
    }
}