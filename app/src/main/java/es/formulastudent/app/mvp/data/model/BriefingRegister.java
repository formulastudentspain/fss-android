package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class BriefingRegister extends EventRegister implements Serializable {

    public static String COLLECTION_ID = "EVENT_CONTROL_BRIEFING";

    public BriefingRegister(String teamID, String team, String userID, String user, String userImage, Date date) {
        super(teamID, team, userID, user, userImage, date);
        setType(EventType.BRIEFING);
    }

    public BriefingRegister(User user, Date date) {
        super(user.getTeamID(), user.getTeam(), user.getID(), user.getName(), user.getPhotoUrl(), date);
        setType(EventType.BRIEFING);
    }

    public Map<String, Object> toObjectData(){
        return super.toObjectData();
    }


    public BriefingRegister(DocumentSnapshot object){
        super(object);
        setType(EventType.BRIEFING);
    }


}