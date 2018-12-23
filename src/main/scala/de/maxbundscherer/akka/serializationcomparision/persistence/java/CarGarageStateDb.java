package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class CarGarageStateDb implements Serializable {

    CarDb[] cars;
    ComplexCarDb[] complexCars;

    public CarGarageStateDb(CarDb[] cars, ComplexCarDb[] complexCars) {
        this.cars = cars;
        this.complexCars = complexCars;
    }

    public CarDb[] getCars() {
        return cars;
    }

    public ComplexCarDb[] getComplexCars() {
        return complexCars;
    }

}