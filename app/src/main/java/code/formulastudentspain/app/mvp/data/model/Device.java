package code.formulastudentspain.app.mvp.data.model;

import java.io.Serializable;

public enum Device implements Serializable {

    WALKIE("Walkie"),
    CELLPHONE("Cellphone");

    private final String name;

    Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
