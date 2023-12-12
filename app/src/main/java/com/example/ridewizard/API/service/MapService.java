package com.example.ridewizard.API.service;

import com.example.ridewizard.model.map.trip.TripResponse;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MapService {
    @GET("/api/v1/trips/passenger/trip")
    Call<TripResponse> getRoute(@Query("origin") String origin,
                                @Query("destination") String destination,@Query("key") String key );

}
