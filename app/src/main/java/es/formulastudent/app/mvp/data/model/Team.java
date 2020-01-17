package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Team implements Serializable, Cloneable {


    //Database constants
    public static final String NAME = "name";
    public static final String CAR = "car";
    public static final String CAR_NUMBER = "car.number";
    public static final String CAR_TYPE = "car.type";
    public static final String SCRUTINEERING_PS = "scrutineeringPS";
    public static final String SCRUTINEERING_AI = "scrutineeringAI";
    public static final String SCRUTINEERING_EI = "scrutineeringEI";
    public static final String SCRUTINEERING_MI = "scrutineeringMI";
    public static final String SCRUTINEERING_TTT = "scrutineeringTTT";
    public static final String SCRUTINEERING_NT = "scrutineeringNT";
    public static final String SCRUTINEERING_RT = "scrutineeringRT";
    public static final String SCRUTINEERING_BT = "scrutineeringBT";
    public static final String SCRUTINEERING_PS_COMMENT = "scrutineeringPSComment";
    public static final String SCRUTINEERING_AI_COMMENT = "scrutineeringAIComment";
    public static final String SCRUTINEERING_EI_COMMENT = "scrutineeringEIComment";
    public static final String SCRUTINEERING_MI_COMMENT = "scrutineeringMIComment";
    public static final String SCRUTINEERING_TTT_COMMENT = "scrutineeringTTTComment";
    public static final String SCRUTINEERING_NT_COMMENT = "scrutineeringNTComment";
    public static final String SCRUTINEERING_RT_COMMENT = "scrutineeringRTComment";
    public static final String SCRUTINEERING_BT_COMMENT = "scrutineeringBTComment";
    public static final String ENERGY_METER_FEE_GIVEN = "energyMeterFeeGiven";
    public static final String ENERGY_METER_ITEM_GIVEN = "energyMeterItemGiven";
    public static final String ENERGY_METER_ITEM_RETURNED = "energyMeterItemReturned";
    public static final String ENERGY_METER_FEE_RETURNED = "energyMeterFeeReturned";
    public static final String TRANSPONDER_FEE_GIVEN = "transponderFeeGiven";
    public static final String TRANSPONDER_ITEM_GIVEN = "transponderItemGiven";
    public static final String TRANSPONDER_ITEM_RETURNED = "transponderItemReturned";
    public static final String TRANSPONDER_FEE_RETURNED = "transponderFeeReturned";






    private String ID;
    private String name;
    private Car car;

    //Passes
    private Boolean scrutineeringPS; //Pre-Scrutineering (C, DC, E, DE)
    private Boolean scrutineeringAI; //Accumulation Inspection (E, DE)
    private Boolean scrutineeringEI; //Electrical Inspection (E, DE)
    private Boolean scrutineeringMI; //Mechanical Inspection (C, DC, E, DE)
    private Boolean scrutineeringTTT; //Tilt Table Test (C, DC, E, DE)
    private Boolean scrutineeringNT; //Noise Test (C, DC)
    private Boolean scrutineeringRT; //Rain Test (E, DE)
    private Boolean scrutineeringBT; //Brake Test (C, DC, E, DE)

    //Comments
    private String scrutineeringPSComment; //Pre-Scrutineering (C, DC, E, DE)
    private String scrutineeringAIComment; //Accumulation Inspection (E, DE)
    private String scrutineeringEIComment; //Electrical Inspection (E, DE)
    private String scrutineeringMIComment; //Mechanical Inspection (C, DC, E, DE)
    private String scrutineeringTTTComment; //Tilt Table Test (C, DC, E, DE)
    private String scrutineeringNTComment; //Noise Test (C, DC)
    private String scrutineeringRTComment; //Rain Test (E, DE)
    private String scrutineeringBTComment; //Brake Test (C, DC, E, DE)
    
    //Fee transponder
    private Boolean transponderFeeGiven;
    private Boolean transponderItemGiven;
    private Boolean transponderItemReturned;
    private Boolean transponderFeeReturned;

    //Fee energy meter
    private Boolean energyMeterFeeGiven;
    private Boolean energyMeterItemGiven;
    private Boolean energyMeterItemReturned;
    private Boolean energyMeterFeeReturned;



    
    public Team(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Team(DocumentSnapshot object){
        this.ID = object.getReference().getId();
        this.name = object.getString(Team.NAME);

        Map<String, Object> carMap = (Map<String, Object>) object.getData().get(Team.CAR);
        Car car = new Car();
        car.setNumber((Long)carMap.get("number"));
        car.setType((String)carMap.get("type"));
        this.car = car;

        this.scrutineeringPS = object.getBoolean(Team.SCRUTINEERING_PS);
        this.scrutineeringAI = object.getBoolean(Team.SCRUTINEERING_AI);
        this.scrutineeringEI = object.getBoolean(Team.SCRUTINEERING_EI);
        this.scrutineeringMI = object.getBoolean(Team.SCRUTINEERING_MI);
        this.scrutineeringTTT = object.getBoolean(Team.SCRUTINEERING_TTT);
        this.scrutineeringNT = object.getBoolean(Team.SCRUTINEERING_NT);
        this.scrutineeringRT = object.getBoolean(Team.SCRUTINEERING_RT);
        this.scrutineeringBT = object.getBoolean(Team.SCRUTINEERING_BT);

        this.scrutineeringPSComment = object.getString(Team.SCRUTINEERING_PS_COMMENT);
        this.scrutineeringAIComment = object.getString(Team.SCRUTINEERING_AI_COMMENT);
        this.scrutineeringEIComment = object.getString(Team.SCRUTINEERING_EI_COMMENT);
        this.scrutineeringMIComment = object.getString(Team.SCRUTINEERING_MI_COMMENT);
        this.scrutineeringTTTComment = object.getString(Team.SCRUTINEERING_TTT_COMMENT);
        this.scrutineeringNTComment = object.getString(Team.SCRUTINEERING_NT_COMMENT);
        this.scrutineeringRTComment = object.getString(Team.SCRUTINEERING_RT_COMMENT);
        this.scrutineeringBTComment = object.getString(Team.SCRUTINEERING_BT_COMMENT);
        
        this.transponderFeeGiven = object.getBoolean(Team.TRANSPONDER_FEE_GIVEN);
        this.transponderItemGiven = object.getBoolean(Team.TRANSPONDER_ITEM_GIVEN);
        this.transponderItemReturned = object.getBoolean(Team.TRANSPONDER_ITEM_RETURNED);
        this.transponderFeeReturned = object.getBoolean(Team.TRANSPONDER_FEE_RETURNED);

        this.energyMeterFeeGiven = object.getBoolean(Team.ENERGY_METER_FEE_GIVEN);
        this.energyMeterItemGiven = object.getBoolean(Team.ENERGY_METER_ITEM_GIVEN);
        this.energyMeterItemReturned = object.getBoolean(Team.ENERGY_METER_ITEM_RETURNED);
        this.energyMeterFeeReturned = object.getBoolean(Team.ENERGY_METER_FEE_RETURNED);

    }

    public Map<String, Object> toDocumentData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(Team.NAME, this.getName());
        docData.put(Team.CAR, this.getCar());

        docData.put(Team.SCRUTINEERING_PS, this.getScrutineeringPS());
        docData.put(Team.SCRUTINEERING_AI, this.getScrutineeringAI());
        docData.put(Team.SCRUTINEERING_EI, this.getScrutineeringEI());
        docData.put(Team.SCRUTINEERING_MI, this.getScrutineeringMI());
        docData.put(Team.SCRUTINEERING_TTT, this.getScrutineeringTTT());
        docData.put(Team.SCRUTINEERING_NT, this.getScrutineeringNT());
        docData.put(Team.SCRUTINEERING_RT, this.getScrutineeringRT());
        docData.put(Team.SCRUTINEERING_BT, this.getScrutineeringBT());

        docData.put(Team.SCRUTINEERING_PS_COMMENT, this.getScrutineeringPSComment());
        docData.put(Team.SCRUTINEERING_AI_COMMENT, this.getScrutineeringAIComment());
        docData.put(Team.SCRUTINEERING_EI_COMMENT, this.getScrutineeringEIComment());
        docData.put(Team.SCRUTINEERING_MI_COMMENT, this.getScrutineeringMIComment());
        docData.put(Team.SCRUTINEERING_TTT_COMMENT, this.getScrutineeringTTTComment());
        docData.put(Team.SCRUTINEERING_NT_COMMENT, this.getScrutineeringNTComment());
        docData.put(Team.SCRUTINEERING_RT_COMMENT, this.getScrutineeringRTComment());
        docData.put(Team.SCRUTINEERING_BT_COMMENT, this.getScrutineeringBTComment());
        
        docData.put(Team.TRANSPONDER_FEE_GIVEN, this.getTransponderFeeGiven());
        docData.put(Team.TRANSPONDER_ITEM_GIVEN, this.getTransponderItemGiven());
        docData.put(Team.TRANSPONDER_ITEM_RETURNED, this.getTransponderItemReturned());
        docData.put(Team.TRANSPONDER_FEE_RETURNED, this.getTransponderFeeReturned());

        docData.put(Team.ENERGY_METER_FEE_GIVEN, this.getEnergyMeterFeeGiven());
        docData.put(Team.ENERGY_METER_ITEM_GIVEN, this.getEnergyMeterItemGiven());
        docData.put(Team.ENERGY_METER_ITEM_RETURNED, this.getEnergyMeterItemReturned());
        docData.put(Team.ENERGY_METER_FEE_RETURNED, this.getEnergyMeterFeeReturned());

        return docData;
    }

    public List<FeeItem> getTransponderStates(){
        
        List<FeeItem> feeItems = new ArrayList<>();
        
        FeeItem tFeeGiven = FeeItem.TRANSPONDER_FEE_GIVEN;
        tFeeGiven.setValue(getTransponderFeeGiven());
        feeItems.add(tFeeGiven);

        FeeItem tItemGiven = FeeItem.TRANSPONDER_GIVEN;
        tItemGiven.setValue(getTransponderItemGiven());
        feeItems.add(tItemGiven);

        FeeItem tItemReturned = FeeItem.TRANSPONDER_RETURNED;
        tItemReturned.setValue(getTransponderItemReturned());
        feeItems.add(tItemReturned);

        FeeItem tFeeReturned = FeeItem.TRANSPONDER_FEE_RETURNED;
        tFeeReturned.setValue(getTransponderFeeReturned());
        feeItems.add(tFeeReturned);
        
        return feeItems;
    }

    public List<FeeItem> getEnergyMeterStates(){

        List<FeeItem> feeItems = new ArrayList<>();

        FeeItem emFeeGiven = FeeItem.ENERGY_METER_FEE_GIVEN;
        emFeeGiven.setValue(getEnergyMeterFeeGiven());
        feeItems.add(emFeeGiven);

        FeeItem emItemGiven = FeeItem.ENERGY_METER_GIVEN;
        emItemGiven.setValue(getEnergyMeterItemGiven());
        feeItems.add(emItemGiven);

        FeeItem emItemReturned = FeeItem.ENERGY_METER_RETURNED;
        emItemReturned.setValue(getEnergyMeterItemReturned());
        feeItems.add(emItemReturned);

        FeeItem emFeeReturned = FeeItem.ENERGY_METER_FEE_RETURNED;
        emFeeReturned.setValue(getEnergyMeterFeeReturned());
        feeItems.add(emFeeReturned);

        return feeItems;
    }



    @Override
    public Team clone() {
        final Team clone;
        try {
            clone = (Team) super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("superclass messed up", ex);
        }
        clone.car = this.car.clone();
        return clone;
    }


    public Team() {
        this.ID = UUID.randomUUID().toString();
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

    @Override
    public String toString() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Boolean getScrutineeringPS() {
        return scrutineeringPS;
    }

    public void setScrutineeringPS(Boolean scrutineeringPS) {
        this.scrutineeringPS = scrutineeringPS;
    }

    public Boolean getScrutineeringAI() {
        return scrutineeringAI;
    }

    public void setScrutineeringAI(Boolean scrutineeringAI) {
        this.scrutineeringAI = scrutineeringAI;
    }

    public Boolean getScrutineeringEI() {
        return scrutineeringEI;
    }

    public void setScrutineeringEI(Boolean scrutineeringEI) {
        this.scrutineeringEI = scrutineeringEI;
    }

    public Boolean getScrutineeringMI() {
        return scrutineeringMI;
    }

    public void setScrutineeringMI(Boolean scrutineeringMI) {
        this.scrutineeringMI = scrutineeringMI;
    }

    public Boolean getScrutineeringTTT() {
        return scrutineeringTTT;
    }

    public void setScrutineeringTTT(Boolean scrutineeringTTT) {
        this.scrutineeringTTT = scrutineeringTTT;
    }

    public Boolean getScrutineeringNT() {
        return scrutineeringNT;
    }

    public void setScrutineeringNT(Boolean scrutineeringNT) {
        this.scrutineeringNT = scrutineeringNT;
    }

    public Boolean getScrutineeringRT() {
        return scrutineeringRT;
    }

    public void setScrutineeringRT(Boolean scrutineeringRT) {
        this.scrutineeringRT = scrutineeringRT;
    }

    public Boolean getScrutineeringBT() {
        return scrutineeringBT;
    }

    public void setScrutineeringBT(Boolean scrutineeringBT) {
        this.scrutineeringBT = scrutineeringBT;
    }

    public String getScrutineeringPSComment() {
        return scrutineeringPSComment;
    }

    public void setScrutineeringPSComment(String scrutineeringPSComment) {
        this.scrutineeringPSComment = scrutineeringPSComment;
    }

    public String getScrutineeringAIComment() {
        return scrutineeringAIComment;
    }

    public void setScrutineeringAIComment(String scrutineeringAIComment) {
        this.scrutineeringAIComment = scrutineeringAIComment;
    }

    public String getScrutineeringEIComment() {
        return scrutineeringEIComment;
    }

    public void setScrutineeringEIComment(String scrutineeringEIComment) {
        this.scrutineeringEIComment = scrutineeringEIComment;
    }

    public String getScrutineeringMIComment() {
        return scrutineeringMIComment;
    }

    public void setScrutineeringMIComment(String scrutineeringMIComment) {
        this.scrutineeringMIComment = scrutineeringMIComment;
    }

    public String getScrutineeringTTTComment() {
        return scrutineeringTTTComment;
    }

    public void setScrutineeringTTTComment(String scrutineeringTTTComment) {
        this.scrutineeringTTTComment = scrutineeringTTTComment;
    }

    public String getScrutineeringNTComment() {
        return scrutineeringNTComment;
    }

    public void setScrutineeringNTComment(String scrutineeringNTComment) {
        this.scrutineeringNTComment = scrutineeringNTComment;
    }

    public String getScrutineeringRTComment() {
        return scrutineeringRTComment;
    }

    public void setScrutineeringRTComment(String scrutineeringRTComment) {
        this.scrutineeringRTComment = scrutineeringRTComment;
    }

    public String getScrutineeringBTComment() {
        return scrutineeringBTComment;
    }

    public void setScrutineeringBTComment(String scrutineeringBTComment) {
        this.scrutineeringBTComment = scrutineeringBTComment;
    }

    public List<ScrutineeringTest> getTests(){
        return ScrutineeringTest.getTestsByCarType(this.getCar().getType());
    }

    public Boolean getTransponderFeeGiven() {
        return transponderFeeGiven;
    }

    public void setTransponderFeeGiven(Boolean transponderFeeGiven) {
        this.transponderFeeGiven = transponderFeeGiven;
    }

    public Boolean getTransponderItemGiven() {
        return transponderItemGiven;
    }

    public void setTransponderItemGiven(Boolean transponderItemGiven) {
        this.transponderItemGiven = transponderItemGiven;
    }

    public Boolean getTransponderItemReturned() {
        return transponderItemReturned;
    }

    public void setTransponderItemReturned(Boolean transponderItemReturned) {
        this.transponderItemReturned = transponderItemReturned;
    }

    public Boolean getTransponderFeeReturned() {
        return transponderFeeReturned;
    }

    public void setTransponderFeeReturned(Boolean transponderFeeReturned) {
        this.transponderFeeReturned = transponderFeeReturned;
    }

    public Boolean getEnergyMeterFeeGiven() {
        return energyMeterFeeGiven;
    }

    public void setEnergyMeterFeeGiven(Boolean energyMeterFeeGiven) {
        this.energyMeterFeeGiven = energyMeterFeeGiven;
    }

    public Boolean getEnergyMeterItemGiven() {
        return energyMeterItemGiven;
    }

    public void setEnergyMeterItemGiven(Boolean energyMeterItemGiven) {
        this.energyMeterItemGiven = energyMeterItemGiven;
    }

    public Boolean getEnergyMeterItemReturned() {
        return energyMeterItemReturned;
    }

    public void setEnergyMeterItemReturned(Boolean energyMeterItemReturned) {
        this.energyMeterItemReturned = energyMeterItemReturned;
    }

    public Boolean getEnergyMeterFeeReturned() {
        return energyMeterFeeReturned;
    }

    public void setEnergyMeterFeeReturned(Boolean energyMeterFeeReturned) {
        this.energyMeterFeeReturned = energyMeterFeeReturned;
    }
}
