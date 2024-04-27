package code.formulastudentspain.app.mvp.data.business.raceaccess;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.model.EventRegister;
import code.formulastudentspain.app.mvp.data.model.EventType;
import code.formulastudentspain.app.mvp.data.model.TeamMember;

public interface RaceAccessBO extends DataLoader.Consumer{

    /**
     * Method to retrieve Dynamic Event registers
     * @param from
     * @param to
     * @param teamID
     * @param carNumber
     * @param type
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveRegisters(Date from, Date to, String teamID, Long carNumber, EventType type,
                           @NotNull OnSuccessCallback<List<EventRegister>> onSuccessCallback,
                           @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to create a Dynamic Event register
     * @param teamMember
     * @param carNumber
     * @param briefingDone
     * @param type
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createRegister(TeamMember teamMember, Long carNumber, Boolean briefingDone, EventType type,
                        @NotNull OnSuccessCallback<EventRegister> onSuccessCallback,
                        @NotNull OnFailureCallback onFailureCallback);


    /**
     * Method to update the Chrono time to the Pre-Scrutineering register
     * @param id
     * @param milliseconds
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void updatePreScrutineeringRegister(String id, long milliseconds,
                                        @NotNull OnSuccessCallback<?> onSuccessCallback,
                                        @NotNull OnFailureCallback onFailureCallback);

    /**
     * Method to delete a Dynamic Event register
     * @param type
     * @param registerID
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void deleteRegister(EventType type, String registerID,
                        @NotNull OnSuccessCallback<?> onSuccessCallback,
                        @NotNull OnFailureCallback onFailureCallback);


    /**
     *
     * @param userId
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void getDifferentEventRegistersByDriver(String userId,
                                            @NotNull OnSuccessCallback<Map<String, List<EventRegister>>> onSuccessCallback,
                                            @NotNull OnFailureCallback onFailureCallback);
}
