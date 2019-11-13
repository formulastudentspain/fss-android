package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class Role implements Serializable {

        //ROLES
        public final static String ADMINISTRATOR = "ADMINISTRATOR";
        public final static String STAFF = "STAFF";
        public final static String SCRUTUINEER = "SCRUTUINEER";
        public final static String MARSHALL = "MARSHALL";
        public final static String OFFICIAL_MARSHALL = "OFFICIAL_MARSHALL";
        public final static String OFFICIAL_SCRUTINEER = "OFFICIAL_SCRUTINEER";
        public final static String OFFICIAL_STAFF = "OFFICIAL_STAFF";


        //Database constants
        public static final String NAME = "name";

        private String ID;
        private String name;


        public Role(String ID, String name) {
                this.ID = ID;
                this.name = name;
        }

        public Role(DocumentSnapshot object){
                this.name = object.getString(Role.NAME);
        }

        public Role() {
        }

        public String getID() {
                return ID;
        }

        public void setID(String ID) {
                this.ID = ID;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}
