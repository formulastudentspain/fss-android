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


    private String ID;
    private String name;
    private String mail;
    private String photoUrl;
    private String role;


    public User(String ID, String name, String mail, String photoUrl, String role) {
        this.ID = ID;
        this.name = name;
        this.mail = mail;
        this.photoUrl = photoUrl;
        this.role = role;
    }

    public User(DocumentSnapshot object){
        this.ID = object.getId();
        this.name = object.getString(TeamMember.NAME);
        this.mail = object.getString(TeamMember.MAIL);
        this.photoUrl = object.getString(TeamMember.USER_IMAGE);
        this.role = object.getString(TeamMember.ROLE);
    }

    public Map<String, Object> toDocumentData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(TeamMember.NAME, this.getName());
        docData.put(TeamMember.MAIL, this.getMail());
        docData.put(TeamMember.ROLE, this.getRole());
        docData.put(TeamMember.USER_IMAGE, this.getPhotoUrl());

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
