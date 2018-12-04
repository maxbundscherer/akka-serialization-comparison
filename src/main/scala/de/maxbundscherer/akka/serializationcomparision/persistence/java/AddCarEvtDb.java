package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class AddCarEvtDb implements Serializable {

    CarDb value;

    public AddCarEvtDb(CarDb value) {
        this.value = value;
    }

    public CarDb getValue() {
        return value;
    }

}