package com.example.ridewizard.model.uploadImage;

import com.example.ridewizard.model.user.Data;

public class LoadImageResponse {
    private boolean success;
    private String message;
    private int status;
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
    public Data getData(){
        return data;
    }
}
