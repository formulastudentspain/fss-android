package code.formulastudentspain.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public static final String LOG_CONES = "cones";
    public static final String LOG_OFFCOURSES = "offcourses";
    public static final String LOG_DATE = "date";
    public static final String LOG = "log";







    private String id;
    private Long sectorNumber;
    private Long carNumber;
    private String flagURL;
    private String raceRound;
    private Boolean isRacing;
    private Long trafficCones;
    private Long offCourses;
    private List<ConeControlRegisterLog> logs;

    //Values to draw the item in the list, not to persist
    private int state = 0; //0=active, 1=modifying, 2=saving
    private int currentConesCount;
    private int currentOffCourseCount;



    public ConeControlRegister(DocumentSnapshot documentSnapshot) {

        this.id = documentSnapshot.getId();
        this.sectorNumber = documentSnapshot.getLong(ConeControlRegister.SECTOR_NUMBER);
        this.carNumber = documentSnapshot.getLong(ConeControlRegister.CAR_NUMBER);
        this.flagURL = documentSnapshot.getString(ConeControlRegister.FLAG_URL);
        this.raceRound = documentSnapshot.getString(ConeControlRegister.RACE_ROUND);
        this.isRacing = documentSnapshot.getBoolean(ConeControlRegister.IS_RACING);
        this.trafficCones = documentSnapshot.getLong(ConeControlRegister.TRAFFIC_CONES);
        this.offCourses = documentSnapshot.getLong(ConeControlRegister.OFF_COURSES);

        //Log
        List<Map<String, Object>> logRegisters = (List<Map<String, Object>>) documentSnapshot.get("log");

        List<ConeControlRegisterLog> logs = new ArrayList<>();
        if(logRegisters != null){
            for(Map<String, Object> logRegister: logRegisters){
                ConeControlRegisterLog log = new ConeControlRegisterLog();
                log.setCones((Long) logRegister.get(LOG_CONES));
                log.setOffcourses((Long) logRegister.get(LOG_OFFCOURSES));
                Timestamp timestamp = (Timestamp) logRegister.get(LOG_DATE);
                log.setDate(timestamp.toDate());
                logs.add(log);
            }
        }

        this.logs = logs;
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

        docData.put(ConeControlRegister.LOG, this.logs);

        return docData;
    }

    public ConeControlRegister() {
        this.id = UUID.randomUUID().toString();
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCurrentConesCount() {
        return currentConesCount;
    }

    public void setCurrentConesCount(int currentConesCount) {
        this.currentConesCount = currentConesCount;
    }

    public int getCurrentOffCourseCount() {
        return currentOffCourseCount;
    }

    public void setCurrentOffCourseCount(int currentOffCourseCount) {
        this.currentOffCourseCount = currentOffCourseCount;
    }

    public List<ConeControlRegisterLog> getLogs() {
        return logs;
    }

    public void setLogs(List<ConeControlRegisterLog> logs) {
        this.logs = logs;
    }

}
