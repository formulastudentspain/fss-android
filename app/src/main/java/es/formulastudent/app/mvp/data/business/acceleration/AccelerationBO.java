package es.formulastudent.app.mvp.data.business.acceleration;

import java.util.Date;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.User;

public interface AccelerationBO {

    /**
     * Method to retrieve Acceleration registers
     *
     * @param from:      From date
     * @param to:        To date
     * @param teamID:    Selected teamID
     * @param carNumber: Selected car number
     * @param callback
     */
    void retrieveAccelerationRegisters(Date from, Date to, String teamID, Long carNumber, BusinessCallback callback);


    /**
     * Method to create an Acceleration registry
     *
     * @param user
     * @param callback
     */
    void createAccelerationRegistry(User user, String carType, Long carNumber, Boolean briefingDone, BusinessCallback callback);

}