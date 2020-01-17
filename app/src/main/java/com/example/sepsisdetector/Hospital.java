package com.example.sepsisdetector;

public class Hospital {

    String id;
    String email;

    public Hospital(){

    }

    public Hospital(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
