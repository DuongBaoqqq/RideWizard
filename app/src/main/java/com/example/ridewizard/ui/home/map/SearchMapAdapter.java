package com.example.ridewizard.ui.home.map;

import android.annotation.SuppressLint;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ridewizard.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class SearchMapAdapter extends RecyclerView.Adapter<SearchMapAdapter.SearchMapHolder> {
    List<AutocompletePrediction> data;
    MapsFragment mapsFragment;
    PlacesClient placesClient;
    FragmentManager fragmentManager;
    SetMap setMap;

    public SearchMapAdapter(MapsFragment mapsFragment,SetMap setMap) {
        this.mapsFragment = mapsFragment;
        this.setMap = setMap;
    }

    public SearchMapAdapter(MapsFragment mapsFragment, FragmentManager fragmentManager) {
//        this.mapsFragment = mapsFragment;
        placesClient = Places.createClient(mapsFragment.requireContext());
        this.fragmentManager = fragmentManager;
    }
    //    public SearchMapAdapter(List<AutocompletePrediction> data) {
//        this.data = data;
//    }
    public boolean checkDataIsNull(){
        return  data==null?true:false;
    }
    public void setData(List<AutocompletePrediction> data) {
        this.data = data;
    }



    @NonNull
    @Override
    public SearchMapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_view,parent,false);
        return new SearchMapHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchMapHolder holder, int position) {
        if (data != null && position < data.size()) {
            holder.location.setText(data.get(position).getPrimaryText(null));
            holder.secondLocation.setText(data.get(position).getSecondaryText(null));
            holder.space.setText(String.valueOf(data.get(position).getDistanceMeters()));

            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setMap.setLocation(data.get(position).getPlaceId());
//                    mapsFragment = new MapsFragment(data.get(position).getPlaceId());
//                    fragmentManager.beginTransaction().replace(R.id.frag_manager,mapsFragment).commit();
//                    Log.d("Map adapter", "onClick: "+data.get(position).getPlaceId());
                    

                }
            });
        }
    }
    private void getLatLngFromPrediction(AutocompletePrediction prediction) {
        String placeId = prediction.getPlaceId();
        List<Place.Field> placeFields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS
                // Thêm các trường khác nếu cần
        );
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId,placeFields)
                .build();

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            // Lấy tọa độ từ đối tượng Place
            double latitude = place.getLatLng().latitude;
            double longitude = place.getLatLng().longitude;

        }).addOnFailureListener((exception) -> {
            // Xử lý khi có lỗi xảy ra
        });
    }
    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    class SearchMapHolder extends RecyclerView.ViewHolder{
        TextView location;
        TextView secondLocation;
        LinearLayout container;
        TextView space;
        public SearchMapHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            container = itemView.findViewById(R.id.container);
            space = itemView.findViewById(R.id.space);
            secondLocation = itemView.findViewById(R.id.second_location);
        }
    }
}
