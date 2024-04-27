package code.formulastudentspain.app.mvp.data.business.teammember;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.TeamMember;

public interface TeamMemberBO extends DataLoader.Consumer{

    /**
     * Retrieve team member by NFC tag
     * @param tag
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveTeamMemberByNFCTag(String tag,
                                    @NotNull OnSuccessCallback<TeamMember> onSuccessCallback,
                                    @NotNull OnFailureCallback onFailureCallback);

    /**
     * Retrieve all team member
     * @param selectedTeam
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveTeamMembers(Team selectedTeam,
                             @NotNull OnSuccessCallback<List<TeamMember>> onSuccessCallback,
                             @NotNull OnFailureCallback onFailureCallback);


    /**
     * Create team member
     * @param teamMember
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createTeamMember(TeamMember teamMember,
                          @NotNull OnSuccessCallback<?> onSuccessCallback,
                          @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to delete all team members
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void deleteAllTeamMembers(@NotNull OnSuccessCallback<?> onSuccessCallback,
            @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to get the team members register by a team (max. 6)
     * @param teamID
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void getRegisteredTeamMemberByTeamId(String teamID,
                                         @NotNull OnSuccessCallback<List<TeamMember>> onSuccessCallback,
                                         @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to update the team member
     * @param teamMember
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void updateTeamMember(TeamMember teamMember, @NotNull OnSuccessCallback<?> onSuccessCallback,
                          @NotNull OnFailureCallback onFailureCallback);
}