package com.example.page1;

import java.util.Date;

public class Booking {
    private String id;  // Add this line
    private String name;
    private String email;
    private String phone;
    private String city;
    private String spinnerData1;
    private String spinnerData2;
    private String spinnerData3;
    private String title;
    private Date checkInDate;
    private Date checkOutDate;
    private String status;  // Add this line
    private String userId;  // Add this line

    public Booking() {
        // Default constructor required for calls to DataSnapshot.getValue(Booking.class)
    }

    public Booking(String name, String email, String phone, String city, String spinnerData1, String spinnerData2, String spinnerData3, Date checkInDate, Date checkOutDate, String title, String status, String userId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.spinnerData1 = spinnerData1;
        this.spinnerData2 = spinnerData2;
        this.spinnerData3 = spinnerData3;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.title = title;
        this.status = status;  // Add this line
        this.userId = userId;  // Add this line
    }

    public String getId() {  // Add this method
        return id;
    }

    public void setId(String id) {  // Add this method
        this.id = id;
    }

    // Other getters and setters

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getSpinnerData1() {
        return spinnerData1;
    }

    public String getSpinnerData2() {
        return spinnerData2;
    }

    public String getSpinnerData3() {
        return spinnerData3;
    }

    public String getTitle() {
        return title;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public String getStatus() {  // Add this method
        return status;
    }

    public void setStatus(String status) {  // Add this method
        this.status = status;
    }

    public String getUserId() {  // Add this method
        return userId;
    }

    public void setUserId(String userId) {  // Add this method
        this.userId = userId;
    }
}
