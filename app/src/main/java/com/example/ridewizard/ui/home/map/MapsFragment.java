package com.example.ridewizard.ui.home.map;

import static com.android.volley.VolleyLog.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.MapDAO;
import com.example.ridewizard.model.map.trip.Data;
import com.example.ridewizard.model.map.trip.StepsItem;
import com.example.ridewizard.model.map.trip.TripResponse;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment {
    String KEY = "AIzaSyBUjeRT3vA0V9pPsGsm5dHBJ4eWZHWd6To";
    int YOUR_REQUEST_CODE = 100;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng currentLatLng;
    private Location lastKnownLocation;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    boolean locationPermissionGranted;
    FusedLocationProviderClient fusedLocationProviderClient;
    GeoApiContext geoApiContext;
    GoogleMap mMap;
    TextView pickUpLocation;
    LinearLayout searchPickUpPoint;
    LinearLayout searchDestination;
    private PlacesClient placesClient;
    SearchMapFragment searchMapFragment;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            updateLocationUI();
            getDeviceLocation();

        }
    };
    private void moveCamera(LatLng location,int zoom){

        mMap.addMarker(new MarkerOptions().position(location).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
//        new DrawRouteTask().execute(currentLatLng,location);

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
        searchMapFragment = new SearchMapFragment();
        Places.initialize(requireContext(), KEY);
        placesClient = Places.createClient(requireContext());

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        searchDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(requireContext(), SearchMapActivity.class);
//                startActivity(intent);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frag_manager,searchMapFragment,"searchMapFragment")
                        .addToBackStack(null).commit();
            }
        });

        searchPickUpPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(requireContext());
                startAutocomplete.launch(intent);
            }
        });






//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
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
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String featureName = address.getFeatureName();
                String thoroughfare = address.getThoroughfare();
                String locality = address.getLocality();
                String adminArea = address.getAdminArea();

                if (featureName != null && !featureName.equals("Unnamed")) {
                    return featureName + " " + thoroughfare;
                } else if (thoroughfare != null) {
                    return thoroughfare;
                } else if (locality != null && adminArea != null) {
                    return locality + " " + adminArea;
                }
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

    }
    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                LatLng location = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(location).title("Your Location"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM));
                                pickUpLocation.setText(getNameLocation(location));

                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    private void drawRoute(String origin, String destination){
        MapDAO.getInstance().getRoute(origin,destination,KEY).enqueue(new Callback<TripResponse>() {
            @Override
            public void onResponse(@NonNull Call<TripResponse> call, @NonNull Response<TripResponse> response) {
                assert response.body() != null;
                Data data = response.body().getData();
                PolylineOptions polylineOptions = new PolylineOptions();
                for (StepsItem point : data.getRoute().getLegs().get(0).getSteps() ) {
                    polylineOptions.add(new LatLng((Double) point.getStartLocation().getLat(), (Double) point.getStartLocation().getLng()));
                }
                mMap.addPolyline(polylineOptions);

            }

            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {

            }
        });
    }

}