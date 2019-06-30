package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum EventType implements Serializable {

        BRIEFING ("Briefing control", 10001L,"FSS_EVENT_CONTROL_BRIEFING"),
        PRE_SCRUTINEERING ("Pre-Scrutineering", 2L, ""),
        PRACTICE_TRACK ("Practice Track control", 10011L, "FSS_EVENT_CONTROL_PRACTICE_TRACK"),
        SKIDPAD ("Skidpad control", 10012L, "FSS_EVENT_CONTROL_SKIDPAD"),
        ACCELERATION ("Acceleration control", 10013L, "FSS_EVENT_CONTROL_ACCELERATION"),
        AUTOCROSS ("Autocross control", 10014L, "FSS_EVENT_CONTROL_AUTOCROSS"),
        ENDURANCE_EFFICIENCY ("Endurance control", 10015L, "FSS_EVENT_CONTROL_ENDURANCE");
        

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
