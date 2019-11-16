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
    public static final String WITH_WALKIE = "withWalkie";
    public static final String WITH_CELL_PHONE = "withCellPhone";


    private String ID;
    private String name;
    private String mail;
    private String photoUrl;
    private Role role;
    private Boolean withWalkie;
    private Boolean withCellPhone;



    public User(DocumentSnapshot object){
        this.ID = object.getId();
        this.name = object.getString(User.NAME);
        this.mail = object.getString(User.MAIL);
        this.photoUrl = object.getString(User.USER_IMAGE);
        this.withCellPhone = object.getBoolean(User.WITH_CELL_PHONE);
        this.withWalkie = object.getBoolean(User.WITH_WALKIE);

        //Getting role from enum
        String roleString = object.getString(TeamMember.ROLE);
        this.role = Role.getRoleByName(roleString);
    }

    public Map<String, Object> toDocumentData(){

        Map<String, Object> docData = new HashMap<>();
        docData.put(User.NAME, this.getName());
        docData.put(User.MAIL, this.getMail());
        docData.put(User.ROLE, this.getRole().getName());
        docData.put(User.USER_IMAGE, this.getPhotoUrl());
        docData.put(User.WITH_CELL_PHONE, this.getWithCellPhone());
        docData.put(User.WITH_WALKIE, this.getWithWalkie());

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getWithWalkie() {
        return withWalkie;
    }

    public void setWithWalkie(Boolean withWalkie) {
        this.withWalkie = withWalkie;
    }

    public Boolean getWithCellPhone() {
        return withCellPhone;
    }

    public void setWithCellPhone(Boolean withCellPhone) {
        this.withCellPhone = withCellPhone;
    }
}
