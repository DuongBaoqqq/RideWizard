package com.example.ridewizard.model.user;

import java.util.List;

public class User{
	private Object googleId;
	private String lastName;
	private Object address;
	private int emailVerified;
	private List<RoleItem> role;
	private Object facebookId;
	private String fullName;
	private Object avatar;
	private String phNo;
	private Object firstName;
	private String createdAt;
	private int phoneVerified;
	private boolean passwordDefault;
	private Object dob;
	private int id;
	private String email;
	private String status;
	private String updatedAt;

	public Object getGoogleId(){
		return googleId;
	}

	public String getLastName(){
		return lastName;
	}

	public Object getAddress(){
		return address;
	}

	public int getEmailVerified(){
		return emailVerified;
	}

	public List<RoleItem> getRole(){
		return role;
	}

	public Object getFacebookId(){
		return facebookId;
	}

	public String getFullName(){
		return fullName;
	}

	public Object getAvatar(){
		return avatar;
	}

	public String getPhNo(){
		return phNo;
	}

	public Object getFirstName(){
		return firstName;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getPhoneVerified(){
		return phoneVerified;
	}

	public boolean isPasswordDefault(){
		return passwordDefault;
	}

	public Object getDob(){
		return dob;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getStatus(){
		return status;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}