package code.formulastudentspain.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class RaceControlRegisterAutocross extends RaceControlRegister implements Serializable {

    //Database constants
    public static final String RC_STATE_RACING_ROUND_1 = "rcRacingRound1";
    public static final String RC_STATE_FINISHED_ROUND_1 = "rcFinishedRound1";
    public static final String RC_STATE_DNF_ROUND_1 = "rcDNFRound1";
    public static final String RC_STATE_RACING_ROUND_2 = "rcRacingRound2";
    public static final String RC_STATE_FINISHED_ROUND_2 = "rcFinishedRound2";
    public static final String RC_STATE_DNF_ROUND_2 = "rcDNFRound2";
    public static final String RC_STATE_RACING_ROUND_3 = "rcRacingRound3";
    public static final String RC_STATE_FINISHED_ROUND_3 = "rcFinishedRound3";
    public static final String RC_STATE_DNF_ROUND_3 = "rcDNFRound3";
    public static final String RC_STATE_RACING_ROUND_4 = "rcRacingRound4";
    public static final String RC_STATE_FINISHED_ROUND_4 = "rcFinishedRound4";
    public static final String RC_STATE_DNF_ROUND_4 = "rcDNFRound4";


    //States
    private Date stateRacingRound1;
    private Date stateFinishedRound1;
    private Date stateDNFRound1;
    private Date stateRacingRound2;
    private Date stateFinishedRound2;
    private Date stateDNFRound2;
    private Date stateRacingRound3;
    private Date stateFinishedRound3;
    private Date stateDNFRound3;
    private Date stateRacingRound4;
    private Date stateFinishedRound4;
    private Date stateDNFRound4;


    private RaceControlAutocrossState currentState;

    public RaceControlRegisterAutocross(){}

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = super.toObjectData();
        docData.put(RaceControlRegister.CURRENT_STATE, this.currentState.getAcronym());
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_1, this.stateRacingRound1 == null ? null : new Timestamp(this.stateRacingRound1));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_1, this.stateFinishedRound1 == null ? null : new Timestamp(this.stateFinishedRound1));
        docData.put(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_1, this.stateDNFRound1 == null ? null : new Timestamp(this.stateDNFRound1));
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_2, this.stateRacingRound2 == null ? null : new Timestamp(this.stateRacingRound2));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_2, this.stateFinishedRound2 == null ? null : new Timestamp(this.stateFinishedRound2));
        docData.put(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_2, this.stateDNFRound2 == null ? null : new Timestamp(this.stateDNFRound2));
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_3, this.stateRacingRound3 == null ? null : new Timestamp(this.stateRacingRound3));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_3, this.stateFinishedRound3 == null ? null : new Timestamp(this.stateFinishedRound3));
        docData.put(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_3, this.stateDNFRound3 == null ? null : new Timestamp(this.stateDNFRound3));
        docData.put(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_4, this.stateRacingRound4 == null ? null : new Timestamp(this.stateRacingRound4));
        docData.put(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_4, this.stateFinishedRound4 == null ? null : new Timestamp(this.stateFinishedRound4));
        docData.put(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_4, this.stateDNFRound4 == null ? null : new Timestamp(this.stateDNFRound4));

        return docData;
    }

    public RaceControlRegisterAutocross(DocumentSnapshot object){
        super(object);

        this.stateRacingRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_1);
        this.stateFinishedRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_1);
        this.stateDNFRound1 = object.getDate(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_1);
        this.stateRacingRound2 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_2);
        this.stateFinishedRound2 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_2);
        this.stateDNFRound2 = object.getDate(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_2);
        this.stateRacingRound3 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_3);
        this.stateFinishedRound3 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_3);
        this.stateDNFRound3 = object.getDate(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_3);
        this.stateRacingRound4 = object.getDate(RaceControlRegisterAutocross.RC_STATE_RACING_ROUND_4);
        this.stateFinishedRound4 = object.getDate(RaceControlRegisterAutocross.RC_STATE_FINISHED_ROUND_4);
        this.stateDNFRound4 = object.getDate(RaceControlRegisterAutocross.RC_STATE_DNF_ROUND_4);

        String stateAcronym = object.getString(RaceControlRegister.CURRENT_STATE);
        this.currentState = RaceControlAutocrossState.getStateByAcronym(stateAcronym);

    }

    @Override
    public RaceControlState getNextStateAtIndex(int index) {
        String stateString = this.currentState.getStates().get(index);
        return RaceControlAutocrossState.getStateByAcronym(stateString);
    }

    public void setStateRacingRound1(Date stateRacingRound1) {
        this.stateRacingRound1 = stateRacingRound1;
    }

    public void setStateFinishedRound1(Date stateFinishedRound1) {
        this.stateFinishedRound1 = stateFinishedRound1;
    }

    public void setStateRacingRound2(Date stateRacingRound2) {
        this.stateRacingRound2 = stateRacingRound2;
    }

    public void setStateFinishedRound2(Date stateFinishedRound2) {
        this.stateFinishedRound2 = stateFinishedRound2;
    }

    public void setStateRacingRound3(Date stateRacingRound3) {
        this.stateRacingRound3 = stateRacingRound3;
    }

    public void setStateFinishedRound3(Date stateFinishedRound3) {
        this.stateFinishedRound3 = stateFinishedRound3;
    }

    public void setStateRacingRound4(Date stateRacingRound4) {
        this.stateRacingRound4 = stateRacingRound4;
    }

    public void setStateFinishedRound4(Date stateFinishedRound4) {
        this.stateFinishedRound4 = stateFinishedRound4;
    }

    public void setStateDNFRound1(Date stateDNFRound1) {
        this.stateDNFRound1 = stateDNFRound1;
    }

    public void setStateDNFRound2(Date stateDNFRound2) {
        this.stateDNFRound2 = stateDNFRound2;
    }

    public void setStateDNFRound3(Date stateDNFRound3) {
        this.stateDNFRound3 = stateDNFRound3;
    }

    public void setStateDNFRound4(Date stateDNFRound4) {
        this.stateDNFRound4 = stateDNFRound4;
    }

    @Override
    public RaceControlAutocrossState getCurrentState() {
        return currentState;
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
            case DNF_ROUND_1:
                setStateDNFRound1(now);
                break;
            case RACING_ROUND_2:
                setStateRacingRound2(now);
                break;
            case FINISHED_ROUND_2:
                setStateFinishedRound2(now);
                break;
            case DNF_ROUND_2:
                setStateDNFRound2(now);
                break;
            case RACING_ROUND_3:
                setStateRacingRound3(now);
                break;
            case FINISHED_ROUND_3:
                setStateFinishedRound3(now);
                break;
            case DNF_ROUND_3:
                setStateDNFRound3(now);
                break;
            case RACING_ROUND_4:
                setStateRacingRound4(now);
                break;
            case FINISHED_ROUND_4:
                setStateFinishedRound4(now);
                break;
            case DNF_ROUND_4:
                setStateDNFRound4(now);
                break;
        }
    }
}