package code.formulastudentspain.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class RaceControlRegister implements Serializable {

    //RaceControl Event Type
    public static final String RACE_ROUND_1 = "1";
    public static final String RACE_ROUND_2 = "2";
    public static final String RACE_TYPE_ELECTRIC_DRIVERLESS = Car.CAR_TYPE_AUTONOMOUS_ELECTRIC;
    public static final String RACE_TYPE_COMBUSTION_DRIVERLESS = Car.CAR_TYPE_AUTONOMOUS_COMBUSTION;
    public static final String RACE_ROUND_FINAL = "Final";


    //Database constants
    public static final String CAR_NUMBER = "carNumber";
    public static final String ROUND = "round";
    public static final String FLAG_URL = "flagURL";
    public static final String CURRENT_STATE = "currentState";
    public static final String CURRENT_STATE_DATE = "currentStateDate";
    public static final String ORDER = "order";
    public static final String RC_STATE_DNF = "rcDNF";
    public static final String RC_STATE_FINISHED = "rcFinished";
    public static final String RC_STATE_NOT_AVAILABLE = "rcNotAvailable";


    private String ID;
    private String round;
    private Long carNumber;
    private String flagURL;
    private Date currentStateDate;
    private Long order;

    //States
    private Date stateNA;
    private Date stateDNF;
    private Date stateFinished;

    protected RaceControlRegister(){}

    protected Map<String, Object> toObjectData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(RaceControlRegister.CAR_NUMBER, this.carNumber);
        docData.put(RaceControlRegister.ROUND, this.round);
        docData.put(RaceControlRegister.FLAG_URL, this.flagURL);
        docData.put(RaceControlRegister.CURRENT_STATE_DATE, this.currentStateDate == null ? null : new Timestamp(this.currentStateDate));
        docData.put(RaceControlRegister.ORDER, this.order);
        docData.put(RaceControlRegister.RC_STATE_NOT_AVAILABLE, this.stateNA == null ? null : new Timestamp(this.stateNA));
        docData.put(RaceControlRegister.RC_STATE_DNF, this.stateDNF == null ? null : new Timestamp(this.stateDNF));
        docData.put(RaceControlRegister.RC_STATE_FINISHED, this.stateFinished == null ? null : new Timestamp(this.stateFinished));

        return docData;
    }


    protected RaceControlRegister(DocumentSnapshot object){
        this.ID = object.getId();
        this.carNumber = object.getLong(RaceControlRegister.CAR_NUMBER);
        this.round = object.getString(RaceControlRegister.ROUND);
        this.flagURL = object.getString(RaceControlRegister.FLAG_URL);
        this.currentStateDate = object.getDate(RaceControlRegister.CURRENT_STATE_DATE);
        this.order = object.getLong(RaceControlRegister.ORDER);
        this.stateNA = object.getDate(RaceControlRegister.RC_STATE_NOT_AVAILABLE);
        this.stateDNF = object.getDate(RaceControlRegister.RC_STATE_DNF);
        this.stateFinished = object.getDate(RaceControlRegister.RC_STATE_FINISHED);

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public Long getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Long carNumber) {
        this.carNumber = carNumber;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public Date getCurrentStateDate() {
        return currentStateDate;
    }

    public void setCurrentStateDate(Date currentStateDate) {
        this.currentStateDate = currentStateDate;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Date getStateDNF() {
        return stateDNF;
    }

    public void setStateDNF(Date stateDNF) {
        this.stateDNF = stateDNF;
    }

    public Date getStateFinished() {
        return stateFinished;
    }

    public void setStateFinished(Date stateFinished) {
        this.stateFinished = stateFinished;
    }

    public Date getStateNA() {
        return stateNA;
    }

    public void setStateNA(Date stateNA) {
        this.stateNA = stateNA;
    }

    public abstract RaceControlState getCurrentState();

    public abstract RaceControlState getNextStateAtIndex(int index);

    public abstract void setCurrentState(RaceControlState currentState);
}