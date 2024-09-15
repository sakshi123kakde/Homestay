package com.example.page1;


public class SecondModel {
    private String title;
    private String address;
    private String imageUrl;
    private String activities;
    private String facility;
    private String food;
    private String how;
    private String local;
    private String tariff;
    private String description;

    public SecondModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SecondModel(String title, String address, String imageUrl, String activities, String facility, String food, String how, String local, String tariff, String description) {
        this.title = title;
        this.address = address;
        this.imageUrl = imageUrl;
        this.activities = activities;
        this.facility = facility;
        this.food = food;
        this.how = how;
        this.local = local;
        this.tariff = tariff;
        this.description=description;
    }

    // Getter and setter methods for all fields
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }
}
