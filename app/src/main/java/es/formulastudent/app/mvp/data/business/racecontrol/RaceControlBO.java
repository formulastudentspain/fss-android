package es.formulastudent.app.mvp.data.business.racecontrol;


import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;
import java.util.Map;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.view.screen.racecontrol.dialog.RaceControlTeamDTO;

public interface RaceControlBO extends DataLoader.Consumer {

    /**
     * Get filtered Race Control registers in Real-Time
     *
     * @param filters
     * @param callback
     */
    ListenerRegistration getRaceControlRegistersRealTime(Map<String, Object> filters, BusinessCallback callback);


    /**
     * Get Teams in order to be added to the RaceControl Endurance, it returns a list of RaceControlTeamDTO objects
     * @param filters
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void getRaceControlTeams(Map<String, Object> filters,
                             OnSuccessCallback<List<RaceControlTeamDTO>> onSuccessCallback,
                             OnFailureCallback onFailureCallback);


    /**
     * Create Race Control registers
     *
     * @param raceControlTeamDTOList
     * @param vehicleType
     * @param currentMaxIndex
     * @param callback
     */
    void createRaceControlRegister(List<RaceControlTeamDTO> raceControlTeamDTOList, RaceControlEvent eventType, String vehicleType, Long currentMaxIndex, BusinessCallback callback);


    /**
     * Get filtered Race Control registers
     *
     * @param filters
     * @param callback
     */
    void getRaceControlRegisters(Map<String, Object> filters, BusinessCallback callback);


    /**
     * Update the registry state into a new one
     *
     * @param event
     * @param newState
     * @param callback
     */
    void updateRaceControlState(RaceControlRegister register, RaceControlEvent event, RaceControlState newState, BusinessCallback callback);

}