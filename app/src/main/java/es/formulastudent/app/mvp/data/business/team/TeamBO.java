package es.formulastudent.app.mvp.data.business.team;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.business.DataConsumer;

public interface TeamBO extends DataLoader.Consumer{


    /**
     * Method to retrieve all teams
     * @param carType, if null, all teams will be returned
     * @param filters
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveTeams(@Nullable String carType, Map<String, String> filters,
                       @NotNull OnSuccessCallback<List<Team>> onSuccessCallback,
                       @NotNull OnFailureCallback onFailureCallback);


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

    /**
     * Method to update a team
     * @param team
     * @param callback
     */
    void updateTeam(Team team, BusinessCallback callback);

    /**
     * Set data consumer
     * @param dataConsumer
     */
    void setDataConsumer(DataConsumer dataConsumer);
}
