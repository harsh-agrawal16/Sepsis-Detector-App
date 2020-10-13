package com.example.sepsisdetector;

public class Patient {

    String name;
    String address;
    Integer number;
    String hospitalId;

    public Patient(String name, String address, Integer number, String hospitalId) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getNumber() {
        return number;
    }

    public String getHospitalId() {
        return hospitalId;
    }
}