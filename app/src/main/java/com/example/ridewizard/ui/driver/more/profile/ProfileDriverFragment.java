package com.example.ridewizard.ui.driver.more.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.uploadImage.Driver;
import com.example.ridewizard.model.user.User;
import com.example.ridewizard.model.user.UserResponse;
import com.example.ridewizard.ui.driver.more.profile.profileScreenDetail.UploadImageActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDriverFragment extends Fragment {
    List<LinearLayout>  listItemProfiles = new ArrayList<>();
    List<TextView> listItemTTProfiles = new ArrayList<>();
    LinearLayout back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_driver, container, false);
        back = view.findViewById(R.id.bt_back);

        listItemProfiles.add(view.findViewById(R.id.login));
        listItemProfiles.add(view.findViewById(R.id.phone_number));
        listItemProfiles.add(view.findViewById(R.id.vehicle));
        listItemProfiles.add(view.findViewById(R.id.get_a_priority____));
        listItemProfiles.add(view.findViewById(R.id.email));
        listItemProfiles.add(view.findViewById(R.id.client_promo_code));
        listItemProfiles.add(view.findViewById(R.id.driver_recruiter_code));
        listItemProfiles.add(view.findViewById(R.id.id_card));
        listItemProfiles.add(view.findViewById(R.id.driver_portrait));
        listItemProfiles.add(view.findViewById(R.id.driver_license));
        listItemProfiles.add(view.findViewById(R.id.criminal_record));
        listItemProfiles.add(view.findViewById(R.id.compulsory_accident));
        listItemProfiles.add(view.findViewById(R.id.medical_health));
        listItemProfiles.add(view.findViewById(R.id.vehicle_certificate));
        listItemProfiles.add(view.findViewById(R.id.vehicle_image));

        listItemTTProfiles.add(view.findViewById(R.id.tt_login));
        listItemTTProfiles.add(view.findViewById(R.id.tt_phone_number));
        listItemTTProfiles.add(view.findViewById(R.id.tt_vehicle));
        listItemTTProfiles.add(view.findViewById(R.id.tt_get_a_priority____));
        listItemTTProfiles.add(view.findViewById(R.id.tt_email));
        listItemTTProfiles.add(view.findViewById(R.id.tt_client_promo_code));
        listItemTTProfiles.add(view.findViewById(R.id.tt_driver_recruiter_code));
        listItemTTProfiles.add(view.findViewById(R.id.tt_id_card));
        listItemTTProfiles.add(view.findViewById(R.id.tt_driver_portrait));
        listItemTTProfiles.add(view.findViewById(R.id.tt_driver_license));
        listItemTTProfiles.add(view.findViewById(R.id.tt_criminal_record));
        listItemTTProfiles.add(view.findViewById(R.id.tt_compulsory_accident));
        listItemTTProfiles.add(view.findViewById(R.id.tt_medical_health));
        listItemTTProfiles.add(view.findViewById(R.id.tt_vehicle_certificate));
        listItemTTProfiles.add(view.findViewById(R.id.tt_vehicle_image));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        for (int i = 0; i < 15; i++) {
            if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
                final int index = i;
                listItemProfiles.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleListItemClick(index);
                    }
                });
            }
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);
        String token = "Bearer " + sharedPreferences.getString("accessToken","");
        UserDAO.getInstance().getProfileById(token,userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                Driver data = response.body().getData().getDriver();
                User data = response.body().getData().getUser();
                if(response.isSuccessful()){
                    listItemTTProfiles.get(0).setText(String.valueOf(data.getId()));
                    listItemTTProfiles.get(1).setText(data.getPhNo());
//                    listItemTTProfiles.get(2).setText(data.getVehicle());
//                    listItemTTProfiles.get(3).setText(data.getPriority_and_decreased());
                    listItemTTProfiles.get(4).setText(data.getEmail());
//                    listItemTTProfiles.get(5).setText(data.getClient_promo());
//                    listItemTTProfiles.get(6).setText(data.getDriver_recruiter());
                }
                else {
                    Log.d("access token", "onResponse: " + response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("access token", "onFailure: " + t.getMessage());
            }
        });
        return view;
    }
    private void handleListItemClick(int index) {
        Intent intent = new Intent(getContext(), UploadImageActivity.class);
        intent.putExtra("key", index);
        startActivity(intent);
    }


}
