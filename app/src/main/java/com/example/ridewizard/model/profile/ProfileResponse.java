package com.example.ridewizard.model.profile;

public class ProfileResponse {
	private Data data;
	private boolean success;
	private int status;

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public int getStatus(){
		return status;
	}
}
