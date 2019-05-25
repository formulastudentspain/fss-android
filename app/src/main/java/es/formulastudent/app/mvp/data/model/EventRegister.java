package es.formulastudent.app.mvp.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class EventRegister implements Serializable {

    //Database constants
    public static final String TEAM_ID = "teamID";
    public static final String TEAM = "team";
    public static final String USER_ID = "userID";
    public static final String USER = "userName";
    public static final String DATE = "date";
    public static final String USER_IMAGE = "userImage";
    public static final String CAR_TYPE = "carType";
    public static final String CAR_NUMBER = "carNumber";
    public static final String BRIEFING_DONE = "briefingDone";



    private String ID;
    private String teamID;
    private String team;
    private String userID;
    private String user;
    private String userImage;
    private EventType type;
    private Date date;
    private String carType;
    private Long carNumber;
    private Boolean briefingDone;


    public EventRegister(String teamID, String team, String userID, String user, String userImage,
                         Date date, String carType, Long carNumber, Boolean briefingDone) {
        this.teamID = teamID;
        this.team = team;
        this.userID = userID;
        this.user = user;
        this.date = date;
        this.userImage = userImage;
        this.date = Calendar.getInstance().getTime();
        this.ID = UUID.randomUUID().toString();
        this.carType = carType;
        this.carNumber = carNumber;
        this.briefingDone = briefingDone;
    }

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(EventRegister.USER, this.user);
        docData.put(EventRegister.USER_ID, this.userID);
        docData.put(EventRegister.TEAM, this.team);
        docData.put(EventRegister.TEAM_ID, this.teamID);
        docData.put(EventRegister.USER_IMAGE, this.userImage);
        docData.put(EventRegister.DATE, new Timestamp(this.date));
        docData.put(EventRegister.CAR_NUMBER, this.carNumber);
        docData.put(EventRegister.CAR_TYPE, this.carType);
        docData.put(EventRegister.BRIEFING_DONE, this.briefingDone);

        return docData;
    }

    public EventRegister(DocumentSnapshot object){

        this.date = object.getDate(EventRegister.DATE);
        this.userImage = object.getString(EventRegister.USER_IMAGE);
        this.user = object.getString(EventRegister.USER);
        this.userID = object.getString(EventRegister.USER_ID);
        this.team = object.getString(EventRegister.TEAM);
        this.teamID = object.getString(EventRegister.TEAM_ID);
        this.carNumber = object.getLong(EventRegister.CAR_NUMBER);
        this.carType = object.getString(EventRegister.CAR_TYPE);
        this.briefingDone = object.getBoolean(EventRegister.BRIEFING_DONE);


    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
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

    public Boolean getBriefingDone() {
        return briefingDone;
    }

    public void setBriefingDone(Boolean briefingDone) {
        this.briefingDone = briefingDone;
    }
}