package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum EventType implements Serializable {

        BRIEFING ("Briefing control", 10001L,"EVENT_CONTROL_BRIEFING"),
        PRE_SCRUTINEERING ("Pre-Scrutineering", 10002L, "DEV_EVENT_CONTROL_PRESCRUTI"),
        PRACTICE_TRACK ("Practice Track control", 10011L, "DEV_EVENT_CONTROL_PRACTICE_TRACK"),
        SKIDPAD ("Skidpad control", 10012L, "DEV_EVENT_CONTROL_SKIDPAD"),
        ACCELERATION ("Acceleration control", 10013L, "DEV_EVENT_CONTROL_ACCELERATION"),
        AUTOCROSS ("Autocross control", 10014L, "DEV_EVENT_CONTROL_AUTOCROSS"),
        ENDURANCE_EFFICIENCY ("Endurance control", 10015L, "DEV_EVENT_CONTROL_ENDURANCE");


        private final String activityTitle;
        private final Long drawerItemID;
        private final String firebaseTable;

        EventType(String value, Long drawerItemID, String firebaseTable) {
                this.activityTitle = value;
                this.drawerItemID = drawerItemID;
                this.firebaseTable = firebaseTable;
        }

        public String getActivityTitle() {
                return activityTitle;
        }

        public Long getDrawerItemID() {
                return drawerItemID;
        }

        public String getFirebaseTable() {
                return firebaseTable;
        }

}
