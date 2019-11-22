package es.formulastudent.app.mvp.data.business.dynamicevent;

import java.util.Date;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.TeamMember;

public interface DynamicEventBO {



    /**
     * Method to retrieve Dynamic Event registers
     * @param from
     * @param to
     * @param teamID
     * @param carNumber
     * @param type
     * @param callback
     */
    void retrieveRegisters(Date from, Date to, String teamID, Long carNumber, EventType type, BusinessCallback callback);



    /**
     * Method to create a Dynamic Event register
     * @param teamMember
     * @param carNumber
     * @param briefingDone
     * @param type
     * @param callback
     */
    void createRegister(TeamMember teamMember, Long carNumber, Boolean briefingDone, EventType type, BusinessCallback callback);



    /**
     * Method to update the Chrono time to the Pre-Scrutineering register
     * @param id
     * @param milliseconds
     * @param callback
     */
    void updatePreScrutineeringRegister(String id, long milliseconds, BusinessCallback callback);


    /**
     * Method to delete a Dynamic Event register
     * @param type
     * @param registerID
     * @param callback
     */
    void deleteRegister(EventType type, String registerID, BusinessCallback callback);


    void getDifferentEventRegistersByDriver(String userId, BusinessCallback callback);
}
