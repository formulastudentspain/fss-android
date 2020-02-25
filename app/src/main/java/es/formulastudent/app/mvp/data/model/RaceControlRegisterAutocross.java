package es.formulastudent.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.DNF;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.FINISHED_ROUND_1;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.FINISHED_ROUND_2;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.FINISHED_ROUND_3;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.FINISHED_ROUND_4;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.NOT_AVAILABLE;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.RACING_ROUND_1;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.RACING_ROUND_2;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.RACING_ROUND_3;
import static es.formulastudent.app.mvp.data.model.RaceControlAutocrossState.RACING_ROUND_4;

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

    private RaceControlAutocrossState currentState;

    public RaceControlRegisterAutocross(){}

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = super.toObjectData();
        docData.put(RaceControlRegister.CURRENT_STATE, this.currentState.getAcronym());
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
        this.stateRacingRound2 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_2);
        this.stateFinishedRound2 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_2);
        this.stateRacingRound3 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_3);
        this.stateFinishedRound3 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_3);
        this.stateRacingRound4 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_4);
        this.stateFinishedRound4 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_4);

        String stateAcronym = object.getString(RaceControlRegister.CURRENT_STATE);
        if(stateAcronym.equals(NOT_AVAILABLE.getAcronym())){
            this.currentState = NOT_AVAILABLE;

        } else if(stateAcronym.equals(RACING_ROUND_1.getAcronym())){
            this.currentState = RACING_ROUND_1;

        }else if(stateAcronym.equals(FINISHED_ROUND_1.getAcronym())){
            this.currentState = FINISHED_ROUND_1;

        }else if(stateAcronym.equals(RACING_ROUND_2.getAcronym())){
            this.currentState = RACING_ROUND_2;

        }else if(stateAcronym.equals(FINISHED_ROUND_2.getAcronym())){
            this.currentState = FINISHED_ROUND_2;

        }else if(stateAcronym.equals(RACING_ROUND_3.getAcronym())){
            this.currentState = RACING_ROUND_3;

        }else if(stateAcronym.equals(FINISHED_ROUND_3.getAcronym())){
            this.currentState = FINISHED_ROUND_3;

        }else if(stateAcronym.equals(RACING_ROUND_4.getAcronym())){
            this.currentState = RACING_ROUND_4;

        }else if(stateAcronym.equals(FINISHED_ROUND_4.getAcronym())){
            this.currentState = FINISHED_ROUND_4;

        }else if(stateAcronym.equals(DNF.getAcronym())){
            this.currentState = DNF;

        }
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

    @Override
    public RaceControlAutocrossState getCurrentState() {
        return currentState;
    }

    @Override
    public RaceControlState getNextStateAtIndex(int index) {
        String stateString = this.currentState.getStates().get(index);

        if(NOT_AVAILABLE.getAcronym().equals(stateString)){
            return NOT_AVAILABLE;

        }else if(RACING_ROUND_1.getAcronym().equals(stateString)){
            return RACING_ROUND_1;

        }else if(FINISHED_ROUND_1.getAcronym().equals(stateString)){
            return FINISHED_ROUND_1;

        }else if(FINISHED_ROUND_2.getAcronym().equals(stateString)){
            return FINISHED_ROUND_2;

        }else if(RACING_ROUND_2.getAcronym().equals(stateString)){
            return RACING_ROUND_2;

        }else if(RACING_ROUND_3.getAcronym().equals(stateString)){
            return RACING_ROUND_3;

        }else if(FINISHED_ROUND_3.getAcronym().equals(stateString)){
            return FINISHED_ROUND_3;

        }else if(RACING_ROUND_4.getAcronym().equals(stateString)){
            return RACING_ROUND_4;

        }else if(FINISHED_ROUND_4.getAcronym().equals(stateString)){
            return FINISHED_ROUND_4;

        }else if(DNF.getAcronym().equals(stateString)) {
            return DNF;

        }else{
            return null;
        }
    }

    @Override
    public void setCurrentState(RaceControlState currentState) {
        Date now = Calendar.getInstance().getTime();

        this.currentState = (RaceControlAutocrossState) currentState;
        this.setCurrentStateDate(now);

        //Update the state date
        switch (this.currentState){
            case NOT_AVAILABLE:
                setStateNA(now);
                break;
            case RACING_ROUND_1:
                setStateRacingRound1(now);
                break;
            case FINISHED_ROUND_1:
                setStateFinishedRound1(now);
                break;
            case RACING_ROUND_2:
                setStateRacingRound2(now);
                break;
            case FINISHED_ROUND_2:
                setStateFinishedRound2(now);
                break;
            case RACING_ROUND_3:
                setStateRacingRound3(now);
                break;
            case FINISHED_ROUND_3:
                setStateFinishedRound3(now);
                break;
            case RACING_ROUND_4:
                setStateRacingRound4(now);
                break;
            case FINISHED_ROUND_4:
                setStateFinishedRound4(now);
                break;
        }
    }
}