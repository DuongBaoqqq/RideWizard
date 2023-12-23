package com.example.ridewizard.ui.home.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class SearchMapActivity extends AppCompatActivity implements SetMap {
    String TAG = "SEARCH VIEW ACTIVITY";
    String SEARCH_DESTINATION = "SEARCH_DESTINATION";
    String SEARCH_PICKUP_POINT = "SEARCH_PICKUP_POINT";
    String currentSearch;
    LinearLayout searchBar;
    EditText search;
    LinearLayout back;
    RecyclerView listLocation;
    PlacesClient placesClient;
    SearchMapAdapter adapter;
    MapsFragment mapsFragment;
    ImageView notFound;
    TextView searchContent;
    TextView resultCount;
    Handler handler = new Handler();
    Runnable runnable;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        searchBar = findViewById(R.id.container_search);
        search = findViewById(R.id.search);
        listLocation = findViewById(R.id.list_location);
        searchContent = findViewById(R.id.search_content);
        back = findViewById(R.id.back);
        notFound = findViewById(R.id.not_found);
        resultCount = findViewById(R.id.count_result);
        placesClient = Places.createClient(getApplicationContext());
        mapsFragment = (MapsFragment) getSupportFragmentManager().findFragmentByTag("mapFragment");

        adapter = new SearchMapAdapter(mapsFragment,this::setLocation);
        listLocation.setAdapter(adapter);
        listLocation.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchContent.setText("\""+s.toString()+"\"");
                handler.removeCallbacks(runnable);

                runnable = () -> {
                    getLocationByName(s.toString());

                };
                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                .setCountries("VN") // Mã quốc gia của Việt Nam
                .setTypesFilter(Arrays.asList(PlaceTypes.ADDRESS))
                .setSessionToken(token)
                .setQuery(query)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            adapter.setData(response.getAutocompletePredictions());
            adapter.notifyDataSetChanged();
            resultCount.setText(String.valueOf(adapter.getItemCount())+" found");
            if(adapter.checkDataIsNull()){
                notFound.setVisibility(View.VISIBLE);
            }else {
                notFound.setVisibility(View.GONE);
            }
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
            }
        });

    }
    int REQUEST_CODE =100;
    String PLACE_KEY = "placeId";
    @Override
    public void setLocation(String placeId) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PLACE_KEY, placeId);
        if(getIntent().getAction().equals(SEARCH_DESTINATION)){
            resultIntent.setAction(SEARCH_DESTINATION);
        }else {
            resultIntent.setAction(SEARCH_PICKUP_POINT);
        }
        setResult(REQUEST_CODE, resultIntent);
        finish();

    }
}