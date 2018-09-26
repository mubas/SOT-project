package com.arbab.sot.models;

public class Student {

    private int id;
    private String name;
    private String phoneNumber;
    private int assignedApartment;
    public Student(int id, String name, String phoneNumber, int assignedApartment) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.assignedApartment = assignedApartment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAssignedApartment() {
        return assignedApartment;
    }

    public void setAssignedApartment(int assignedApartment) {
        this.assignedApartment = assignedApartment;
    }
}
