package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class UpdateComplexCarEvtDb implements Serializable {

    ComplexCarDb value;

    public UpdateComplexCarEvtDb(ComplexCarDb value) {
        this.value = value;
    }

    public ComplexCarDb getValue() {
        return value;
    }

}