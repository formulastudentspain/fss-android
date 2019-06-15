package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class BriefingRegister extends EventRegister implements Serializable {



    public BriefingRegister(User user, Date date) {
        super(user.getTeamID(), user.getTeam(), user.getID(), user.getName(), user.getPhotoUrl(),
                date, null, null, null, EventType.BRIEFING);
    }

    public Map<String, Object> toObjectData(){
        return super.toObjectData();
    }

    public BriefingRegister(DocumentSnapshot object){
        super(object, EventType.BRIEFING);
    }


}