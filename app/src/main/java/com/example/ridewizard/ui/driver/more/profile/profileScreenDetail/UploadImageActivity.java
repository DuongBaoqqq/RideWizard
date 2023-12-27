package com.example.ridewizard.ui.driver.more.profile.profileScreenDetail;

import android.content.Context;
import android.content.Intent;

import com.example.ridewizard.R;
import com.example.ridewizard.model.DAO.UserDAO;
import com.example.ridewizard.model.uploadImage.LoadImageResponse;
import com.example.ridewizard.model.uploadImage.ProfileDriver;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    LinearLayout back;
    LinearLayout image1,image2,image3,image4;
    ImageView imageView1,imageView2,imageView3,imageView4;
    TextView txt1,txt2,txt3,txt4;

    int indexImage;
    int index;
    int numberImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        back = findViewById(R.id.bt_back);
        index = getIntent().getIntExtra("key", -1);
        List<String[]> data = getDataNeedImage(index);
        if (data != null) {
            numberImage = data.size();
            hideImage(numberImage);
        }
        methodSetText(data);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexImage = 1;
                openCamera();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexImage = 2;
                openCamera();
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexImage = 3;
                openCamera();
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexImage = 4;
                openCamera();
            }
        });

    }
    private void methodSetText(List<String[]> data){
        switch (numberImage){
            case 1:
                txt1.setText(data.get(0)[1]);
                break;
            case 2:
                txt1.setText(data.get(0)[1]);
                txt2.setText(data.get(1)[1]);
                break;
            case 3:
                txt1.setText(data.get(0)[1]);
                txt2.setText(data.get(1)[1]);
                txt3.setText(data.get(2)[1]);
                break;
            case 4:
                txt1.setText(data.get(0)[1]);
                txt2.setText(data.get(1)[1]);
                txt3.setText(data.get(2)[1]);
                txt4.setText(data.get(3)[1]);
        }
    }
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap originalBitmap = (Bitmap) extras.get("data");

            // Điều chỉnh kích thước ảnh
            Bitmap resizedBitmap = resizeBitmap(originalBitmap, imageView1.getWidth(), imageView1.getHeight());
            uploadImageToServer(originalBitmap);
            // Hiển thị ảnh trong ImageView tương ứng
            switch (indexImage){
                case 1:
                    imageView1.setImageBitmap(resizedBitmap);
                    break;
                case 2:
                    imageView2.setImageBitmap(resizedBitmap);
                    break;
                case 3:
                    imageView3.setImageBitmap(resizedBitmap);
                    break;
                case 4:
                    imageView4.setImageBitmap(resizedBitmap);
                    break;
            }
        }
    }
    private void uploadImageToServer(Bitmap bitmap) {
        // Chuyển đổi Bitmap thành MultipartBody.Part
        MultipartBody.Part imagePart = convertBitmapToMultipart(bitmap);
        Log.d("imageeeeeeee",imagePart.toString());
        // Lấy loại ảnh (type) từ indexImage (có thể cần thêm logic để xác định loại ảnh)
        int type = getTypeFromIndex(indexImage);
        Log.d("typeppppppp",String.valueOf(type));
        // Gọi API để đẩy ảnh lên server
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);
        String token = "Bearer " + sharedPreferences.getString("accessToken","");
        UserDAO.getInstance().uploadImage(token,type, imagePart).enqueue(new Callback<LoadImageResponse>() {
            @Override
            public void onResponse(Call<LoadImageResponse> call, Response<LoadImageResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getStatus()==200){
                        Log.d("MessageLoadImage", response.body().getMessage());
                    }
                    else {
                        Log.d("ERRORRRRRRRRRRRRRR", "LoiLoiLoiLOi: ");
                    }
                }
                else {
                    Log.d("ERRORRRRRRRRRRRRRR", "No respond: ");
                    Log.d("ERRRRORRORORORORORO", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<LoadImageResponse> call, Throwable t) {
                Log.d("onFailure", "onFailure");
            }
        });
    }
    // Hàm chuyển đổi Bitmap thành MultipartBody.Part
    private MultipartBody.Part convertBitmapToMultipart(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageBytes);
        return MultipartBody.Part.createFormData("image", "image.png", requestBody);
    }

    // Hàm để xác định loại ảnh dựa trên indexImage (cần phải thay đổi phù hợp)
    private int getTypeFromIndex(int indexImage) {
        return Integer.parseInt(getDataNeedImage(index).get(indexImage-1)[0]);
    }

    // Hàm điều chỉnh kích thước ảnh
    private Bitmap resizeBitmap(Bitmap originalBitmap, int targetWidth, int targetHeight) {
        return Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true);
    }

    private void hideImage(int number){
        switch (number) {
            case 1:
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
                image4.setVisibility(View.GONE);
                break;
            case 2:
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.GONE);
                image4.setVisibility(View.GONE);
                break;
            case 3:
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.VISIBLE);
                image4.setVisibility(View.GONE);
                break;
            case 4:
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.VISIBLE);
                image4.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    private List<String[]> getDataNeedImage(int index){
        List<String[]> result = new ArrayList<>();
        switch (index){
            case 7:
                result.add(new String[]{"1","ID Card (front)"});
                result.add(new String[]{"2","ID Card (back)"});
                return result;
            case 8:
                result.add(new String[]{"3","Driver Portrait"});
                return result;
            case 9:
                result.add(new String[]{"4","Driver License (front)"});
                result.add(new String[]{"5","Driver License (back)"});
                return result;
            case 10:
                result.add(new String[]{"6","Criminal record"});
                return result;
            case 11:
                result.add(new String[]{"7","Compulsory accident insurance (front)"});
                result.add(new String[]{"8","Compulsory accident insurance (back)"});
                return result;
            case 12:
                result.add(new String[]{"9","Medical health check (page1)"});
                result.add(new String[]{"10","Medical health check (page2)"});
                result.add(new String[]{"11","Medical health check (page3)"});
                result.add(new String[]{"12","Medical health check (page4)"});
                return result;
            case 13:
                result.add(new String[]{"13","Vehicle certificate (front)"});
                result.add(new String[]{"14","Vehicle certificate (back)"});
                return result;
            case 14:
                result.add(new String[]{"15","Medical health check (front)"});
                result.add(new String[]{"16","Medical health check (back)"});
                result.add(new String[]{"17","Medical health check (left)"});
                result.add(new String[]{"18","Medical health check (right)"});                return result;
        }
        return null;
    }
}
