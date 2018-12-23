package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class ComplexCarDb implements Serializable  {

    int id;
    int horsepower;
    String name;
    float fuelConsumption;
    boolean dieselEngine;
    boolean seatAdjustment;
    double fuelTank;
    double brakingDistance;
    String notes;

    public ComplexCarDb(int id, int horsepower, String name, float fuelConsumption, boolean dieselEngine, boolean seatAdjustment, double fuelTank, double brakingDistance, String notes) {
        this.id = id;
        this.horsepower = horsepower;
        this.name = name;
        this.fuelConsumption = fuelConsumption;
        this.dieselEngine = dieselEngine;
        this.seatAdjustment = seatAdjustment;
        this.fuelTank = fuelTank;
        this.brakingDistance = brakingDistance;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public String getName() {
        return name;
    }

    public float getFuelConsumption() {
        return fuelConsumption;
    }

    public boolean isDieselEngine() {
        return dieselEngine;
    }

    public boolean isSeatAdjustment() {
        return seatAdjustment;
    }

    public double getFuelTank() {
        return fuelTank;
    }

    public double getBrakingDistance() {
        return brakingDistance;
    }

    public String getNotes() {
        return notes;
    }

}