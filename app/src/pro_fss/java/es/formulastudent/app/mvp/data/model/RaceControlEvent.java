package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum RaceControlEvent implements Serializable {

        ENDURANCE ("Race Control: Endurance", 10001L,"FSS_RC_ENDURANCE");


        private final String activityTitle;
        private final Long drawerItemID;
        private final String firebaseTable;

        RaceControlEvent(String value, Long drawerItemID, String firebaseTable) {
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
