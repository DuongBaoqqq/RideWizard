package com.example.ridewizard.ui.home.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ridewizard.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.net.SearchByTextRequest;
import com.google.maps.errors.ApiException;

import java.util.Arrays;
import java.util.List;

public class SearchMapActivity extends AppCompatActivity {
    String TAG = "SEARCH VIEW ACTIVITY";
    SearchView searchView;
    RecyclerView listLocation;
    PlacesClient placesClient;
    SearchMapAdapter adapter;
    MapsFragment mapsFragment;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        searchView = findViewById(R.id.search);
        listLocation = findViewById(R.id.list_location);



        placesClient = Places.createClient(this);
        adapter = new SearchMapAdapter();
        listLocation.setAdapter(adapter);
        listLocation.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    }

    private void getLocationByName(String query){
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

//        RectangularBounds boundsVietnam = RectangularBounds.newInstance(
//                new LatLng(8.18, 102.14), // Phía bắc
//                new LatLng(23.39, 109.46)  // Phía nam
//        );

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
//                .setLocationBias(boundsVietnam)
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