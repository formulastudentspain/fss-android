package es.formulastudent.app.mvp.data.business.team;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface TeamBO {

    /**
     * Method to retrieve all teams
     * @param callback
     */
    void retrieveAllTeams(BusinessCallback callback);


    /**
     * Method to retrieve a team by ID
     * @param id
     * @param callback
     */
    void retrieveTeamById(String id, BusinessCallback callback);
}
