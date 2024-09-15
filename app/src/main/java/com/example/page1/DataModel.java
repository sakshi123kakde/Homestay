package com.example.page1;

public class DataModel {
    private String title;
    private String id;
    private String address;
    private String howToReach;
    private String tariff;
    private String facility;
    private String food;
    private String activities;
    private String localAttraction;
    private String imageUrl;

    public DataModel() {
        // Default constructor required for Firebase
    }

    public DataModel(String title, String address, String howToReach, String tariff, String facility, String food, String activities, String localAttraction, String imageUrl) {
        this.title = title;
        this.address = address;
        this.howToReach = howToReach;
        this.tariff = tariff;
        this.facility = facility;
        this.food = food;
        this.activities = activities;
        this.localAttraction = localAttraction;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getHowToReach() {
        return howToReach;
    }

    public String getTariff() {
        return tariff;
    }

    public String getFacility() {
        return facility;
    }

    public String getFood() {
        return food;
    }

    public String getActivities() {
        return activities;
    }

    public String getLocalAttraction() {
        return localAttraction;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHowToReach(String howToReach) {
        this.howToReach = howToReach;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public void setLocalAttraction(String localAttraction) {
        this.localAttraction = localAttraction;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
