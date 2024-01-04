package com.example.tugaspresentasi.data.models;

import android.net.Uri;

public class Book {
    private int id;
    private String title;
    private String author;
    private String description;
    private Uri coverImageUri;
    private int userId;

    public Book(int id, String title, String author, String description, Uri coverImageUri) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverImageUri = coverImageUri;
    }

    public Book() {

    }


    public int getId() { // And this method
        return id;
    }

    public void setId(int id) { // And this method
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getCoverImageUri() {
        return coverImageUri;
    }

    public void setCoverImageUri(Uri coverImageUri) {
        this.coverImageUri = coverImageUri;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }
}
