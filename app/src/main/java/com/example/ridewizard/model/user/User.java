package com.example.ridewizard.model.user;

import java.util.List;

public class User{
	private Object lastName;
	private Object address;
	private int emailVerified;
	private List<RoleItem> role;
	private String fullName;
	private String avatar;
	private String driverStatus;
	private String phNo;
	private String firstName;
	private String createdAt;
	private int phoneVerified;
	private boolean passwordDefault;
	private Object dob;
	private int id;
	private String email;
	private String status;
	private String updatedAt;

	public Object getLastName(){
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

	public String getFullName(){
		return fullName;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getDriverStatus(){
		return driverStatus;
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