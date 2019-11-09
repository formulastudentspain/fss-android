package es.formulastudent.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class RaceControlRegisterEndurance extends RaceControlRegister implements Serializable {

    //Database constants
    public static final String RC_STATE_WAITING_AREA = "rcWaitingArea";
    public static final String RC_STATE_SCRUTINEERING = "rcScrutineering";
    public static final String RC_STATE_FIXING = "rcFixing";
    public static final String RC_STATE_READY_TO_RACE_1D = "rcReadyToRace1d";
    public static final String RC_STATE_RACING_1D = "rcRacing1d";
    public static final String RC_STATE_READY_TO_RACE_2D = "rcReadyToRace2d";
    public static final String RC_STATE_RACING_2D = "rcRacing2d";
    public static final String RC_STATE_RUN_LATER = "rcRunLater";


    //States
    private Date stateWaitingArea;
    private Date stateScrutineering;
    private Date stateFixing;
    private Date stateReadyToRace1D;
    private Date stateRacing1D;
    private Date stateReadyToRace2D;
    private Date stateRacing2D;
    private Date stateRunLater;

    public RaceControlRegisterEndurance(){}

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = super.toObjectData();
        docData.put(RaceControlRegisterEndurance.RC_STATE_WAITING_AREA, this.stateWaitingArea == null ? null : new Timestamp(this.stateWaitingArea));
        docData.put(RaceControlRegisterEndurance.RC_STATE_SCRUTINEERING, this.stateScrutineering == null ? null : new Timestamp(this.stateScrutineering));
        docData.put(RaceControlRegisterEndurance.RC_STATE_FIXING, this.stateFixing == null ? null : new Timestamp(this.stateFixing));
        docData.put(RaceControlRegisterEndurance.RC_STATE_READY_TO_RACE_1D, this.stateReadyToRace1D == null ? null : new Timestamp(this.stateReadyToRace1D));
        docData.put(RaceControlRegisterEndurance.RC_STATE_RACING_1D, this.stateRacing1D == null ? null : new Timestamp(this.stateRacing1D));
        docData.put(RaceControlRegisterEndurance.RC_STATE_READY_TO_RACE_2D, this.stateReadyToRace2D == null ? null : new Timestamp(this.stateReadyToRace2D));
        docData.put(RaceControlRegisterEndurance.RC_STATE_RACING_2D, this.stateRacing2D == null ? null : new Timestamp(this.stateRacing2D));
        docData.put(RaceControlRegisterEndurance.RC_STATE_RUN_LATER, this.stateRunLater == null ? null : new Timestamp(this.stateRunLater));

        return docData;
    }

    public RaceControlRegisterEndurance(DocumentSnapshot object){
        super(object);

        this.stateWaitingArea = object.getDate(RaceControlRegisterEndurance.RC_STATE_WAITING_AREA);
        this.stateScrutineering = object.getDate(RaceControlRegisterEndurance.RC_STATE_SCRUTINEERING);
        this.stateFixing = object.getDate(RaceControlRegisterEndurance.RC_STATE_FIXING);
        this.stateReadyToRace1D = object.getDate(RaceControlRegisterEndurance.RC_STATE_READY_TO_RACE_1D);
        this.stateRacing1D = object.getDate(RaceControlRegisterEndurance.RC_STATE_RACING_1D);
        this.stateReadyToRace2D = object.getDate(RaceControlRegisterEndurance.RC_STATE_READY_TO_RACE_2D);
        this.stateRacing2D = object.getDate(RaceControlRegisterEndurance.RC_STATE_RACING_2D);
        this.stateRunLater = object.getDate(RaceControlRegisterEndurance.RC_STATE_RUN_LATER);
    }

    public Date getStateWaitingArea() {
        return stateWaitingArea;
    }

    public void setStateWaitingArea(Date stateWaitingArea) {
        this.stateWaitingArea = stateWaitingArea;
    }

    public Date getStateScrutineering() {
        return stateScrutineering;
    }

    public void setStateScrutineering(Date stateScrutineering) {
        this.stateScrutineering = stateScrutineering;
    }

    public Date getStateFixing() {
        return stateFixing;
    }

    public void setStateFixing(Date stateFixing) {
        this.stateFixing = stateFixing;
    }

    public Date getStateReadyToRace1D() {
        return stateReadyToRace1D;
    }

    public void setStateReadyToRace1D(Date stateReadyToRace1D) {
        this.stateReadyToRace1D = stateReadyToRace1D;
    }

    public Date getStateRacing1D() {
        return stateRacing1D;
    }

    public void setStateRacing1D(Date stateRacing1D) {
        this.stateRacing1D = stateRacing1D;
    }

    public Date getStateReadyToRace2D() {
        return stateReadyToRace2D;
    }

    public void setStateReadyToRace2D(Date stateReadyToRace2D) {
        this.stateReadyToRace2D = stateReadyToRace2D;
    }

    public Date getStateRacing2D() {
        return stateRacing2D;
    }

    public void setStateRacing2D(Date stateRacing2D) {
        this.stateRacing2D = stateRacing2D;
    }

    public Date getStateRunLater() {
        return stateRunLater;
    }

    public void setStateRunLater(Date stateRunLater) {
        this.stateRunLater = stateRunLater;
    }
}