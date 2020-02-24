package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import es.formulastudent.app.R;

public enum RaceControlEvent implements Serializable {

        ENDURANCE (R.string.rc_endurance_title, EventType.ENDURANCE_EFFICIENCY,10021L,"DEV_RC_ENDURANCE", new HashMap<String, Integer>()
        {{
                put(RaceControlRegister.RACE_TYPE_ELECTRIC, R.string.rc_endurance_title);
                put(RaceControlRegister.RACE_TYPE_COMBUSTION, R.string.rc_endurance_title);
                put(RaceControlRegister.RACE_TYPE_FINAL, R.string.rc_endurance_title);
        }}),

        AUTOCROSS (R.string.rc_autocross_title, EventType.AUTOCROSS,10022L,"DEV_RC_AUTOCROSS", new HashMap<>());


        private final Integer activityTitle;
        private final Long drawerItemID;
        private final String firebaseTable;
        private final EventType eventType;
        private final Map<String, Integer> raceTypes;

        RaceControlEvent(Integer activityTitle, EventType eventType, Long drawerItemID, String firebaseTable, Map<String, Integer> raceTypes) {
                this.activityTitle = activityTitle;
                this.drawerItemID = drawerItemID;
                this.firebaseTable = firebaseTable;
                this.raceTypes = raceTypes;
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

        public Map<String, Integer> getRaceTypes() {
                return raceTypes;
        }

        public EventType getEventType() {
                return eventType;
        }
}
