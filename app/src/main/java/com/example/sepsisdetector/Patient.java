package com.example.sepsisdetector;

public class Patient {

    String patientId;
    String patientName;
    String number;

    public Patient() {
    }

    public Patient(String patientId, String patientName,String number) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.number = number;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getNumber() {
        return number;
    }
}
