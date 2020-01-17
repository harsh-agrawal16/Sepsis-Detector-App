package com.example.sepsisdetector;

public class Hospitals {

    String hospitalId;
    String hospitalEmail;

    public Hospitals(){

    }

    public Hospitals(String hospitalId, String hospitalEmail) {
        this.hospitalId = hospitalId;
        this.hospitalEmail = hospitalEmail;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }
}
