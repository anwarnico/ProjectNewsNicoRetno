package com.example.projectnewsnicoretno.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("data")
	private UserData userData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	@SerializedName("token")
	private String token;

	public void setData(UserData userData){
		this.userData = userData;
	}

	public UserData getData(){
		return userData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}