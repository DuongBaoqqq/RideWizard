package com.example.ridewizard.ui.home.map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridewizard.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StyleSpan;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {
    int YOUR_REQUEST_CODE = 100;
    int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng currentLatLng;
    private FusedLocationProviderClient fusedLocationClient;
    GeoApiContext geoApiContext;
    GoogleMap mMap;
    TextView pickUpLocation;
    LinearLayout searchPickUpPoint;
    LinearLayout searchDestination;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, YOUR_REQUEST_CODE);
            } else {

                fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        pickUpLocation.setText(getNameLocation(currentLatLng));
                        mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                    } else {
                        Log.d("MapsActivity", "Location is null");
                    }
                });
            }

        }
    };
    private void moveCamera(LatLng location,int zoom){

        mMap.addMarker(new MarkerOptions().position(location).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        new DrawRouteTask().execute(currentLatLng,location);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        pickUpLocation = view.findViewById(R.id.my_location);
        searchPickUpPoint = view.findViewById(R.id.pick_up_point);
        searchDestination = view.findViewById(R.id.destination_point);
        searchDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(getContext());
                startAutocomplete.launch(intent);
            }
        });

        searchPickUpPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(getContext());
                startAutocomplete.launch(intent);
            }
        });






        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        Places.initialize(getContext(), "AIzaSyBivdveQCoYAhOEMd0xCUlKsPKSaq6nSA8");
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyBUjeRT3vA0V9pPsGsm5dHBJ4eWZHWd6To")
                .build();

        return view;
    }
    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        LatLng lng = place.getLatLng();
                        if(lng!=null){
                            moveCamera(lng,13);
                        }
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            });
    private String getNameLocation(LatLng location){
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                return address.getFeatureName() + " " + address.getThoroughfare();
            }
        } catch (IOException e) {
            Log.e("ReverseGeocoding", "Error getting address", e);
        }
        return null;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//        EditText editTextSearchMap = autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input);



//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(@NonNull Place place) {
//                // Xử lý khi một địa điểm được chọn từ gợi ý tìm kiếm
//                LatLng location = place.getLatLng();
//                moveCamera(location,14);
////                new DrawRouteTask().execute(currentLatLng,location);
//
//            }
//
//            @Override
//            public void onError(@NonNull Status status) {
//                // Xử lý lỗi nếu có
//                Log.e("PlacesAPI", "onError: " + status);
//            }
//        });


    }
    private class DrawRouteTask extends AsyncTask<LatLng, Void, DirectionsResult> {
        @Override
        protected DirectionsResult doInBackground(LatLng... params) {
            LatLng origin = params[0];
            LatLng destination = params[1];

            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyBUjeRT3vA0V9pPsGsm5dHBJ4eWZHWd6To")
                    .build();

            try {
                DirectionsResult result = DirectionsApi.newRequest(context)
                        .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                        .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                        .await();

                return result;
            } catch (Exception e) {
                Log.d("MAP -->", "doInBackground: "+e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(DirectionsResult directionsResult) {
            if (directionsResult != null && directionsResult.routes != null && directionsResult.routes.length > 0) {
                DirectionsRoute route = directionsResult.routes[0];
                PolylineOptions polylineOptions = new PolylineOptions();

                // Lặp qua tất cả các bước trên đường và thêm vào danh sách các điểm để vẽ
                for (com.google.maps.model.LatLng point : route.overviewPolyline.decodePath()) {
                    polylineOptions.add(new LatLng(point.lat, point.lng));
                }

                // Vẽ tuyến đường lên bản đồ
                mMap.addPolyline(polylineOptions);
            }
        }
    }


}