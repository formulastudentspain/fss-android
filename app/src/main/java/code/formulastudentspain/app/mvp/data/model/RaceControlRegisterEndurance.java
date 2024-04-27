package code.formulastudentspain.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.DNF;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.FINISHED;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.FIXING;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.NOT_AVAILABLE;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.RACING_1D;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.RACING_2D;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.READY_TO_RACE_1D;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.READY_TO_RACE_2D;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.RUN_LATER;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.SCRUTINEERING;
import static code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState.WAITING_AREA;

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

    private RaceControlEnduranceState currentState;

    public RaceControlRegisterEndurance(){}

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = super.toObjectData();
        docData.put(RaceControlRegister.CURRENT_STATE, this.currentState.getAcronym());
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

        String stateAcronym = object.getString(RaceControlRegister.CURRENT_STATE);
        if(stateAcronym.equals(RaceControlEnduranceState.NOT_AVAILABLE.getAcronym())){
            this.currentState = RaceControlEnduranceState.NOT_AVAILABLE;

        } else if(stateAcronym.equals(WAITING_AREA.getAcronym())){
            this.currentState = WAITING_AREA;

        }else if(stateAcronym.equals(SCRUTINEERING.getAcronym())){
            this.currentState = SCRUTINEERING;

        }else if(stateAcronym.equals(FIXING.getAcronym())){
            this.currentState = FIXING;

        }else if(stateAcronym.equals(READY_TO_RACE_1D.getAcronym())){
            this.currentState = READY_TO_RACE_1D;

        }else if(stateAcronym.equals(RACING_1D.getAcronym())){
            this.currentState = RACING_1D;

        }else if(stateAcronym.equals(READY_TO_RACE_2D.getAcronym())){
            this.currentState = READY_TO_RACE_2D;

        }else if(stateAcronym.equals(RACING_2D.getAcronym())){
            this.currentState = RACING_2D;

        }else if(stateAcronym.equals(FINISHED.getAcronym())){
            this.currentState = FINISHED;

        }else if(stateAcronym.equals(DNF.getAcronym())){
            this.currentState = DNF;

        }else if(stateAcronym.equals(RUN_LATER.getAcronym())){
            this.currentState = RUN_LATER;

        }
    }


    @Override
    public RaceControlState getNextStateAtIndex(int index) {
        String stateString = this.currentState.getStates().get(index);
        
        if(NOT_AVAILABLE.getAcronym().equals(stateString)){
            return NOT_AVAILABLE;
            
        }else if(WAITING_AREA.getAcronym().equals(stateString)){
            return WAITING_AREA;

        }else if(SCRUTINEERING.getAcronym().equals(stateString)){
            return SCRUTINEERING;

        }else if(FIXING.getAcronym().equals(stateString)){
            return FIXING;

        }else if(READY_TO_RACE_1D.getAcronym().equals(stateString)){
            return READY_TO_RACE_1D;

        }else if(RACING_1D.getAcronym().equals(stateString)){
            return RACING_1D;

        }else if(READY_TO_RACE_2D.getAcronym().equals(stateString)){
            return READY_TO_RACE_2D;

        }else if(RACING_2D.getAcronym().equals(stateString)){
            return RACING_2D;

        }else if(FINISHED.getAcronym().equals(stateString)){
            return FINISHED;

        }else if(DNF.getAcronym().equals(stateString)){
            return DNF;

        }else if(RUN_LATER.getAcronym().equals(stateString)){
            return RUN_LATER;
        }else{
            return null;
        }
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

    @Override
    public RaceControlEnduranceState getCurrentState() {
        return currentState;
    }
    
    @Override
    public void setCurrentState(RaceControlState currentState) {
        Date now = Calendar.getInstance().getTime();

        this.currentState = (RaceControlEnduranceState) currentState;
        this.setCurrentStateDate(now);

        //Update the state date
        switch (this.currentState){
            case DNF:
                setStateDNF(now);
                break;
            case WAITING_AREA:
                setStateWaitingArea(now);
                break;
            case SCRUTINEERING:
                setStateScrutineering(now);
                break;
            case RUN_LATER:
                setStateRunLater(now);
                break;
            case READY_TO_RACE_1D:
                setStateReadyToRace1D(now);
                break;
            case READY_TO_RACE_2D:
                setStateReadyToRace2D(now);
                break;
            case RACING_1D:
                setStateRacing1D(now);
                break;
            case RACING_2D:
                setStateRacing2D(now);
                break;
            case NOT_AVAILABLE:
                setStateNA(now);
                break;
            case FIXING:
                setStateFixing(now);
                break;
            case FINISHED:
                setStateFinished(now);
                break;
        }
    }

}