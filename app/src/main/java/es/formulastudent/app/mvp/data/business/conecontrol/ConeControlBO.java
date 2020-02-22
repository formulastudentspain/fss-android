package es.formulastudent.app.mvp.data.business.conecontrol;


import com.google.firebase.firestore.ListenerRegistration;

import java.util.Map;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;

public interface ConeControlBO {

    /**
     * Get filtered Race Cones registers in Real-Time
     *
     * @param filters
     * @param callback
     */
    ListenerRegistration getConeControlRegistersRealTime(Map<String, Object> filters, BusinessCallback callback);



    /**
     * Create a register for each sector for the selected team
     *
     * @param carNumer
     * @param flagURL
     * @param raceRound
     * @param numberOfSectors
     * @param callback
     */
    void createConeControlForAllSectors(Long carNumer, String flagURL, String raceRound, int numberOfSectors, BusinessCallback callback);


    /**
     * Update single register
     * @param register
     * @param callback
     */
    void updateConeControlRegister(ConeControlRegister register, BusinessCallback callback);


    /**
     * Enable/disable all cone control registers for the given car number
     * @param carNumber
     * @param enable
     * @param callback
     */
    void enableOrDisableConeControlRegistersByTeam(Long carNumber, boolean enable, BusinessCallback callback);
}