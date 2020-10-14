package com.example.sepsisdetector;

public class Hospital {

    private String name;
    private String address;
    private String email;
    private String password;
    private Patient[] patients;


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

    public Hospital(String email, String password){
        this.email = email;
        this.password = password;
    }
}
