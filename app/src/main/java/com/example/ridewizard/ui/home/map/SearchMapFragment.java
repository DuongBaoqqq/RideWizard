package com.example.ridewizard.ui.home.map;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    LatLng originLocation;

    public SearchMapFragment(LatLng originLocation) {
        this.originLocation = originLocation;
        Log.d("MAPPPPP", "onComplete: "+originLocation.toString());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_map, container, false);

        searchBar = view.findViewById(R.id.container_search);
        search = view.findViewById(R.id.search);
        listLocation = view.findViewById(R.id.list_location);
        back = view.findViewById(R.id.back);
        searchContent = view.findViewById(R.id.search_content);
        notFound = view.findViewById(R.id.not_found);
        resultCount = view.findViewById(R.id.count_result);
        placesClient = Places.createClient(requireContext());
        mapsFragment = (MapsFragment) getParentFragmentManager().findFragmentByTag("mapFragment");

        adapter = new SearchMapAdapter(mapsFragment,getParentFragmentManager());
        listLocation.setAdapter(adapter);
        listLocation.setLayoutManager(new LinearLayoutManager(requireContext()));
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
                getParentFragmentManager().beginTransaction().replace(R.id.frag_manager,mapsFragment).commit();
            }
        });

        return view;
    }


    private void getLocationByName(String query){
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        Log.d("MAPPPPP", "onComplete: "+originLocation.toString());
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
//                .setLocationBias(boundsVietnam)
                .setOrigin(originLocation)
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
}