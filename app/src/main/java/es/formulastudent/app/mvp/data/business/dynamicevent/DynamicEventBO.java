package es.formulastudent.app.mvp.data.business.dynamicevent;

import java.util.Date;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.User;

public interface DynamicEventBO {




    /**
     * Method to retrieve Acceleration registers
     * @param from
     * @param to
     * @param teamID
     * @param carNumber
     * @param type
     * @param callback
     */
    void retrieveRegisters(Date from, Date to, String teamID, Long carNumber, EventType type, BusinessCallback callback);



    /**
     * Method to create an Acceleration registry
     * @param user
     * @param carType
     * @param carNumber
     * @param briefingDone
     * @param type
     * @param callback
     */
    void createRegister(User user, String carType, Long carNumber, Boolean briefingDone, EventType type, BusinessCallback callback);




}
