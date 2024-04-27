package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs;

import code.formulastudentspain.app.mvp.data.model.Team;

public interface TeamsDetailFragment {

    /**
     * Method to update the view given an updated team
     * @param team
     */
    void updateView(Team team);

}
