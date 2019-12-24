package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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




    private String ID;
    private String name;
    private Car car;

    //Passes
    private Date scrutineeringPS; //Pre-Scrutineering (C, DC, E, DE)
    private Date scrutineeringAI; //Accumulation Inspection (E, DE)
    private Date scrutineeringEI; //Electrical Inspection (E, DE)
    private Date scrutineeringMI; //Mechanical Inspection (C, DC, E, DE)
    private Date scrutineeringTTT; //Tilt Table Test (C, DC, E, DE)
    private Date scrutineeringNT; //Noise Test (C, DC)
    private Date scrutineeringRT; //Rain Test (E, DE)
    private Date scrutineeringBT; //Brake Test (C, DC, E, DE)

    //Comments
    private String scrutineeringPSComment; //Pre-Scrutineering (C, DC, E, DE)
    private String scrutineeringAIComment; //Accumulation Inspection (E, DE)
    private String scrutineeringEIComment; //Electrical Inspection (E, DE)
    private String scrutineeringMIComment; //Mechanical Inspection (C, DC, E, DE)
    private String scrutineeringTTTComment; //Tilt Table Test (C, DC, E, DE)
    private String scrutineeringNTComment; //Noise Test (C, DC)
    private String scrutineeringRTComment; //Rain Test (E, DE)
    private String scrutineeringBTComment; //Brake Test (C, DC, E, DE)


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

        this.scrutineeringPS = object.getDate(Team.SCRUTINEERING_PS);
        this.scrutineeringAI = object.getDate(Team.SCRUTINEERING_AI);
        this.scrutineeringEI = object.getDate(Team.SCRUTINEERING_EI);
        this.scrutineeringMI = object.getDate(Team.SCRUTINEERING_MI);
        this.scrutineeringTTT = object.getDate(Team.SCRUTINEERING_TTT);
        this.scrutineeringNT = object.getDate(Team.SCRUTINEERING_NT);
        this.scrutineeringRT = object.getDate(Team.SCRUTINEERING_RT);
        this.scrutineeringBT = object.getDate(Team.SCRUTINEERING_BT);

        this.scrutineeringPSComment = object.getString(Team.SCRUTINEERING_PS_COMMENT);
        this.scrutineeringAIComment = object.getString(Team.SCRUTINEERING_AI_COMMENT);
        this.scrutineeringEIComment = object.getString(Team.SCRUTINEERING_EI_COMMENT);
        this.scrutineeringMIComment = object.getString(Team.SCRUTINEERING_MI_COMMENT);
        this.scrutineeringTTTComment = object.getString(Team.SCRUTINEERING_TTT_COMMENT);
        this.scrutineeringNTComment = object.getString(Team.SCRUTINEERING_NT_COMMENT);
        this.scrutineeringRTComment = object.getString(Team.SCRUTINEERING_RT_COMMENT);
        this.scrutineeringBTComment = object.getString(Team.SCRUTINEERING_BT_COMMENT);

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

        return docData;
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

    public Date getScrutineeringPS() {
        return scrutineeringPS;
    }

    public void setScrutineeringPS(Date scrutineeringPS) {
        this.scrutineeringPS = scrutineeringPS;
    }

    public Date getScrutineeringAI() {
        return scrutineeringAI;
    }

    public void setScrutineeringAI(Date scrutineeringAI) {
        this.scrutineeringAI = scrutineeringAI;
    }

    public Date getScrutineeringEI() {
        return scrutineeringEI;
    }

    public void setScrutineeringEI(Date scrutineeringEI) {
        this.scrutineeringEI = scrutineeringEI;
    }

    public Date getScrutineeringMI() {
        return scrutineeringMI;
    }

    public void setScrutineeringMI(Date scrutineeringMI) {
        this.scrutineeringMI = scrutineeringMI;
    }

    public Date getScrutineeringTTT() {
        return scrutineeringTTT;
    }

    public void setScrutineeringTTT(Date scrutineeringTTT) {
        this.scrutineeringTTT = scrutineeringTTT;
    }

    public Date getScrutineeringNT() {
        return scrutineeringNT;
    }

    public void setScrutineeringNT(Date scrutineeringNT) {
        this.scrutineeringNT = scrutineeringNT;
    }

    public Date getScrutineeringRT() {
        return scrutineeringRT;
    }

    public void setScrutineeringRT(Date scrutineeringRT) {
        this.scrutineeringRT = scrutineeringRT;
    }

    public Date getScrutineeringBT() {
        return scrutineeringBT;
    }

    public void setScrutineeringBT(Date scrutineeringBT) {
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
}
