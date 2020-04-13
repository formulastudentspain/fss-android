package es.formulastudent.app.mvp.view.screen.raceaccess;

import es.formulastudent.app.mvp.data.model.TeamMember;

    public interface RaceAccessGeneralPresenter {


    /**
     * This method will is called from Confirm Dialog
     * @param teamMember
     * @param carNumber
     * @param briefingDone
     */
    void createRegistry(TeamMember teamMember, Long carNumber, Boolean briefingDone);
}
