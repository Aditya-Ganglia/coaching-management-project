package com.coaching.institute.model;

public class ContactDetails {
    private String phone;
    private String address;

    // Constructors
    public ContactDetails() {}

    public ContactDetails(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
