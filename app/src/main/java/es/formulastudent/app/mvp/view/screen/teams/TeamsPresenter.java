package es.formulastudent.app.mvp.view.screen.teams;

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
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;


public class TeamsPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private LoadingDialog loadingDialog;
    private Messages messages;

    //Data
    private List<Team> teamsList = new ArrayList<>();
    private Map<String, String> filters = new HashMap<>();


    public TeamsPresenter(TeamsPresenter.View view, TeamBO teamBO, LoadingDialog loadingDialog,
                          Messages messages) {
        this.view = view;
        this.teamBO = teamBO;
        this.loadingDialog = loadingDialog;
        this.messages = messages;
    }


    /**
     * Retrieve Briefing registers
     */
    public void retrieveTeamsList() {
        loadingDialog.show();
        view.filtersActivated(!filters.keySet().isEmpty());

        //Call Briefing business
        teamBO.retrieveTeams(null, filters, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                List<Team> results = (List<Team>) responseDTO.getData();
                if (results == null) {
                    results = new ArrayList<>();
                }
                updateTeams(results);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
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
