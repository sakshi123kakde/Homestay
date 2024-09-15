package com.example.page1;

public class MostVisitedModel {
    private String title;
    private String imageUrl;
    private String address;
    private String how;

    public MostVisitedModel() {
        // Default constructor required for calls to DataSnapshot.getValue(MostVisitedModel.class)
    }

    public MostVisitedModel(String title, String imageUrl, String address, String how) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.how = how;
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

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }
}

