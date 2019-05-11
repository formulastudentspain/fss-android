package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class User implements Serializable {

    public static String COLLECTION_ID = "UserInfo";

    //Database constants
    public static final String TEAM_ID = "teamID";
    public static final String TEAM = "team";
    public static final String NAME = "name";
    public static final String USER_IMAGE = "imageURL";
    public static final String TAG_NFC = "tagNFC";
    public static final String MAIL = "mail";
    public static final String ROLE = "role";


    private String ID;
    private String name;
    private String mail;
    private String photoUrl;
    private String NFCTag;
    private String team;
    private String teamID;
    private String role;


    public User(String ID, String name, String mail, String photoUrl, String NFCTag, String role) {
        this.ID = ID;
        this.name = name;
        this.mail = mail;
        this.photoUrl = photoUrl;
        this.NFCTag = NFCTag;
        this.role = role;
    }

    public User(DocumentSnapshot object){
        this.name = (String) object.get(User.NAME);
        this.NFCTag = (String) object.get(User.TAG_NFC);
        this.mail = (String) object.get(User.MAIL);
        this.photoUrl = (String) object.get(User.USER_IMAGE);
        this.team = (String) object.get(User.TEAM);
        this.teamID = (String) object.get(User.TEAM_ID);
        this.role = (String) object.get(User.ROLE);
    }

    public User() {
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

    public static User createRandomUser(){
        User user = new User();
        int randomInt = ThreadLocalRandom.current().nextInt(0, 50);
        user.setName("User Name " + randomInt);
        user.setPhotoUrl("https://randomuser.me/api/portraits/men/"+randomInt+".jpg");
        user.setNFCTag(UUID.randomUUID().toString());
        user.setMail("userName"+randomInt+"@gmail.com");
        return user;
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
}
