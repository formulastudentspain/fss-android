package es.formulastudent.app.mvp.data.business.conecontrol;


import androidx.annotation.Nullable;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.Map;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.Team;

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
     * @param team
     * @param raceRound
     * @param callback
     */
    void createConeControlForAllSectors(Team team, String raceRound, BusinessCallback callback);



    /**
     * Update the register for the selected team and sector. If sector is null, all sectors will be updated.
     *
     * @param team
     * @param sector
     * @param cones
     * @param offCourses
     * @param isRacing
     * @param callback
     */
    void updateConeControlRegister(Team team, @Nullable Long sector, @Nullable Long cones, @Nullable Long offCourses,
                                   @Nullable Boolean isRacing, BusinessCallback callback);

}