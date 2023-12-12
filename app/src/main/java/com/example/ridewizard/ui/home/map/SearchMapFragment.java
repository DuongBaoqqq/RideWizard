package com.example.ridewizard.ui.home.map;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridewizard.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.errors.ApiException;

import java.util.Arrays;


public class SearchMapFragment extends Fragment {
    String TAG = "SEARCH VIEW ACTIVITY";
    SearchView searchView;
    RecyclerView listLocation;
    PlacesClient placesClient;
    SearchMapAdapter adapter;
    MapsFragment mapsFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_map, container, false);
        searchView = view.findViewById(R.id.search);
        listLocation = view.findViewById(R.id.list_location);

        placesClient = Places.createClient(requireContext());
        adapter = new SearchMapAdapter();
        listLocation.setAdapter(adapter);
        listLocation.setLayoutManager(new LinearLayoutManager(requireContext()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getLocationByName(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return view;
    }


    private void getLocationByName(String query){
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        RectangularBounds boundsVietnam = RectangularBounds.newInstance(
                new LatLng(8.18, 102.14), // Phía bắc
                new LatLng(23.39, 109.46)  // Phía nam
        );

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setLocationBias(boundsVietnam)
                .setCountries("VN") // Mã quốc gia của Việt Nam
                .setTypesFilter(Arrays.asList(PlaceTypes.ADDRESS))
                .setSessionToken(token)
                .setQuery(query)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            adapter.setData(response.getAutocompletePredictions());
            adapter.notifyDataSetChanged();
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
            }
        });

    }
}