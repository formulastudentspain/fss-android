package code.formulastudentspain.app.mvp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ScrutineeringTest implements Serializable {


        PRE_SCRUTINEERING(
                "Pre-Scrutineering",
                "PS"
        ),

        ACCUMULATION_INSPECTION(
                "Accumulation Inspection",
                "AI"
        ),

        ELECTRICAL_INSPECTION(
                "Electrical Inspection",
                "EI"
        ),

        MECHANICAL_INSPECTION(
                "Mechanical Inspection",
                "MI"
        ),

        TILT_TABLE_TEST(
                "Tilt Table Test",
                "TTT"
        ),

        NOISE_TEST(
                "Noise Test",
                "NT"
        ),

        RAIN_TEST(
                "Rain Test",
                "RT"
        ),

        BRAKE_TEST(
                "Brake Test",
                "BT"
        );


        private final String name;
        private final String acronym;

        ScrutineeringTest(String name, String acronym) {
                this.name = name;
                this.acronym = acronym;
        }


        public String getName() {
                return name;
        }

        public String getAcronym() {
                return acronym;
        }

        public static List<ScrutineeringTest> getTestsByCarType(String carType) {

                List<ScrutineeringTest> testList = new ArrayList<>();
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
