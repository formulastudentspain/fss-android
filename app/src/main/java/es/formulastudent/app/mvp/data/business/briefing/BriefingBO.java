package es.formulastudent.app.mvp.data.business.briefing;

import java.util.Date;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.User;

public interface BriefingBO {


    /**
     * Method to retrieve Briefing registers
     * @param from:    From date
     * @param to:      To date
     * @param teamID:    Selected teamID
     * @param callback
     */
    void retrieveBriefingRegisters(Date from, Date to, String teamID, BusinessCallback callback);


    /**
     * Method to create a Briefing registry
     * @param user
     * @param callback
     */
    void createBriefingRegistry(User user, BusinessCallback callback);
}