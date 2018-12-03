package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class UpdateCarEvtDb implements Serializable {

    CarDb value;

    public UpdateCarEvtDb(CarDb value) {
        this.value = value;
    }

    public CarDb getValue() {
        return value;
    }

}