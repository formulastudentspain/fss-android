package es.formulastudent.app.mvp.data.model;

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

    private String ID;
    private String teamID;
    private String team;
    private String userID;
    private String user;
    private String userImage;
    private EventType type;
    private Date date;


    public EventRegister(String teamID, String team, String userID, String user, String userImage, Date date) {
        this.teamID = teamID;
        this.team = team;
        this.userID = userID;
        this.user = user;
        this.date = date;
        this.userImage = userImage;
        this.date = Calendar.getInstance().getTime();
        this.ID = UUID.randomUUID().toString();
    }

    public Map<String, Object> toObjectData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(EventRegister.USER, this.user);
        docData.put(EventRegister.USER_ID, this.userID);
        docData.put(EventRegister.TEAM, this.team);
        docData.put(EventRegister.TEAM_ID, this.teamID);
        docData.put(EventRegister.USER_IMAGE, this.userImage);
        docData.put(EventRegister.DATE, this.date.getTime());

        return docData;
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
}