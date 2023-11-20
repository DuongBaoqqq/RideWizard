package com.example.ridewizard.model.profile;

public class User{
	private String lastName;
	private String address;
	private int emailVerified;
	private String oTPSecret;
	private String fullName;
	private String avatar;
	private String phNo;
	private String firstName;
	private String createdAt;
	private int phoneVerified;
	private String dob;
	private int id;
	private String email;
	private String updatedAt;

	public String getLastName(){
		return lastName;
	}

	public String getAddress(){
		return address;
	}

	public int getEmailVerified(){
		return emailVerified;
	}

	public String getOTPSecret(){
		return oTPSecret;
	}

	public String getFullName(){
		return fullName;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getPhNo(){
		return phNo;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getPhoneVerified(){
		return phoneVerified;
	}

	public String getDob(){
		return dob;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}
