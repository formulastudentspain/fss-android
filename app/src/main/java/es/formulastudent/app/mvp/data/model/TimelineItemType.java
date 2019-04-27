package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

public enum TimelineItemType implements Serializable {

        DAY_TYPE ("day_type", -1),
        EVENT ("Event", 1),
        INCIDENT("Incident", 2),
        SOCIAL("Social", 3),
        RESULT("Result", 4);

        private final String value;
        private final int code;

        TimelineItemType(String value, int code) {
                this.value = value;
                this.code = code;
        }

        public String getValue() {
                return value;
        }

        public int getCode() {
                return code;
        }

}
