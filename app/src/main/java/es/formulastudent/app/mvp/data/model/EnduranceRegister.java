package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class EnduranceRegister extends EventRegister implements Serializable {

    public EnduranceRegister(User user, Date date, String carType, Long carNumber, Boolean briefingDone) {
        super(user.getTeamID(), user.getTeam(), user.getID(), user.getName(), user.getPhotoUrl(), date,
                carType, carNumber, briefingDone);
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