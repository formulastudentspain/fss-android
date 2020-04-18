package es.formulastudent.app.mvp.data.business.team;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;
import es.formulastudent.app.mvp.data.model.Team;

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
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveTeamById(String id,  @NotNull OnSuccessCallback<Team> onSuccessCallback,
                          @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to delete all teams
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void deleteAllTeams(@NotNull OnSuccessCallback<?> onSuccessCallback,
                        @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to create a team
     * @param team
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createTeam(Team team, @NotNull OnSuccessCallback<?> onSuccessCallback,
                    @NotNull OnFailureCallback onFailureCallback);

    /**
     * Method to update a team
     * @param team
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void updateTeam(Team team, @NotNull OnSuccessCallback<?> onSuccessCallback,
                    @NotNull OnFailureCallback onFailureCallback);

}
