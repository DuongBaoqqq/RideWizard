package com.example.ridewizard.model.uploadavatar;

public class AvatarResponse{
	private Data data;
	private boolean success;
	private String message;
	private String error;
	private int status;

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public String getError(){
		return error;
	}

	public int getStatus(){
		return status;
	}
}
