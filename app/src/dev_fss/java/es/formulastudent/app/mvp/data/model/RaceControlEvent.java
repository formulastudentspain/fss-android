package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import es.formulastudent.app.R;

public enum RaceControlEvent implements Serializable {

        ENDURANCE (R.string.rc_endurance_title, 10021L,"DEV_RC_ENDURANCE", new HashMap<String, Integer>()
        {{
                put(RaceControlRegister.RACE_TYPE_ELECTRIC, R.string.rc_endurance_electric_title);
                put(RaceControlRegister.RACE_TYPE_COMBUSTION, R.string.rc_endurance_combustion_title);
                put(RaceControlRegister.RACE_TYPE_FINAL, R.string.rc_endurance_final_title);
        }});


        private final Integer activityTitle;
        private final Long drawerItemID;
        private final String firebaseTable;
        private final Map<String, Integer> raceTypes;

        RaceControlEvent(Integer activityTitle, Long drawerItemID, String firebaseTable, Map<String, Integer> raceTypes) {
                this.activityTitle = activityTitle;
                this.drawerItemID = drawerItemID;
                this.firebaseTable = firebaseTable;
                this.raceTypes = raceTypes;
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
}
