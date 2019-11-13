package es.formulastudent.app.mvp.data.business.user;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.TeamMember;

public interface UserBO {


    /**
     * Retrieve user by NFC tag
     * @param tag
     * @param callback
     */
    void retrieveUserByNFCTag(String tag, BusinessCallback callback);


    /**
     * Retrieve all users
     * @param callback
     */
    void retrieveUsers(BusinessCallback callback);


    /**
     * Create teamMember
     * @param teamMember
     * @param callback
     */
    void createUser(TeamMember teamMember, BusinessCallback callback);


    /**
     * Method to retrieve user by mail
     * @param mail
     * @param callback
     */
    void retrieveUserByMail(String mail, BusinessCallback callback);


    /**
     * Method to delete all drivers
     * @param callback
     */
    void deleteAllDrivers(BusinessCallback callback);

    /**
     * Method to get the users register by a team (max. 6)
     * @param teamID
     * @param callback
     */
    void getRegisteredUsersByTeamId(String teamID, BusinessCallback callback);
}