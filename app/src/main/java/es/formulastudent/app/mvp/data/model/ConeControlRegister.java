package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConeControlRegister implements Serializable {

    //Constants
    public static final String ROUND_1 = "round1";
    public static final String ROUND_2 = "round2";
    public static final String ROUND_FINAL = "final";


    //Database constants
    public static final String ID = "id";
    public static final String SECTOR_NUMBER = "sectorNumber";
    public static final String CAR_NUMBER = "carNumber";
    public static final String FLAG_URL = "flagUrl";
    public static final String RACE_ROUND = "raceRound";
    public static final String IS_RACING = "isRacing";
    public static final String TRAFFIC_CONES = "trafficCones";
    public static final String OFF_COURSES = "offCourses";


    private String id;
    private Long sectorNumber;
    private Long carNumber;
    private String flagURL;
    private String raceRound;
    private Boolean isRacing;
    private Long trafficCones;
    private Long offCourses;

    //TODO listado con registros para log


    public ConeControlRegister(DocumentSnapshot documentSnapshot) {

        this.id = documentSnapshot.getId();
        this.sectorNumber = documentSnapshot.getLong(ConeControlRegister.SECTOR_NUMBER);
        this.carNumber = documentSnapshot.getLong(ConeControlRegister.CAR_NUMBER);
        this.flagURL = documentSnapshot.getString(ConeControlRegister.FLAG_URL);
        this.raceRound = documentSnapshot.getString(ConeControlRegister.RACE_ROUND);
        this.isRacing = documentSnapshot.getBoolean(ConeControlRegister.IS_RACING);
        this.trafficCones = documentSnapshot.getLong(ConeControlRegister.TRAFFIC_CONES);
        this.offCourses = documentSnapshot.getLong(ConeControlRegister.OFF_COURSES);

    }

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(ConeControlRegister.ID, this.id);
        docData.put(ConeControlRegister.SECTOR_NUMBER, this.sectorNumber);
        docData.put(ConeControlRegister.CAR_NUMBER, this.carNumber);
        docData.put(ConeControlRegister.FLAG_URL, this.flagURL);
        docData.put(ConeControlRegister.RACE_ROUND, this.raceRound);
        docData.put(ConeControlRegister.IS_RACING, this.isRacing);
        docData.put(ConeControlRegister.TRAFFIC_CONES, this.trafficCones);
        docData.put(ConeControlRegister.OFF_COURSES, this.offCourses);

        return docData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSectorNumber() {
        return sectorNumber;
    }

    public void setSectorNumber(Long sectorNumber) {
        this.sectorNumber = sectorNumber;
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

    public String getRaceRound() {
        return raceRound;
    }

    public void setRaceRound(String raceRound) {
        this.raceRound = raceRound;
    }

    public Boolean getIsRacing() {
        return isRacing;
    }

    public void setIsRacing(Boolean isRacing) {
        this.isRacing = isRacing;
    }

    public Long getTrafficCones() {
        return trafficCones;
    }

    public void setTrafficCones(Long trafficCones) {
        this.trafficCones = trafficCones;
    }

    public Long getOffCourses() {
        return offCourses;
    }

    public void setOffCourses(Long offCourses) {
        this.offCourses = offCourses;
    }
}
