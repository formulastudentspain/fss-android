package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Team implements Serializable {


    //Database constants
    public static final String NAME = "name";
    public static final String CAR = "car";
    public static final String CAR_NUMBER = "car.number";
    public static final String CAR_TYPE = "car.type";

    private String ID;
    private String name;
    private Car car;

    public Team(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Team(DocumentSnapshot object){
        this.ID = object.getReference().getId();
        this.name = object.getString(Team.NAME);

        Map<String, Object> carMap = (Map<String, Object>) object.getData().get(Team.CAR);
        Car car = new Car();
        car.setNumber((Long)carMap.get("number"));
        car.setType((String)carMap.get("type"));
        this.car = car;
    }

    public Map<String, Object> toDocumentData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(Team.NAME, this.getName());
        docData.put(Team.CAR, this.getCar());

        return docData;
    }


    public Team() {
        this.ID = UUID.randomUUID().toString();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
