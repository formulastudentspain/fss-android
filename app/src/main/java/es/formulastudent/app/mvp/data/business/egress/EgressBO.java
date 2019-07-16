package es.formulastudent.app.mvp.data.business.egress;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface EgressBO {

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
