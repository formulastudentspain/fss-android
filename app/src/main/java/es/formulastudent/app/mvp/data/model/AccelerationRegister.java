package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class AccelerationRegister extends EventRegister implements Serializable {

/*
    public AccelerationRegister(String teamID, String team, String userID, String user, String userImage, Date date) {
        super(teamID, team, userID, user, userImage, date);
        setType(EventType.ACCELERATION);
    }
    */

    public AccelerationRegister(User user, Date date, String carType, Long carNumber) {
        super(user.getTeamID(), user.getTeam(), user.getID(), user.getName(), user.getPhotoUrl(),
                date, carType, carNumber);
        setType(EventType.ACCELERATION);
    }

    public AccelerationRegister(AccelerationRegister register, Date date) {
        super(register.getTeamID(), register.getTeam(), register.getID(), register.getUser(),
                register.getUserImage(), date, register.getCarType(), register.getCarNumber());
        setType(EventType.ACCELERATION);
    }

    public Map<String, Object> toObjectData(){
        return super.toObjectData();
    }

    public AccelerationRegister(DocumentSnapshot object){
        super(object);
        setType(EventType.ACCELERATION);
    }


}