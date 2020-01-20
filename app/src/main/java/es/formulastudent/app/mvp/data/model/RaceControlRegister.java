package es.formulastudent.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class RaceControlRegister implements Serializable {

    //RaceControl Event Type
    public static final String RACE_TYPE_ELECTRIC = Car.CAR_TYPE_ELECTRIC;
    public static final String RACE_TYPE_COMBUSTION = Car.CAR_TYPE_COMBUSTION;
    public static final String RACE_TYPE_ELECTRIC_DRIVERLESS = Car.CAR_TYPE_AUTONOMOUS_ELECTRIC;
    public static final String RACE_TYPE_COMBUSTION_DRIVERLESS = Car.CAR_TYPE_AUTONOMOUS_COMBUSTION;
    public static final String RACE_TYPE_FINAL = "Final";


    //Database constants
    public static final String CAR_NUMBER = "carNumber";
    public static final String CAR_TYPE = "carType";
    public static final String FLAG_URL = "flagURL";
    public static final String CURRENT_STATE = "currentState";
    public static final String CURRENT_STATE_DATE = "userID";
    public static final String ORDER = "order";
    public static final String RC_STATE_DNF = "rcDNF";
    public static final String RC_STATE_FINISHED = "rcFinished";
    public static final String RC_STATE_NOT_AVAILABLE = "rcNotAvailable";
    public static final String RUN_FINAL = "runFinal";


    private String ID;
    private String carType;
    private Long carNumber;
    private String flagURL;
    private Date currentStateDate;
    private Long order;
    private RaceControlState currentState;
    private Boolean runFinal;

    //States
    private Date stateNA;
    private Date stateDNF;
    private Date stateFinished;

    protected RaceControlRegister(){}

    protected Map<String, Object> toObjectData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(RaceControlRegister.CAR_NUMBER, this.carNumber);
        docData.put(RaceControlRegister.CAR_TYPE, this.carType);
        docData.put(RaceControlRegister.FLAG_URL, this.flagURL);
        docData.put(RaceControlRegister.CURRENT_STATE, this.currentState.getAcronym());
        docData.put(RaceControlRegister.CURRENT_STATE_DATE, this.currentStateDate == null ? null : new Timestamp(this.currentStateDate));
        docData.put(RaceControlRegister.ORDER, this.order);
        docData.put(RaceControlRegister.RC_STATE_NOT_AVAILABLE, this.stateNA == null ? null : new Timestamp(this.stateNA));
        docData.put(RaceControlRegister.RC_STATE_DNF, this.stateDNF == null ? null : new Timestamp(this.stateDNF));
        docData.put(RaceControlRegister.RC_STATE_FINISHED, this.stateFinished == null ? null : new Timestamp(this.stateFinished));
        docData.put(RaceControlRegister.RUN_FINAL, runFinal);

        return docData;
    }


    protected RaceControlRegister(DocumentSnapshot object){
        this.ID = object.getId();
        this.carNumber = object.getLong(RaceControlRegister.CAR_NUMBER);
        this.carType = object.getString(RaceControlRegister.CAR_TYPE);
        this.flagURL = object.getString(RaceControlRegister.FLAG_URL);
        this.currentStateDate = object.getDate(RaceControlRegister.CURRENT_STATE_DATE);
        this.order = object.getLong(RaceControlRegister.ORDER);
        this.stateNA = object.getDate(RaceControlRegister.RC_STATE_NOT_AVAILABLE);
        this.stateDNF = object.getDate(RaceControlRegister.RC_STATE_DNF);
        this.stateFinished = object.getDate(RaceControlRegister.RC_STATE_FINISHED);
        this.runFinal = object.getBoolean(RaceControlRegister.RUN_FINAL);

        String stateAcronym = object.getString(RaceControlRegister.CURRENT_STATE);
        if(stateAcronym.equals(RaceControlState.NOT_AVAILABLE.getAcronym())){
            this.currentState = RaceControlState.NOT_AVAILABLE;

        } else if(stateAcronym.equals(RaceControlState.WAITING_AREA.getAcronym())){
            this.currentState = RaceControlState.WAITING_AREA;

        }else if(stateAcronym.equals(RaceControlState.SCRUTINEERING.getAcronym())){
            this.currentState = RaceControlState.SCRUTINEERING;

        }else if(stateAcronym.equals(RaceControlState.FIXING.getAcronym())){
            this.currentState = RaceControlState.FIXING;

        }else if(stateAcronym.equals(RaceControlState.READY_TO_RACE_1D.getAcronym())){
            this.currentState = RaceControlState.READY_TO_RACE_1D;

        }else if(stateAcronym.equals(RaceControlState.RACING_1D.getAcronym())){
            this.currentState = RaceControlState.RACING_1D;

        }else if(stateAcronym.equals(RaceControlState.READY_TO_RACE_2D.getAcronym())){
            this.currentState = RaceControlState.READY_TO_RACE_2D;

        }else if(stateAcronym.equals(RaceControlState.RACING_2D.getAcronym())){
            this.currentState = RaceControlState.RACING_2D;

        }else if(stateAcronym.equals(RaceControlState.FINISHED.getAcronym())){
            this.currentState = RaceControlState.FINISHED;

        }else if(stateAcronym.equals(RaceControlState.DNF.getAcronym())){
            this.currentState = RaceControlState.DNF;

        }else if(stateAcronym.equals(RaceControlState.RUN_LATER.getAcronym())){
            this.currentState = RaceControlState.RUN_LATER;

        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
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

    public RaceControlState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(RaceControlState currentState) {
        this.currentState = currentState;
    }

    public Boolean getRunFinal() {
        return runFinal;
    }

    public void setRunFinal(Boolean runFinal) {
        this.runFinal = runFinal;
    }

    public Date getStateNA() {
        return stateNA;
    }

    public void setStateNA(Date stateNA) {
        this.stateNA = stateNA;
    }
}