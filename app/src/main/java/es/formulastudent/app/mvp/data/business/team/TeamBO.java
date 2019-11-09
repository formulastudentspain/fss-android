package es.formulastudent.app.mvp.data.business.team;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.Team;

public interface TeamBO {

    /**
     * Method to retrieve all teams
     * @param callback
     */
    void retrieveAllTeams(String carType, BusinessCallback callback);


    /**
     * Method to retrieve a team by ID
     * @param id
     * @param callback
     */
    void retrieveTeamById(String id, BusinessCallback callback);


    /**
     * Method to delete all teams
     * @param callback
     */
    void deleteAllTeams(BusinessCallback callback);

    /**
     * Method to create a team
     * @param team
     * @param callback
     */
    void createTeam(Team team, BusinessCallback callback);
}
