package com.example.weightpet0604;

public class Weight {
    private double weight;
    private String dateTime;

    public Weight(double weight, String dateTime) {
        this.weight = weight;
        this.dateTime = dateTime;
    }

    public double getWeight() {
        return weight;
    }

    public String getDateTime() {
        return dateTime;
    }
}
