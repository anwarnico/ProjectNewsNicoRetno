package com.example.projectnewsnicoretno.model;

import com.google.gson.annotations.SerializedName;

public class UserData {

	@SerializedName("remember_exp")
	private String rememberExp;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("last_login")
	private String lastLogin;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("oauth_uid")
	private String oauthUid;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("ip_address")
	private String ipAddress;

	@SerializedName("forgot_exp")
	private String forgotExp;

	@SerializedName("remember_time")
	private String rememberTime;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("last_activity")
	private String lastActivity;

	@SerializedName("top_secret")
	private String topSecret;

	@SerializedName("oauth_provider")
	private String oauthProvider;

	@SerializedName("id")
	private String id;

	@SerializedName("banned")
	private String banned;

	@SerializedName("verification_code")
	private String verificationCode;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public String getRememberExp() {
		return rememberExp;
	}

	public void setRememberExp(String rememberExp) {
		this.rememberExp = rememberExp;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getOauthUid() {
		return oauthUid;
	}

	public void setOauthUid(String oauthUid) {
		this.oauthUid = oauthUid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getForgotExp() {
		return forgotExp;
	}

	public void setForgotExp(String forgotExp) {
		this.forgotExp = forgotExp;
	}

	public String getRememberTime() {
		return rememberTime;
	}

	public void setRememberTime(String rememberTime) {
		this.rememberTime = rememberTime;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
	}

	public String getTopSecret() {
		return topSecret;
	}

	public void setTopSecret(String topSecret) {
		this.topSecret = topSecret;
	}

	public String getOauthProvider() {
		return oauthProvider;
	}

	public void setOauthProvider(String oauthProvider) {
		this.oauthProvider = oauthProvider;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBanned() {
		return banned;
	}

	public void setBanned(String banned) {
		this.banned = banned;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}