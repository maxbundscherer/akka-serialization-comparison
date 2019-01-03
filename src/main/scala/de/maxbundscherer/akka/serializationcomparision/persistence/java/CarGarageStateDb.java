package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;
import java.util.Map;

public class CarGarageStateDb implements Serializable {

    Map<Integer, CarDb> cars;
    Map<Integer, ComplexCarDb> complexCars;

    public CarGarageStateDb(Map<Integer, CarDb> cars, Map<Integer, ComplexCarDb> complexCars) {
        this.cars = cars;
        this.complexCars = complexCars;
    }

    public Map<Integer, CarDb> getCars() {
        return cars;
    }

    public Map<Integer, ComplexCarDb> getComplexCars() {
        return complexCars;
    }

}