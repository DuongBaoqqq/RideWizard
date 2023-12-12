package com.example.ridewizard.ui.home.menu.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.uploadavatar.AvatarResponse;
import com.example.ridewizard.model.user.User;
import com.example.ridewizard.model.user.UserResponse;
import com.example.ridewizard.util.LocalDataUser;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView userName;
    EditText email;
    EditText phone;
    ImageView avatar;
    Spinner gender;
    EditText address;
    ImageView back;
    String token;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        avatar = findViewById(R.id.avatar);
        back = findViewById(R.id.back);
        int userId = LocalDataUser.getInstance(getApplicationContext()).getUserId();
        token = "Bearer " + LocalDataUser.getInstance(getApplicationContext()).getToken();

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        UserDAO.getInstance().getProfileById(token,userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    User data = response.body().getData().getUser();
                    if(data.getAvatar() == null){
                        avatar.setImageResource(R.drawable.avatar);
                    }else {
                        Glide.with(getApplicationContext()).load(data.getAvatar()).into(avatar);
                    }
                    userName.setText(data.getFullName());
                    email.setText(data.getEmail());
                    phone.setText(data.getPhNo());
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


    }

    private Bitmap uriToBitmap(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            if (inputStream != null) {
                inputStream.close();
            }

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri selectedImageUri = result.getData().getData();
            if(selectedImageUri != null){
                handleImageUpload(selectedImageUri);

            }
        }
        else {
            Log.d("imageeeee", "result: "+ result);
        }
    });

    private void handleImageUpload(Uri selectedImageUri) {
        try {
            Bitmap bitmap = uriToBitmap(selectedImageUri);

            File imageFile = createImageFile(bitmap);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);

            MultipartBody.Part part = MultipartBody.Part.createFormData("file", "avatar.jpeg", requestFile);

            UserDAO.getInstance().uploadAvatar(token, part).enqueue(new Callback<AvatarResponse>() {
                @Override
                public void onResponse(@NonNull Call<AvatarResponse> call, @NonNull Response<AvatarResponse> response) {
                    if(response.isSuccessful()){
                        Log.d("profileeee", "onResponse: "+response.body().getMessage());
                    }else {
                        Log.d("profileeee", "onResponse: "+response);
                    }
                }

                @Override
                public void onFailure(Call<AvatarResponse> call, Throwable t) {
                    Log.d("profileeee", "onResponse: "+t.getMessage());
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File createImageFile(Bitmap bitmap) throws IOException {
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(picturesDirectory, "my_image.jpeg");

        // Ghi dữ liệu của bitmap vào tệp
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }

        return imageFile;
    }

    int PICK_IMAGE = 100;
    int CROP_IMAGE = 101;
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImageUri = data.getData();
            startUCrop(selectedImageUri);
        }else if(requestCode == CROP_IMAGE && resultCode == RESULT_OK && data != null){
            Uri croppedImageUri = UCrop.getOutput(data);
            handleImageUpload(croppedImageUri);

        }
    }

    private void startUCrop(Uri selectedImageUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));
        float x = (float) (avatar.getWidth());
        float y = (float)(avatar.getHeight());

        UCrop uCrop = UCrop.of(selectedImageUri, destinationUri)
                .withAspectRatio(x, y);
        Log.d("djaskda", "X : " +x + " Y: "+y);
//        cropImageLauncher.launch(uCrop.getIntent(this));
        startActivityForResult(uCrop.getIntent(this),CROP_IMAGE);
    }
}