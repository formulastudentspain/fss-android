package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public class Car implements Serializable {

    public static final String CAR_TYPE_COMBUSTION = "Combustion";
    public static final String CAR_TYPE_ELECTRIC = "Electric";
    public static final String CAR_TYPE_AUTONOMOUS_ELECTRIC = "Driverless Electric";
    public static final String CAR_TYPE_AUTONOMOUS_COMBUSTION = "Driverless Combustion";

    private String type;
    private Long number;

    public Car() { }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
