package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class UserRole implements Serializable {

        public static String COLLECTION_ID = "USER_ROLE";

        //Database constants
        public static final String NAME = "name";

        private String ID;
        private String name;


        public UserRole(String ID, String name) {
                this.ID = ID;
                this.name = name;
        }

        public UserRole(DocumentSnapshot object){
                this.name = object.getString(User.NAME);
        }

        public UserRole() {
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
