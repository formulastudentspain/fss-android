package es.formulastudent.app.mvp.data.model;

import org.apache.poi.ss.formula.functions.Even;

import java.io.Serializable;

public enum EventType implements Serializable {

        BRIEFING ("Briefing control", 10001L,"DEV_DYNAMIC_EVENT_CONTROL"),
        PRE_SCRUTINEERING ("Pre-Scrutineering", 10002L, "DEV_DYNAMIC_EVENT_CONTROL"),
        PRACTICE_TRACK ("Practice Track control", 10011L, "DEV_DYNAMIC_EVENT_CONTROL"),
        SKIDPAD ("Skidpad control", 10012L, "DEV_DYNAMIC_EVENT_CONTROL"),
        ACCELERATION ("Acceleration control", 10013L, "DEV_DYNAMIC_EVENT_CONTROL"),
        AUTOCROSS ("Autocross control", 10014L, "DEV_DYNAMIC_EVENT_CONTROL"),
        ENDURANCE_EFFICIENCY ("Endurance control", 10015L, "DEV_DYNAMIC_EVENT_CONTROL");


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
