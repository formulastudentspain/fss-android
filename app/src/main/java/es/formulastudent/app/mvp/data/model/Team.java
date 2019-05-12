package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;

public class Team implements Serializable {

    public static String COLLECTION_ID = "TEAM";

    //Database constants
    public static final String NAME = "name";

    private String ID;
    private String name;

    public Team(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Team(QueryDocumentSnapshot object){
        this.ID = object.getReference().getId();
        this.name = object.getString(Team.NAME);
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
}
