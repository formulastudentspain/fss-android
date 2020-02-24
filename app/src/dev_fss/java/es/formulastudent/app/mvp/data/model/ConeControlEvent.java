package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import es.formulastudent.app.R;

public enum ConeControlEvent implements Serializable {

        ENDURANCE (R.string.cc_endurance_title, EventType.ENDURANCE_EFFICIENCY,80021L,"DEV_CC_ENDURANCE", new HashMap<String, Integer>()
        {{
                put(ConeControlRegister.ROUND_1, R.string.cc_endurance_round_1);
                put(ConeControlRegister.ROUND_2, R.string.cc_endurance_round_2);
                put(ConeControlRegister.ROUND_FINAL, R.string.cc_endurance_round_final);
        }}),

        AUTOCROSS (R.string.cc_endurance_title, EventType.AUTOCROSS,80022L,"DEV_CC_AUTOCROSS", new HashMap<>());


        private final Integer activityTitle;
        private final Long drawerItemID;
        private final String firebaseTable;
        private final EventType eventType;
        private final Map<String, Integer> raceTypes;

        ConeControlEvent(Integer activityTitle, EventType eventType, Long drawerItemID, String firebaseTable, Map<String, Integer> raceTypes) {
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
