package es.formulastudent.app.mvp.data.business.briefing;

import java.util.Date;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.model.TeamMember;

public interface BriefingBO extends DataLoader.Consumer {


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
     * @param teamMember
     * @param callback
     */
    void createBriefingRegistry(TeamMember teamMember, String registerUserMail, BusinessCallback callback);


    /**
     * Retrieve briefing registers by user and dates
     * @param userID
     * @param callback
     */
    void checkBriefingByUser(String userID, BusinessCallback callback);


    /**
     * Delete Briefing register
     * @param userID
     * @param callback
     */
    void deleteBriefingRegister(String userID, BusinessCallback callback);

}

