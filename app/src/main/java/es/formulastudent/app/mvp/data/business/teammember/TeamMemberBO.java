package es.formulastudent.app.mvp.data.business.teammember;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.TeamMember;

public interface TeamMemberBO {


    /**
     * Retrieve team member by NFC tag
     * @param tag
     * @param callback
     */
    void retrieveTeamMemberByNFCTag(String tag, BusinessCallback callback);


    /**
     * Retrieve all team member
     * @param callback
     */
    void retrieveTeamMembers(BusinessCallback callback);


    /**
     * Create team member
     * @param teamMember
     * @param callback
     */
    void createTeamMember(TeamMember teamMember, BusinessCallback callback);


    /**
     * Method to delete all team members
     * @param callback
     */
    void deleteAllTeamMembers(BusinessCallback callback);

    /**
     * Method to get the team members register by a team (max. 6)
     * @param teamID
     * @param callback
     */
    void getRegisteredTeamMemberByTeamId(String teamID, BusinessCallback callback);
}