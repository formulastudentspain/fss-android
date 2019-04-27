package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TimelineItem implements Serializable {

    private UUID ID;
    private String title;
    private String message;
    private Date date;
    private String photoURL;
    private TimelineItemType type;

    public TimelineItem() {
    }

    public TimelineItem(String title, Date date, TimelineItemType type, String photoURL, String message) {
        this.title = title;
        this.date = date;
        this.type = type;
        this.photoURL = photoURL;
        this.message = message;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public TimelineItemType getType() {
        return type;
    }

    public void setType(TimelineItemType type) {
        this.type = type;
    }
}