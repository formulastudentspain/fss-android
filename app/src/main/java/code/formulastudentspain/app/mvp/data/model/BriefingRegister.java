package code.formulastudentspain.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class BriefingRegister extends EventRegister implements Serializable {



    public BriefingRegister(TeamMember teamMember, Date date, String registerUserMail) {
        super(teamMember.getTeamID(), teamMember.getTeam(), teamMember.getID(), teamMember.getName(), teamMember.getPhotoUrl(),
                date, null, null, EventType.BRIEFING, registerUserMail);
    }

    public Map<String, Object> toObjectData(){
        return super.toObjectData();
    }

    public BriefingRegister(DocumentSnapshot object){
        super(object, EventType.BRIEFING);
    }


}