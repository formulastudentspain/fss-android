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
    public static final String CARS = "cars";

    private String ID;
    private String name;
    private List<Car> cars;

    public Team(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Team(DocumentSnapshot object){
        this.ID = object.getReference().getId();
        this.name = object.getString(Team.NAME);

        List<Map<String, Object>> cars = (ArrayList) object.getData().get(Team.CARS);
        this.cars = new ArrayList<>();
        for(Map<String, Object> carMap: cars){
            Car car = new Car();
            car.setNumber((Long)carMap.get("number"));
            car.setType((String)carMap.get("type"));
            this.cars.add(car);
        }
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
