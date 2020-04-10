package es.formulastudent.app.mvp.view.screen.teams;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog;


public class TeamsPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;
    private TeamBO teamBO;

    //Data
    List<Team> allTeamsList = new ArrayList<>();
    List<Team> filteredTeamsList = new ArrayList<>();
    Map<String, String> filters = new HashMap<>();


    public TeamsPresenter(TeamsPresenter.View view, Context context, TeamBO teamBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
    }


    /**
     * Retrieve Briefing registers
     */
    public void retrieveTeamsList() {

        view.filtersActivated(!filters.keySet().isEmpty());

        //Call Briefing business
        teamBO.retrieveTeams(null, filters, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<Team> results = (List<Team>) responseDTO.getData();
                if (results == null) {
                    results = new ArrayList<>();
                }
                updateTeams(results);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
            }
        });
    }


    public void updateTeams(List<Team> items) {
        //Update all-register-list
        this.allTeamsList.clear();
        this.allTeamsList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredTeamsList.clear();
        this.filteredTeamsList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {
        Team selectedTeam = filteredTeamsList.get(position);

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


    public List<Team> getTeamsList() {
        return filteredTeamsList;
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
