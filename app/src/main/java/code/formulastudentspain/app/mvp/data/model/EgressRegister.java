package code.formulastudentspain.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EgressRegister implements Serializable {


    //Database constants
    public static final String ID = "id";
    public static final String PRESCRUTINEERING_ID = "prescrutineeringId";
    public static final String FIRST_ATTEMPT = "firstAttempt";
    public static final String SECOND_ATTEMPT = "secondAttempt";
    public static final String THIRD_ATTEMPT = "thirdAttempt";


    private String id;
    private String preScrutineeringRegisterID;
    private Long firstAttempt;
    private Long secondAttempt;
    private Long thirdAttempt;

    public EgressRegister(){}

    public EgressRegister(String preScrutineeringRegisterID) {
        this.id = UUID.randomUUID().toString();
        this.preScrutineeringRegisterID = preScrutineeringRegisterID;
    }

    public EgressRegister(DocumentSnapshot documentSnapshot) {

        this.id = documentSnapshot.getId();
        this.preScrutineeringRegisterID = documentSnapshot.getString(EgressRegister.PRESCRUTINEERING_ID);
        this.firstAttempt = documentSnapshot.getLong(EgressRegister.FIRST_ATTEMPT);
        this.secondAttempt = documentSnapshot.getLong(EgressRegister.SECOND_ATTEMPT);
        this.thirdAttempt = documentSnapshot.getLong(EgressRegister.THIRD_ATTEMPT);
    }

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(EgressRegister.ID, this.id);
        docData.put(EgressRegister.PRESCRUTINEERING_ID, this.preScrutineeringRegisterID);
        docData.put(EgressRegister.FIRST_ATTEMPT, this.firstAttempt);
        docData.put(EgressRegister.SECOND_ATTEMPT, this.secondAttempt);
        docData.put(EgressRegister.THIRD_ATTEMPT, this.thirdAttempt);

        return docData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreScrutineeringRegisterID() {
        return preScrutineeringRegisterID;
    }

    public void setPreScrutineeringRegisterID(String preScrutineeringRegisterID) {
        this.preScrutineeringRegisterID = preScrutineeringRegisterID;
    }

    public Long getFirstAttempt() {
        return firstAttempt;
    }

    public void setFirstAttempt(Long firstAttempt) {
        this.firstAttempt = firstAttempt;
    }

    public Long getSecondAttempt() {
        return secondAttempt;
    }

    public void setSecondAttempt(Long secondAttempt) {
        this.secondAttempt = secondAttempt;
    }

    public Long getThirdAttempt() {
        return thirdAttempt;
    }

    public void setThirdAttempt(Long thirdAttempt) {
        this.thirdAttempt = thirdAttempt;
    }
}