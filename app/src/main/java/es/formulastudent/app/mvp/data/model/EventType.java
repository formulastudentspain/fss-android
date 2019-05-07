package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum EventType implements Serializable {

        BRIEFING ("Briefing", 1),
        PRE_SCRUTINEERING ("Pre-Scrutineering", 2),
        PRACTICE_TRACK ("Practice Track", 3),
        SKIDPAD ("Skidpad", 4),
        ACCELERATION ("Acceleration", 5),
        AUTOCROSS ("Autocross", 6),
        ENDURANCE_EFFICIENCY ("Endurance/Efficiency", 7);


        private final String value;
        private final int code;

        EventType(String value, int code) {
                this.value = value;
                this.code = code;
        }

        public String getValue() {
                return value;
        }

        public int getCode() {
                return code;
        }

        @Override
        public String toString(){
                return getValue();
        }
}
