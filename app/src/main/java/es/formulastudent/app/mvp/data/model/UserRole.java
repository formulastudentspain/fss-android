package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum UserRole implements Serializable {

        DRIVER ("Driver", 1),
        STAFF ("Staff", 2);

        private final String value;
        private final int code;

        UserRole(String value, int code) {
                this.value = value;
                this.code = code;
        }

        public String getValue() {
                return value;
        }

        public int getCode() {
                return code;
        }

        @Override
        public String toString(){
                return getValue();
        }
}
