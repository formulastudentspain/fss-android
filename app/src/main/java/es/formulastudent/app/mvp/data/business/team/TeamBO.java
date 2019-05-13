package es.formulastudent.app.mvp.data.business.team;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface TeamBO {

    /**
     * Method to retrieve all teams
     * @param callback
     */
    void retrieveAllTeams(BusinessCallback callback);
}
