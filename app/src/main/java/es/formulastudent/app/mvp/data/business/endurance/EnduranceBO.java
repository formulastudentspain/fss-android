package es.formulastudent.app.mvp.data.business.endurance;

import java.util.Date;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.User;

public interface EnduranceBO {


    /**
     * Method to retrieve Endurance registers
     * @param from:    From date
     * @param to:      To date
     * @param teamID:    Selected teamID
     * @param callback
     */
    void retrieveEnduranceRegisters(Date from, Date to, String teamID, Long carNumber, BusinessCallback callback);


    /**
     * Method to create an Endurance registry
     * @param user
     * @param carType
     * @param carNumber
     * @param briefingDone
     * @param callback
     */
    void createEnduranceRegistry(User user, String carType, Long carNumber, Boolean briefingDone, BusinessCallback callback);
}