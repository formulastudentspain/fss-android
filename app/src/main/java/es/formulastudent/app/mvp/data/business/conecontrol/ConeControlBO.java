package es.formulastudent.app.mvp.data.business.conecontrol;


import com.google.firebase.firestore.ListenerRegistration;

import java.util.Map;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlState;

public interface ConeControlBO {

    /**
     * Get filtered Race Cones registers in Real-Time
     *
     * @param filters
     * @param callback
     */
    ListenerRegistration getConeControlRegistersRealTime(ConeControlEvent event, Map<String, Object> filters, BusinessCallback callback);


    /**
     * Create a register for each sector for the selected team
     *
     * @param carNumber
     * @param flagURL
     * @param raceRound
     * @param numberOfSectors
     * @param callback
     */
    void createConeControlForAllSectors(ConeControlEvent event, Long carNumber, String flagURL, String raceRound, int numberOfSectors, BusinessCallback callback);


    /**
     * Update single register
     * @param register
     * @param callback
     */
    void updateConeControlRegister(ConeControlEvent event, ConeControlRegister register, BusinessCallback callback);

    /**
     * Enable/disable all cone control registers for the given car number
     * @param ccEvent
     * @param carNumber
     * @param state
     * @param callback
     */
    void enableOrDisableConeControlRegistersByTeam(ConeControlEvent ccEvent, Long carNumber, RaceControlState state, BusinessCallback callback);


    /**
     * Get all registers by race round
     * @param raceRound
     * @param callback
     */
    void getConeControlRegistersByRaceRound(ConeControlEvent event, String raceRound, BusinessCallback callback);

}