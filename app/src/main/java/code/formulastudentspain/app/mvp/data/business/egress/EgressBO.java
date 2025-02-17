package code.formulastudentspain.app.mvp.data.business.egress;

import code.formulastudentspain.app.mvp.data.business.BusinessCallback;
import code.formulastudentspain.app.mvp.data.business.DataLoader;

public interface EgressBO extends DataLoader.Consumer{

    /**
     * Method to retrieve the egress register by a Pre-Scrutineering ID given
     * @param callback
     */
    void retrieveEgressByPreScrutineeringId(String preScrutineeringID, BusinessCallback callback);


    /**
     * Method to create a new register
     * @param preScrutineeringID
     * @param callback
     */
    void createRegister(String preScrutineeringID, BusinessCallback callback);


    /**
     * Method to save a time record
     * @param ID
     * @param time
     * @param callback
     */
    void saveTime(String ID, Long time, BusinessCallback callback);
}
