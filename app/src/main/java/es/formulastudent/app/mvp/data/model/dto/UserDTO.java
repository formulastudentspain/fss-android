package es.formulastudent.app.mvp.data.model.dto;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class UserDTO implements Serializable {

    private String uid;
    private String name;
    private String mail;
    private boolean mailVerified;
    private String photoUrl;
    private String phoneNumber;
    private String NFCTag;
    private boolean preScrutinering;


    public UserDTO(String uid, String name, String mail, boolean mailVerified, String photoUrl,
                   String phoneNumber, String NFCTag, boolean preScrutinering) {
        this.uid = uid;
        this.name = name;
        this.mail = mail;
        this.mailVerified = mailVerified;
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
        this.NFCTag = NFCTag;
        this.preScrutinering = preScrutinering;
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

    public boolean isPreScrutinering() {
        return preScrutinering;
    }

    public void setPreScrutinering(boolean preScrutinering) {
        this.preScrutinering = preScrutinering;
    }

    public static UserDTO createRandomUser(){
        UserDTO user = new UserDTO();
        int randomInt = ThreadLocalRandom.current().nextInt(0, 50);
        user.setName("User Name " + randomInt);
        user.setPhotoUrl("https://randomuser.me/api/portraits/men/"+randomInt+".jpg");
        user.setUid(UUID.randomUUID().toString());
        user.setNFCTag(UUID.randomUUID().toString());
        user.setPreScrutinering(new Random().nextBoolean());
        user.setMailVerified(new Random().nextBoolean());
        user.setMail("userName"+randomInt+"@gmail.com");
        return user;
    }
}
