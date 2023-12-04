package com.example.ridewizard.ui.home.menu.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import com.example.ridewizard.model.profile.ProfileResponse;
import com.example.ridewizard.model.profile.User;
import com.example.ridewizard.model.uploadavatar.UploadAvatar;

import java.io.File;
import java.io.FileNotFoundException;
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
//    private ActivityResultLauncher<Intent> pickImageLauncher;
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
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);
        token = "Bearer " + sharedPreferences.getString("accessToken","");

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

        UserDAO.getInstance().getProfileById(token,userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
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
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
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
            // Xử lý đường dẫn của ảnh đã chọn từ thư viện
            Uri selectedImageUri = result.getData().getData();
            if(selectedImageUri != null){
                handleImageUpload(selectedImageUri);

            }
//            Bitmap bitmap = uriToBitmap(selectedImageUri);
//
//            File imageFile = new File(getCacheDir(), "image.png");
//            try {
//                FileOutputStream fos = new FileOutputStream(imageFile);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "image.png");
//                    MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",imageFile.getName(),requestFile);
//                    UserDAO.getInstance().uploadAvatar(token,multipartBody);
//                }
//                fos.flush();
//                fos.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                // Xử lý nếu có lỗi xảy ra trong quá trình lưu hình ảnh
//            }
        }
        else {
            Log.d("imageeeee", "result: "+ result);
        }
    });

    private void handleImageUpload(Uri selectedImageUri) {
        try {
            Bitmap bitmap = uriToBitmap(selectedImageUri);

            // Tiếp tục xử lý bitmap và tạo file
            File imageFile = createImageFile(bitmap);

            // Tạo RequestBody từ file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

            // Tạo MultipartBody.Part từ RequestBody
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);

            // Gọi API upload với multipartBody
            UserDAO.getInstance().uploadAvatar(token, multipartBody).enqueue(new Callback<UploadAvatar>() {
                @Override
                public void onResponse(Call<UploadAvatar> call, Response<UploadAvatar> response) {
                    if (response.isSuccessful()) {
                        Log.d("profileeee", "onResponse: " + response.body().getStatus());
                    } else {
                        Log.d("profileeee", "onResponse: " + response);
                    }
                }

                @Override
                public void onFailure(Call<UploadAvatar> call, Throwable t) {
                    Log.d("profileeee", "onResponse: " + t.getMessage());
                }
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File createImageFile(Bitmap bitmap) throws IOException {
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(picturesDirectory, "my_image.png");

        // Ghi dữ liệu của bitmap vào tệp
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }

        return imageFile;
    }

    int PICK_IMAGE = 100;
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("adasdasd " + data);
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
//            Uri selectedImageUri = data.getData();
////            handleImageUpload(selectedImageUri);
//        }
    }

    private void saveBitmapToFile(Bitmap bitmap) throws IOException {
        // Tạo một tệp mới trong thư mục ảnh của ứng dụng


        // Cập nhật thư viện ảnh
//        MediaStore.Images.Media.insertImage(getContentResolver(), imageFile.getAbsolutePath(), imageFile.getName(), imageFile.getName());
    }
}