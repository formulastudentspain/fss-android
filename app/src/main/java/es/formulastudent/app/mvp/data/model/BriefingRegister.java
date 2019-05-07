package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import es.formulastudent.app.mvp.data.model.dto.UserDTO;

public class BriefingRegister extends EventRegister implements Serializable {

    public BriefingRegister(UUID ID, Date date, UserDTO userDTO) {
        super(ID, date, userDTO);
        super.setType(EventType.BRIEFING);
    }

}