package es.formulastudent.app.mvp.data.api.team;

import es.formulastudent.app.mvp.data.api.BusinessCallback;

public interface TeamBO {

    /**
     * Method to retrieve all teams
     * @param callback
     */
    void retrieveAllTeams(BusinessCallback callback);
}
