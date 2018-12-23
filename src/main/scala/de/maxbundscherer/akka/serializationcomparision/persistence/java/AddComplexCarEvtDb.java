package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class AddComplexCarEvtDb implements Serializable {

    ComplexCarDb value;

    public AddComplexCarEvtDb(ComplexCarDb value) {
        this.value = value;
    }

    public ComplexCarDb getValue() {
        return value;
    }

}