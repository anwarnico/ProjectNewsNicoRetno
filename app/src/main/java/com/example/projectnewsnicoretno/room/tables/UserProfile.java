package com.example.projectnewsnicoretno.room.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"title"}, unique = true)})
public class UserProfile {
    @PrimaryKey(autoGenerate = false)
    public int id;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "avatar")
    public String avatar;

    @ColumnInfo(name = "last_login")
    public String lastLogin;

    @ColumnInfo(name = "last_activity")
    public String lastActivity;

    @ColumnInfo(name = "date_created")
    public String dateCreated;

    @ColumnInfo(name = "company_id")
    public String companyId;
}
