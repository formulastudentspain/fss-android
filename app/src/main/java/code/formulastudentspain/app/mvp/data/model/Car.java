package code.formulastudentspain.app.mvp.data.model;

import java.io.Serializable;

public class Car implements Serializable, Cloneable {

    public static final String CAR_TYPE_COMBUSTION = "Combustion";
    public static final String CAR_TYPE_ELECTRIC = "Electric";
    public static final String CAR_TYPE_AUTONOMOUS_ELECTRIC = "Driverless Electric";
    public static final String CAR_TYPE_AUTONOMOUS_COMBUSTION = "Driverless Combustion";

    private String type;
    private Long number;

    public Car() { }


    @Override
    public Car clone() {
        final Car clone;
        try {
            clone = (Car) super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("superclass messed up", ex);
        }
        return clone;
    }

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
