package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import es.formulastudent.app.mvp.data.model.dto.UserDTO;

public abstract class EventRegister implements Serializable {

    private UUID ID;
    private Date date;
    private UserDTO user;
    private EventType type;


    public EventRegister(UUID ID, Date date, UserDTO user) {
        this.ID = ID;
        this.date = date;
        this.user = user;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}