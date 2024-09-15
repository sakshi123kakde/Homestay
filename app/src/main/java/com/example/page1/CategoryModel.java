package com.example.page1;

public class CategoryModel {
    private String title;
    private String imageUrl;
    private String address;
    private String description;

    public CategoryModel() {
        // Default constructor required for calls to DataSnapshot.getValue(MostVisitedModel.class)
    }

    public CategoryModel(String title, String imageUrl, String address, String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

