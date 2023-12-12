package com.example.ridewizard.model.DAO;

import com.example.ridewizard.API.APIClient;
import com.example.ridewizard.API.service.MapService;
import com.example.ridewizard.API.service.UserService;
import com.example.ridewizard.model.map.trip.TripResponse;

import retrofit2.Call;

public class MapDAO {

    private static MapDAO instance;
    MapService service;
    private MapDAO() {
        this.service = APIClient.getInstance().getAPI().create(MapService.class);
    }
    public static MapDAO getInstance(){
        if(instance == null){
            instance = new MapDAO();
        }
        return instance;
    }

    public Call<TripResponse> getRoute(String origin, String destination,String key){
        return service.getRoute(origin,destination,key);
    }

}
