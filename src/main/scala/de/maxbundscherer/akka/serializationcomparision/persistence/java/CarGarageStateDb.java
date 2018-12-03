package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class CarGarageStateDb implements Serializable {

    CarDb[] cars;

    public CarGarageStateDb(CarDb[] cars) {
        this.cars = cars;
    }

    public CarDb[] getCars() {
        return cars;
    }

}