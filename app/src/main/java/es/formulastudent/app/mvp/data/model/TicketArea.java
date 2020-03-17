package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TicketArea implements Serializable {

    PRE_SCRUTINEERING(
        "Pre-Scrutineering",
        "CURRENT_TICKET_TURN_PS"
    ),

    ACCUMULATION_INSPECTION(
        "Accumulation Inspection",
        "CURRENT_TICKET_TURN_AI"
    ),

    ELECTRICAL_INSPECTION(
        "Electrical Inspection",
        "CURRENT_TICKET_TURN_EI"
    ),

    MECHANICAL_INSPECTION(
        "Mechanical Inspection",
        "CURRENT_TICKET_TURN_MI"
    ),

    TILT_TABLE_TEST(
        "Tilt Table Test",
        "CURRENT_TICKET_TURN_TTT"
    ),

    NOISE_TEST(
        "Noise Test",
        "CURRENT_TICKET_TURN_NT"
    ),

    RAIN_TEST(
        "Rain Test",
        "CURRENT_TICKET_TURN_RT"
    ),

    BRAKE_TEST(
        "Brake Test",
        "CURRENT_TICKET_TURN_BT"
    );


    private final String name;
    private final String currentTurnKey;

    TicketArea(String name, String currentTurnKey) {
        this.name = name;
        this.currentTurnKey = currentTurnKey;
    }


    public String getName() {
        return name;
    }

    public String getCurrentTurnKey() {
        return currentTurnKey;
    }

    public static TicketArea getByName(String name) {
        if (TicketArea.PRE_SCRUTINEERING.getName().equals(name)) {
            return PRE_SCRUTINEERING;
        } else if (TicketArea.ACCUMULATION_INSPECTION.getName().equals(name)) {
            return ACCUMULATION_INSPECTION;
        } else if (TicketArea.ELECTRICAL_INSPECTION.getName().equals(name)) {
            return ELECTRICAL_INSPECTION;
        } else if (TicketArea.MECHANICAL_INSPECTION.getName().equals(name)) {
            return MECHANICAL_INSPECTION;
        } else if (TicketArea.TILT_TABLE_TEST.getName().equals(name)) {
            return TILT_TABLE_TEST;
        } else if (TicketArea.NOISE_TEST.getName().equals(name)) {
            return NOISE_TEST;
        } else if (TicketArea.RAIN_TEST.getName().equals(name)) {
            return RAIN_TEST;
        } else if (TicketArea.BRAKE_TEST.getName().equals(name)) {
            return BRAKE_TEST;
        } else {
            return null;
        }
    }

    public static List<TicketArea> getTestsByCarType(String carType) {

        List<TicketArea> testList = new ArrayList<>();
        switch (carType) {
            case Car.CAR_TYPE_ELECTRIC:
                testList.addAll(Arrays.asList(
                    PRE_SCRUTINEERING,
                    ACCUMULATION_INSPECTION,
                    ELECTRICAL_INSPECTION,
                    MECHANICAL_INSPECTION,
                    TILT_TABLE_TEST,
                    RAIN_TEST,
                    BRAKE_TEST
                ));
                break;

            case Car.CAR_TYPE_COMBUSTION:
                testList.addAll(Arrays.asList(
                    PRE_SCRUTINEERING,
                    MECHANICAL_INSPECTION,
                    TILT_TABLE_TEST,
                    NOISE_TEST,
                    BRAKE_TEST
                ));
                break;

            case Car.CAR_TYPE_AUTONOMOUS_ELECTRIC:
                testList.addAll(Arrays.asList(
                    PRE_SCRUTINEERING,
                    ACCUMULATION_INSPECTION,
                    ELECTRICAL_INSPECTION,
                    MECHANICAL_INSPECTION,
                    TILT_TABLE_TEST,
                    RAIN_TEST,
                    BRAKE_TEST
                ));
                break;

            case Car.CAR_TYPE_AUTONOMOUS_COMBUSTION:
                testList.addAll(Arrays.asList(
                    PRE_SCRUTINEERING,
                    MECHANICAL_INSPECTION,
                    TILT_TABLE_TEST,
                    NOISE_TEST,
                    BRAKE_TEST
                ));
                break;
        }
        return testList;
    }
}
