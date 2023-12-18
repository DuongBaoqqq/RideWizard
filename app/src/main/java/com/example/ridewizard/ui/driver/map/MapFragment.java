package com.example.ridewizard.ui.driver.map;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ridewizard.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    private static final int YOUR_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;
    GoogleMap ggDriverMap;
    private ImageView zoom_out, zoom_in, dinh_vi;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            ggDriverMap = googleMap;
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, YOUR_REQUEST_CODE);
            } else {
                fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                        ggDriverMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your Location"));
                        ggDriverMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                    } else {
                        Log.d("MapsActivity", "Location is null");
                    }
                });
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_driver, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        zoom_out = view.findViewById(R.id.zoom_out);
        zoom_in = view.findViewById(R.id.zoom_in);
        dinh_vi = view.findViewById(R.id.local);

        zoom_in.setOnClickListener(v -> zoomMap(true));
        zoom_out.setOnClickListener(v -> zoomMap(false));
        dinh_vi.setOnClickListener(v -> markCurrentLocation());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        return view;
    }

    private void zoomMap(boolean zoomIn) {
        if (ggDriverMap != null) {
            float currentZoom = ggDriverMap.getCameraPosition().zoom;

            if (zoomIn) {
                Log.d("Zoom in", "yes");
                ggDriverMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoom - 1));
            } else {
                Log.d("Zoom out", "yes");
                ggDriverMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoom + 1));
            }
        } else {
            Log.d("GoogleMap", "Null");
        }
    }

    // Hàm đánh dấu vị trí hiện tại trên bản đồ khi click vào nút định vị
    private void markCurrentLocation() {
        if (ggDriverMap != null) {
            Log.d("DinhViiii", "yes");
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, YOUR_REQUEST_CODE);
            } else {
                fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        ggDriverMap.clear();  // Clear markers and polylines
                        ggDriverMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your Location"));
                        ggDriverMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                    } else {
                        Log.d("MapsActivity", "Location is null");
                    }
                });
            }
        }
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


}
