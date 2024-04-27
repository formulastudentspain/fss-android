package code.formulastudentspain.app.mvp.data.model;

import java.io.Serializable;

import code.formulastudentspain.app.R;

public enum RaceControlEvent implements Serializable {

        ENDURANCE (R.string.rc_endurance_title, EventType.ENDURANCE_EFFICIENCY,10021L,"DEV_RC_ENDURANCE", ConeControlEvent.ENDURANCE),
        AUTOCROSS (R.string.rc_autocross_title, EventType.AUTOCROSS,10022L,"DEV_RC_AUTOCROSS", ConeControlEvent.AUTOCROSS),
        SKIDPAD (R.string.rc_skidpad_title, EventType.SKIDPAD,10023L,"DEV_RC_SKIDPAD", ConeControlEvent.SKIDPAD);


        private final Integer activityTitle;
        private final Long drawerItemID;
        private final String firebaseTable;
        private final EventType eventType;
        private final ConeControlEvent coneControlEvent;

        RaceControlEvent(Integer activityTitle, EventType eventType, Long drawerItemID, String firebaseTable, ConeControlEvent coneControlEvent) {
                this.activityTitle = activityTitle;
                this.drawerItemID = drawerItemID;
                this.firebaseTable = firebaseTable;
                this.eventType = eventType;
                this.coneControlEvent = coneControlEvent;
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

        public ConeControlEvent getConeControlEvent() {
                return coneControlEvent;
        }
}
