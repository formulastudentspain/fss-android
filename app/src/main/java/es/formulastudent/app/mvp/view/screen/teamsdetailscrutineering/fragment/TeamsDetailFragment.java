package es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.fragment;

import es.formulastudent.app.mvp.data.model.Team;

public interface TeamsDetailFragment {

    /**
     * Method to update the view given an updated team
     * @param team
     */
    void updateView(Team team);

}
