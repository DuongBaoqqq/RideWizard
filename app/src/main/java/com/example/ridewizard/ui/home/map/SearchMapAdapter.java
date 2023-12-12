package com.example.ridewizard.ui.home.map;

import android.text.style.CharacterStyle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ridewizard.R;
import com.google.android.libraries.places.api.model.AutocompletePrediction;

import java.util.List;

public class SearchMapAdapter extends RecyclerView.Adapter<SearchMapAdapter.SearchMapHolder> {
    List<AutocompletePrediction> data;

//    public SearchMapAdapter(List<AutocompletePrediction> data) {
//        this.data = data;
//    }

    public void setData(List<AutocompletePrediction> data) {
        this.data = data;
    }



    @NonNull
    @Override
    public SearchMapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_view,parent,false);
        return new SearchMapHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMapHolder holder, int position) {
        if (data != null && position < data.size()) {
            holder.location.setText(data.get(position).getFullText(null));

        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    class SearchMapHolder extends RecyclerView.ViewHolder{
        TextView location;
        LinearLayout container;
        public SearchMapHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            container = itemView.findViewById(R.id.container);
        }
    }
}
