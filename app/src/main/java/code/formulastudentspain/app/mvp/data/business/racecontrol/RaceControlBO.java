package code.formulastudentspain.app.mvp.data.business.racecontrol;


import com.google.firebase.firestore.ListenerRegistration;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.model.RaceControlEvent;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegister;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegisterEndurance;
import code.formulastudentspain.app.mvp.data.model.RaceControlState;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.dialog.RaceControlTeamDTO;

public interface RaceControlBO extends DataLoader.Consumer {

    /**
     * Get filtered Race Control registers in Real-Time
     * @param filters
     * @param onSuccessCallback
     * @param onFailureCallback
     * @return
     */
    ListenerRegistration getRaceControlRegistersRealTime(Map<String, Object> filters,
                                                         @NotNull OnSuccessCallback<List<RaceControlRegister>> onSuccessCallback,
                                                         @NotNull OnFailureCallback onFailureCallback);


    /**
     * Get Teams in order to be added to the RaceControl Endurance, it returns a list of RaceControlTeamDTO objects
     * @param filters
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void getRaceControlTeams(Map<String, Object> filters,
                             @NotNull OnSuccessCallback<List<RaceControlTeamDTO>> onSuccessCallback,
                             @NotNull OnFailureCallback onFailureCallback);


    /**
     * Create Race Control registers
     * @param raceControlTeamDTOList
     * @param eventType
     * @param vehicleType
     * @param currentMaxIndex
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createRaceControlRegister(List<RaceControlTeamDTO> raceControlTeamDTOList, RaceControlEvent eventType,
                                   String vehicleType, Long currentMaxIndex,
                                   @NotNull OnSuccessCallback<?> onSuccessCallback,
                                   @NotNull OnFailureCallback onFailureCallback);


    /**
     * Get filtered Race Control registers
     * @param filters
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void getRaceControlRegisters(Map<String, Object> filters,
                                 @NotNull OnSuccessCallback<List<RaceControlRegisterEndurance>> onSuccessCallback,
                                 @NotNull OnFailureCallback onFailureCallback);


    /**
     *
     * @param register
     * @param event
     * @param newState
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void updateRaceControlState(RaceControlRegister register, RaceControlEvent event,
                                RaceControlState newState,
                                @NotNull OnSuccessCallback<?> onSuccessCallback,
                                @NotNull OnFailureCallback onFailureCallback);

}