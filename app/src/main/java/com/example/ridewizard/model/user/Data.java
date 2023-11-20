package com.example.ridewizard.model.user;

public class Data{
	private String accessToken;
	private User user;
	private String refreshToken;

	public String getAccessToken(){
		return accessToken;
	}

	public User getUser(){
		return user;
	}

	public String getRefreshToken(){
		return refreshToken;
	}
}
