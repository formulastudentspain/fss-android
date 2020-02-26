package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

import es.formulastudent.app.R;

public enum ConeControlEvent implements Serializable {

        ENDURANCE ("Endurance",R.string.cc_endurance_title, EventType.ENDURANCE_EFFICIENCY,80021L,"DEV_CC_ENDURANCE"),

        AUTOCROSS ("Autocross", R.string.cc_autocross_title, EventType.AUTOCROSS,80022L,"DEV_CC_AUTOCROSS");

        private final String name;
        private final Integer activityTitle;
        private final Long drawerItemID;
        private final String firebaseTable;
        private final EventType eventType;

        ConeControlEvent(String name, Integer activityTitle, EventType eventType, Long drawerItemID, String firebaseTable) {
                this.name = name;
                this.activityTitle = activityTitle;
                this.drawerItemID = drawerItemID;
                this.firebaseTable = firebaseTable;
                this.eventType = eventType;
        }

        public Integer getActivityTitle() {
                return activityTitle;
        }

        public Long getDrawerItemID() {
                return drawerItemID;
        }

        public String getFirebaseTable() {
                return firebaseTable;
        }

        public EventType getEventType() {
                return eventType;
        }

        public String getName() {
                return name;
        }
}
