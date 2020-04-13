package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum EventType implements Serializable {

        BRIEFING ("Briefing control", 10001L,"DEV_DYNAMIC_EVENT_CONTROL"),
        PRE_SCRUTINEERING ("Pre-Scrutineering", 10002L, "DEV_DYNAMIC_EVENT_CONTROL"),

        PRACTICE_TRACK ("Practice Track", 10011L, "DEV_DYNAMIC_EVENT_CONTROL"),
        SKIDPAD ("Skidpad", 10012L, "DEV_DYNAMIC_EVENT_CONTROL"),
        ACCELERATION ("Acceleration", 10013L, "DEV_DYNAMIC_EVENT_CONTROL"),
        AUTOCROSS ("Autocross", 10014L, "DEV_DYNAMIC_EVENT_CONTROL"),
        ENDURANCE_EFFICIENCY ("Endurance", 10015L, "DEV_DYNAMIC_EVENT_CONTROL");


        private final String name;
        private final Long drawerItemID;
        private final String firebaseTable;

        EventType(String name, Long drawerItemID, String firebaseTable) {
                this.name = name;
                this.drawerItemID = drawerItemID;
                this.firebaseTable = firebaseTable;
        }

        public String getName() {
                return name;
        }

        public Long getDrawerItemID() {
                return drawerItemID;
        }

        public String getFirebaseTable() {
                return firebaseTable;
        }

}
