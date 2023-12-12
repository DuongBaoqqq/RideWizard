package com.example.ridewizard.ui.driver.more;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.user.User;
import com.example.ridewizard.model.user.UserResponse;
import com.example.ridewizard.ui.driver.more.profile.ProfileDriverFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.ridewizard.ui.home.HomeActivity;
import com.example.ridewizard.util.LocalDataUser;

public class MoreFragment extends Fragment {
    LinearLayout profile;
    TextView name;
    LinearLayout switchUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        profile = view.findViewById(R.id.info_profile);
        name = view.findViewById(R.id.name);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frag, new ProfileDriverFragment());
                transaction.addToBackStack(null); // Nếu bạn muốn thêm vào ngăn xếp quay lại (back stack)
                transaction.commit();
            }
        });
        switchUser = view.findViewById(R.id.switch_to_user);
        switchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDataUser.getInstance(getContext()).setCurrentType(false);
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);
        String token = "Bearer " + sharedPreferences.getString("accessToken","");
        UserDAO.getInstance().getProfileById(token,userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    User data = response.body().getData().getUser();
                    name.setText(data.getFullName());
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
}
