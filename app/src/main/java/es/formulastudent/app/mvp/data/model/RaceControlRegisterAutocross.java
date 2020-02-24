package es.formulastudent.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class RaceControlRegisterAutocross extends RaceControlRegister implements Serializable {

    //Database constants
    public static final String RC_STATE_RACING_ROUND_1 = "rcRacingRound1";
    public static final String RC_STATE_FINISHED_ROUND_1 = "rcFinishedRound1";
    public static final String RC_STATE_RACING_ROUND_2 = "rcRacingRound2";
    public static final String RC_STATE_FINISHED_ROUND_2 = "rcFinishedRound2";
    public static final String RC_STATE_RACING_ROUND_3 = "rcRacingRound3";
    public static final String RC_STATE_FINISHED_ROUND_3 = "rcFinishedRound3";
    public static final String RC_STATE_RACING_ROUND_4 = "rcRacingRound4";
    public static final String RC_STATE_FINISHED_ROUND_4 = "rcFinishedRound4";

    //States
    private Date stateRacingRound1;
    private Date stateFinishedRound1;
    private Date stateRacingRound2;
    private Date stateFinishedRound2;
    private Date stateRacingRound3;
    private Date stateFinishedRound3;
    private Date stateRacingRound4;
    private Date stateFinishedRound4;

    public RaceControlRegisterAutocross(){}

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = super.toObjectData();
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_1, this.stateRacingRound1 == null ? null : new Timestamp(this.stateRacingRound1));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_1, this.stateFinishedRound1 == null ? null : new Timestamp(this.stateFinishedRound1));
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_2, this.stateRacingRound2 == null ? null : new Timestamp(this.stateRacingRound2));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_2, this.stateFinishedRound2 == null ? null : new Timestamp(this.stateFinishedRound2));
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_3, this.stateRacingRound3 == null ? null : new Timestamp(this.stateRacingRound3));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_3, this.stateFinishedRound3 == null ? null : new Timestamp(this.stateFinishedRound3));
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_4, this.stateRacingRound4 == null ? null : new Timestamp(this.stateRacingRound4));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_4, this.stateFinishedRound4 == null ? null : new Timestamp(this.stateFinishedRound4));

        return docData;
    }

    public RaceControlRegisterAutocross(DocumentSnapshot object){
        super(object);

        this.stateRacingRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_1);
        this.stateFinishedRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_1);
        this.stateRacingRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_2);
        this.stateFinishedRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_2);
        this.stateRacingRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_3);
        this.stateFinishedRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_3);
        this.stateRacingRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_4);
        this.stateFinishedRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_4);
    }

    public Date getStateRacingRound1() {
        return stateRacingRound1;
    }

    public void setStateRacingRound1(Date stateRacingRound1) {
        this.stateRacingRound1 = stateRacingRound1;
    }

    public Date getStateFinishedRound1() {
        return stateFinishedRound1;
    }

    public void setStateFinishedRound1(Date stateFinishedRound1) {
        this.stateFinishedRound1 = stateFinishedRound1;
    }

    public Date getStateRacingRound2() {
        return stateRacingRound2;
    }

    public void setStateRacingRound2(Date stateRacingRound2) {
        this.stateRacingRound2 = stateRacingRound2;
    }

    public Date getStateFinishedRound2() {
        return stateFinishedRound2;
    }

    public void setStateFinishedRound2(Date stateFinishedRound2) {
        this.stateFinishedRound2 = stateFinishedRound2;
    }

    public Date getStateRacingRound3() {
        return stateRacingRound3;
    }

    public void setStateRacingRound3(Date stateRacingRound3) {
        this.stateRacingRound3 = stateRacingRound3;
    }

    public Date getStateFinishedRound3() {
        return stateFinishedRound3;
    }

    public void setStateFinishedRound3(Date stateFinishedRound3) {
        this.stateFinishedRound3 = stateFinishedRound3;
    }

    public Date getStateRacingRound4() {
        return stateRacingRound4;
    }

    public void setStateRacingRound4(Date stateRacingRound4) {
        this.stateRacingRound4 = stateRacingRound4;
    }

    public Date getStateFinishedRound4() {
        return stateFinishedRound4;
    }

    public void setStateFinishedRound4(Date stateFinishedRound4) {
        this.stateFinishedRound4 = stateFinishedRound4;
    }
}