package es.formulastudent.app.mvp.data.model.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String uid;
    private String name;
    private String mail;
    private boolean mailVerified;
    private String photoUrl;
    private String phoneNumber;
    private String NFCTag;


    public UserDTO(String uid, String name, String mail, boolean mailVerified, String photoUrl, String phoneNumber, String NFCTag) {
        this.uid = uid;
        this.name = name;
        this.mail = mail;
        this.mailVerified = mailVerified;
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
        this.NFCTag = NFCTag;
    }

    public UserDTO() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public boolean isMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNFCTag() {
        return NFCTag;
    }

    public void setNFCTag(String NFCTag) {
        this.NFCTag = NFCTag;
    }
}
