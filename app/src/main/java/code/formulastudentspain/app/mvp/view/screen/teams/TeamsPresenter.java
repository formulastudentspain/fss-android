package code.formulastudentspain.app.mvp.view.screen.teams;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog;


public class TeamsPresenter extends DataConsumer implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private TeamBO teamBO;

    //Data
    private List<Team> teamsList = new ArrayList<>();
    private Map<String, String> filters = new HashMap<>();


    public TeamsPresenter(TeamsPresenter.View view, TeamBO teamBO) {
        super(teamBO);
        this.view = view;
        this.teamBO = teamBO;
    }


    /**
     * Retrieve Briefing registers
     */
    public void retrieveTeamsList() {
        view.filtersActivated(!filters.keySet().isEmpty());

        //Call Briefing business
        teamBO.retrieveTeams(null, filters,
                this::updateTeams,
                this::setErrorToDisplay);
    }


    private void updateTeams(List<Team> items) {
        this.teamsList.clear();
        this.teamsList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {
        Team selectedTeam = teamsList.get(position);

        if (v.getId() == R.id.scrutineering_button) {
            view.openScrutineeringFragment(selectedTeam);

        } else if (v.getId() == R.id.fee_button) {
            view.openFeeFragment(selectedTeam);
        }
    }

    /**
     * Open the filtering dialog
     */
    void filterIconClicked() {
        FilterTeamsDialog filterTeamsDialog = FilterTeamsDialog.newInstance(this, filters);
        filterTeamsDialog.show(view.getActivity().getSupportFragmentManager(), "addCommentDialog");
    }


    List<Team> getTeamsList() {
        return teamsList;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }




    public interface View {

        FragmentActivity getActivity();

        /**
         * Refresh items in list
         */
        void refreshBriefingRegisterItems();

        /**
         * Method to know if the filters are activated
         *
         * @param activated
         */
        void filtersActivated(Boolean activated);

        /**
         * Open scrutineering fragment
         *
         * @param team: selected team
         */
        void openScrutineeringFragment(Team team);

        /**
         * Open fee fragment
         *
         * @param team: selected team
         */
        void openFeeFragment(Team team);
    }
}
