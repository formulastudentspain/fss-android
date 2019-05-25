package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Team implements Serializable {

    public static String COLLECTION_ID = "TEAM";

    //Database constants
    public static final String NAME = "name";
    public static final String CAR = "car";

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
    }

    public Team() { }

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
