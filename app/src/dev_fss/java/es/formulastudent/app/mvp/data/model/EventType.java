package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum EventType implements Serializable {

        BRIEFING ("Briefing control", 1L,""),
        PRE_SCRUTINEERING ("Pre-Scrutineering", 2L, ""),
        PRACTICE_TRACK ("Practice Track control", 3L, ""),
        SKIDPAD ("Skidpad control", 4L, ""),
        ACCELERATION ("Acceleration control", 10013L, "DEV_EVENT_CONTROL_ACCELERATION"),
        AUTOCROSS ("Autocross control", 6L, ""),
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
