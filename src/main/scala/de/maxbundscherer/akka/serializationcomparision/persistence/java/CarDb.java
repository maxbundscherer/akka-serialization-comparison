package de.maxbundscherer.akka.serializationcomparision.persistence.java;

import java.io.Serializable;

public class CarDb implements Serializable {

    int id;
    int horsepower;
    String name;

    public CarDb(int id, int horsepower, String name) {
        this.id = id;
        this.horsepower = horsepower;
        this.name = name;
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

}