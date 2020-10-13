package com.example.sepsisdetector;

public class Hospital {

    String name;
    String address;
    String email;
    String password;
    Patient[] patients;


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Patient[] getPatients() {
        return patients;
    }

    public Hospital(String name, String address, String email, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
    }


}
