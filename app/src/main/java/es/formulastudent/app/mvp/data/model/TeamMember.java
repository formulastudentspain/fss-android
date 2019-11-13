package es.formulastudent.app.mvp.data.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamMember implements Serializable {


    //Database constants
    public static final String TEAM_ID = "teamID";
    public static final String TEAM = "team";
    public static final String NAME = "name";
    public static final String USER_IMAGE = "imageURL";
    public static final String TAG_NFC = "tagNFC";
    public static final String MAIL = "mail";
    public static final String ROLE = "role";
    public static final String ROLE_ID = "roleID";
    public static final String CAR_NUMBER = "carNumber";


    private String ID;
    private String name;
    private String mail;
    private String photoUrl;
    private String NFCTag;
    private String team;
    private String teamID;
    private String role;
    private String roleID;
    private Long carNumber;


    public TeamMember(String ID, String name, String mail, String photoUrl, String NFCTag, String role, String roleID) {
        this.ID = ID;
        this.name = name;
        this.mail = mail;
        this.photoUrl = photoUrl;
        this.NFCTag = NFCTag;
        this.role = role;
        this.roleID = roleID;
    }

    public TeamMember(DocumentSnapshot object){
        this.ID = object.getId();
        this.name = object.getString(TeamMember.NAME);
        this.NFCTag = object.getString(TeamMember.TAG_NFC);
        this.mail = object.getString(TeamMember.MAIL);
        this.photoUrl = object.getString(TeamMember.USER_IMAGE);
        this.team = object.getString(TeamMember.TEAM);
        this.teamID = object.getString(TeamMember.TEAM_ID);
        this.role = object.getString(TeamMember.ROLE);
        this.roleID = object.getString(TeamMember.ROLE_ID);
        this.carNumber = object.getLong(TeamMember.CAR_NUMBER);
    }

    public Map<String, Object> toDocumentData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(TeamMember.NAME, this.getName());
        docData.put(TeamMember.MAIL, this.getMail());
        docData.put(TeamMember.ROLE, this.getRole());
        docData.put(TeamMember.ROLE_ID, this.getRoleID());
        docData.put(TeamMember.TEAM, this.getTeam());
        docData.put(TeamMember.TEAM_ID, this.getTeamID());
        docData.put(TeamMember.USER_IMAGE, this.getPhotoUrl());
        docData.put(TeamMember.TAG_NFC, this.getNFCTag());
        docData.put(TeamMember.CAR_NUMBER, this.getCarNumber());

        return docData;
    }


    public TeamMember() {
        this.ID = UUID.randomUUID().toString();
    }

    public TeamMember(FirebaseUser firebaseUser){
        this.name = firebaseUser.getDisplayName();
        this.mail = firebaseUser.getEmail();
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNFCTag() {
        return NFCTag;
    }

    public void setNFCTag(String NFCTag) {
        this.NFCTag = NFCTag;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public Long getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Long carNumber) {
        this.carNumber = carNumber;
    }
}
