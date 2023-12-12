package com.example.ridewizard.model.verify;

public class Verify {
	private Data data;
	private boolean success;
	private String message;
	private Error error;
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

	public Error getError(){
		return error;
	}

	public int getStatus(){
		return status;
	}
}
