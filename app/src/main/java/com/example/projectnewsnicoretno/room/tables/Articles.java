package com.example.projectnewsnicoretno.room.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projectnewsnicoretno.model.Source;

@Entity
public class Articles {
	@PrimaryKey(autoGenerate = true)
	public int id;

	@ColumnInfo(name = "publishedAt")
	public String publishedAt;

	@ColumnInfo(name = "author")
	public String author;

	@ColumnInfo(name = "urlToImage")
	public String urlToImage;

	@ColumnInfo(name = "description")
	public String description;
	@ColumnInfo(name = "title")
	public String title;

	@ColumnInfo(name = "url")
	public String url;

	@ColumnInfo(name = "content")
	public String content;

	@ColumnInfo(name = "type")
	public String type;
}
