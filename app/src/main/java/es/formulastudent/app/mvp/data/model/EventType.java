package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum EventType implements Serializable {

        BRIEFING ("Briefing", 1,""),
        PRE_SCRUTINEERING ("Pre-Scrutineering", 2, ""),
        PRACTICE_TRACK ("Practice Track", 3, ""),
        SKIDPAD ("Skidpad", 4, ""),
        ACCELERATION ("Acceleration", 5, "DEV_EVENT_CONTROL_ACCELERATION"),
        AUTOCROSS ("Autocross", 6, ""),
        ENDURANCE_EFFICIENCY ("Endurance/Efficiency", 7, "DEV_EVENT_CONTROL_ENDURANCE");


        private final String value;
        private final int code;
        private final String firebaseTable;

        EventType(String value, int code, String firebaseTable) {
                this.value = value;
                this.code = code;
                this.firebaseTable = firebaseTable;
        }

        public String getValue() {
                return value;
        }

        public int getCode() {
                return code;
        }

        public String getFirebaseTable() {
                return firebaseTable;
        }

        @Override
        public String toString(){
                return getValue();
        }
}
