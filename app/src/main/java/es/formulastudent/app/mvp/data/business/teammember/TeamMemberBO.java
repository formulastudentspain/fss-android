package es.formulastudent.app.mvp.data.business.teammember;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;

public interface TeamMemberBO extends DataLoader.Consumer{


    /**
     * Retrieve team member by NFC tag
     * @param tag
     * @param callback
     */
    void retrieveTeamMemberByNFCTag(String tag, BusinessCallback callback);


    /**
     * Retrieve all team member
     * @param selectedTeam
     * @param callback
     */
    void retrieveTeamMembers(Team selectedTeam, BusinessCallback callback);


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

    /**
     * Method to update the team member
     * @param teamMember
     * @param callback
     */
    void updateTeamMember(TeamMember teamMember, BusinessCallback callback);
}