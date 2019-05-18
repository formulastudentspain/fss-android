package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class EnduranceRegister extends EventRegister implements Serializable {


    public EnduranceRegister(String teamID, String team, String userID, String user, String userImage, Date date) {
        super(teamID, team, userID, user, userImage, date);
        setType(EventType.ENDURANCE_EFFICIENCY);
    }

    public EnduranceRegister(User user, Date date) {
        super(user.getTeamID(), user.getTeam(), user.getID(), user.getName(), user.getPhotoUrl(), date);
        setType(EventType.ENDURANCE_EFFICIENCY);
    }

    public Map<String, Object> toObjectData(){
        return super.toObjectData();
    }

    public EnduranceRegister(DocumentSnapshot object){
        super(object);
        setType(EventType.ENDURANCE_EFFICIENCY);
    }


}