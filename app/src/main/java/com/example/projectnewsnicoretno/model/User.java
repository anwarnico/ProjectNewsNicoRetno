package com.example.projectnewsnicoretno.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("X-API-Key")
    @Expose
    private String XAPIKey;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public String getXAPIKey() {
        return XAPIKey;
    }

    public void setXAPIKey(String XAPIKey) {
        this.XAPIKey = XAPIKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
