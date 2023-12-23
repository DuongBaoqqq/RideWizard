package com.example.ridewizard.ui.home.map;

import static com.android.volley.VolleyLog.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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
import com.example.ridewizard.util.LocalDataUser;
import com.example.ridewizard.util.Map;
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
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
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
    String KEY = "AIzaSyAHUq7rXW6gtVCss6HHxDGK9Su14uwkdU0";
    int REQUEST_CODE = 100;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng currentLatLng;
    private Location lastKnownLocation;
    private String searchLocationId = "";
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    boolean locationPermissionGranted;
    FusedLocationProviderClient fusedLocationProviderClient;
    GeoApiContext geoApiContext;
    GoogleMap mMap;
    LinearLayout searchPickUpPoint;
    LinearLayout searchDestination;
    TextView pickUpLocation;
    private PlacesClient placesClient;


    String action;
    String SEARCH_DESTINATION = "SEARCH_DESTINATION";
    String SEARCH_PICKUP_POINT = "SEARCH_PICKUP_POINT";
    String PLACE_KEY = "placeId";
    LatLng pickUpPoint;
    LatLng destinationPoint;
    OriginFragment originFragment;
    NavHostFragment navHostFragment;



    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {

                }
            });
            updateLocationUI();
            getDeviceLocation();

        }
    };
    private void moveCamera(LatLng location,int zoom){

        mMap.addMarker(new MarkerOptions().position(location).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
//        new DrawRouteTask().execute(currentLatLng,location);
    }

    public void drawPlace(String placeId){
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            // Lấy thông tin cần thiết từ đối tượng Place
            String placeName = place.getName();
            LatLng placeLatLng = place.getLatLng();

            // Đặt Marker lên Google Map
            if (placeLatLng != null) {
                moveCamera(placeLatLng, 13);
            }
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                // Xử lý lỗi khi không lấy được thông tin địa điểm
            }
        });
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


        Places.initialize(requireContext(), KEY);
        placesClient = Places.createClient(requireContext());

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());



//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();

        return view;
    }




    public String getNameLocation(LatLng location){
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
        navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navHostFragment = NavHostFragment.create(R.navigation.nav_graph);
        getChildFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, navHostFragment).commit();

    }
    public void getLocationPermission() {

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
    public void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                lastKnownLocation = mMap.getMyLocation();
                pickUpPoint = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
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
    public void getDeviceLocation() {
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
                                pickUpPoint = location;
                                mMap.addMarker(new MarkerOptions().position(location).title("Your Location"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM));
                                pickUpLocation.setText(getNameLocation(pickUpPoint));
//                                LatLng latLng = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
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
    public void drawRoute(){
        String token = "Bearer " + LocalDataUser.getInstance(getContext()).getToken();
        if(destinationPoint!=null){
            String origin = pickUpPoint.latitude + ", "+ pickUpPoint.longitude;
            String destination = destinationPoint.latitude + ", " + destinationPoint.longitude;
            MapDAO.getInstance().getRoute(token,origin,destination,KEY).enqueue(new Callback<TripResponse>() {
                @Override
                public void onResponse(@NonNull Call<TripResponse> call, @NonNull Response<TripResponse> response) {
                    Data data = response.body().getData();
                    PolylineOptions polylineOptions = new PolylineOptions();
                    List<LatLng> path = Map.decodePolyline(data.getRoute().getOverview_polyline().getPoints());
                    polylineOptions.addAll(path);
                    mMap.addPolyline(polylineOptions);

                }

                @Override
                public void onFailure(Call<TripResponse> call, Throwable t) {
                    Log.d("MAPPPPP", "onFailure: "+t.getMessage());
                }
            });
        }

    }
    public void getLocationById(String placeId,String action){
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        // Thực hiện request và xử lý kết quả
        placesClient.fetchPlace(request).addOnCompleteListener(new OnCompleteListener<FetchPlaceResponse>() {
            @Override
            public void onComplete(@NonNull Task<FetchPlaceResponse> task) {
                if (task.isSuccessful()) {
                    FetchPlaceResponse fetchPlaceResponse = task.getResult();
                    Place place = fetchPlaceResponse.getPlace();

                    // Lấy LatLng từ Place
                    LatLng latLng = place.getLatLng();
                    if(action.equals(SEARCH_DESTINATION)){
                        destinationPoint = latLng;


                    }else {
                        pickUpPoint = latLng;
                        pickUpLocation.setText(getNameLocation(pickUpPoint));
//                        originFragment.setTextPickupLocation(getNameLocation(pickUpPoint));


                    }
                    mMap.clear();
                    if(destinationPoint!=null){
                        mMap.addMarker(new MarkerOptions().position(destinationPoint).title("Destination"));
                    }
                    mMap.addMarker(new MarkerOptions().position(pickUpPoint).title("Pick up point"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                    drawRoute();


                    Log.d(TAG, "LatLng: " + latLng.toString());
                } else {
//                    Status status = task.getException().getStatus();
//                    Log.e(TAG, "Error getting place details: " + status);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == REQUEST_CODE && data!=null){
            getLocationById(data.getStringExtra(PLACE_KEY),data.getAction());
        }
    }
}