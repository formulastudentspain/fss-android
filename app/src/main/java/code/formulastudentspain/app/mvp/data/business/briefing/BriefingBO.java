package code.formulastudentspain.app.mvp.data.business.briefing;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.model.BriefingRegister;
import code.formulastudentspain.app.mvp.data.model.TeamMember;

public interface BriefingBO extends DataLoader.Consumer {


    /**
     * Method to retrieve Briefing registers
     * @param from:    From date
     * @param to:      To date
     * @param teamID:    Selected teamID
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveBriefingRegisters(Date from, Date to, String teamID,
                                   @NotNull OnSuccessCallback<List<BriefingRegister>> onSuccessCallback,
                                   @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to create a Briefing registry
     * @param teamMember
     * @param registerUserMail
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createBriefingRegistry(TeamMember teamMember, String registerUserMail,
                                @NotNull OnSuccessCallback<?> onSuccessCallback,
                                @NotNull OnFailureCallback onFailureCallback);


    /**
     * Retrieve briefing registers by user and dates
     * @param userID
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void checkBriefingByUser(String userID,
                             @NotNull OnSuccessCallback<Boolean> onSuccessCallback,
                             @NotNull OnFailureCallback onFailureCallback);


    /**
     * Delete Briefing register
     * @param userID
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void deleteBriefingRegister(String userID,
                                @NotNull OnSuccessCallback<?> onSuccessCallback,
                                @NotNull OnFailureCallback onFailureCallback);

}

