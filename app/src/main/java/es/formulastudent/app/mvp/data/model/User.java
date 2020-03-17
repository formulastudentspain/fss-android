package es.formulastudent.app.mvp.data.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User implements Serializable {


    //Database constants
    public static final String NAME = "name";
    public static final String USER_IMAGE = "imageURL";
    public static final String MAIL = "mail";
    public static final String ROLE = "role";
    public static final String WALKIE = "walkie";
    public static final String CELL_PHONE = "cellPhone";
    public static final String TEAM_ID = "teamId";


    private String ID;
    private String name;
    private String mail;
    private String photoUrl;
    private UserRole role;
    private Long walkie;
    private Long cellPhone;
    private String teamId; //just for team leaders
    private Team team; //just for team leaders (not persisted)



    public User(DocumentSnapshot object){
        this.ID = object.getId();
        this.name = object.getString(User.NAME);
        this.mail = object.getString(User.MAIL);
        this.photoUrl = object.getString(User.USER_IMAGE);
        this.cellPhone = object.getLong(User.CELL_PHONE);
        this.walkie = object.getLong(User.WALKIE);
        this.teamId = object.getString(User.TEAM_ID);

        //Getting role from enum
        String roleString = object.getString(TeamMember.ROLE);
        this.role = (UserRole) UserRole.getRoleByName(roleString);
    }

    public Map<String, Object> toDocumentData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(User.NAME, this.getName());
        docData.put(User.MAIL, this.getMail());
        docData.put(User.ROLE, this.getRole()==null ? null : this.getRole().getName());
        docData.put(User.USER_IMAGE, this.getPhotoUrl());
        docData.put(User.CELL_PHONE, this.getCellPhone());
        docData.put(User.WALKIE, this.getWalkie());
        docData.put(User.TEAM_ID, this.getTeamId());

        return docData;
    }


    public User() {
        this.ID = UUID.randomUUID().toString();
    }

    public User(FirebaseUser firebaseUser){
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getWalkie() {
        return walkie;
    }

    public void setWalkie(Long walkie) {
        this.walkie = walkie;
    }

    public Long getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(Long cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
