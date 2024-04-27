package code.formulastudentspain.app.mvp.data.business.conecontrol;


import com.google.firebase.firestore.ListenerRegistration;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.statistics.dto.ExportStatisticsDTO;
import code.formulastudentspain.app.mvp.data.model.ConeControlEvent;
import code.formulastudentspain.app.mvp.data.model.ConeControlRegister;
import code.formulastudentspain.app.mvp.data.model.RaceControlState;

public interface ConeControlBO extends DataLoader.Consumer{

    /**
     * Get filtered Race Cones registers in Real-Time
     * @param event
     * @param filters
     * @param onSuccessCallback
     * @param onFailureCallback
     * @return
     */
    ListenerRegistration getConeControlRegistersRealTime(ConeControlEvent event, Map<String, Object> filters,
                                                         @NotNull OnSuccessCallback<List<ConeControlRegister>> onSuccessCallback,
                                                         @NotNull OnFailureCallback onFailureCallback);


    /**
     * Create a register for each sector for the selected team
     * @param event
     * @param carNumber
     * @param flagURL
     * @param raceRound
     * @param numberOfSectors
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createConeControlForAllSectors(ConeControlEvent event, Long carNumber, String flagURL,
                                        String raceRound, int numberOfSectors,
                                        @NotNull OnSuccessCallback<?> onSuccessCallback,
                                        @NotNull OnFailureCallback onFailureCallback);

    /**
     * Update single register
     * @param event
     * @param register
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void updateConeControlRegister(ConeControlEvent event, ConeControlRegister register,
                                   @NotNull OnSuccessCallback<?> onSuccessCallback,
                                   @NotNull OnFailureCallback onFailureCallback);

    /**
     * Enable/disable all cone control registers for the given car number
     * @param ccEvent
     * @param carNumber
     * @param state
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void enableOrDisableConeControlRegistersByTeam(ConeControlEvent ccEvent, Long carNumber,
                                                   RaceControlState state,
                                                   @NotNull OnSuccessCallback<?> onSuccessCallback,
                                                   @NotNull OnFailureCallback onFailureCallback);


    /**
     * Get all registers by race round
     * @param event
     * @param raceRound
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void getConeControlRegistersByRaceRound(ConeControlEvent event, String raceRound,
                                            @NotNull OnSuccessCallback<List<ConeControlRegister>> onSuccessCallback,
                                            @NotNull OnFailureCallback onFailureCallback);


    /**
     * Export cones and off courses to Excel
     * @param event
     * @param onSuccessCallback
     * @param onFailureCallback
     * @throws IOException
     */
    void exportConesToExcel(ConeControlEvent event,
                            @NotNull OnSuccessCallback<ExportStatisticsDTO> onSuccessCallback,
                            @NotNull OnFailureCallback onFailureCallback) throws IOException;
}