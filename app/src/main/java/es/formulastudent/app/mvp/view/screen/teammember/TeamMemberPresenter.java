package es.formulastudent.app.mvp.view.screen.teammember;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.business.DataConsumer;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.utils.messages.Messages;


public class TeamMemberPresenter extends DataConsumer implements RecyclerViewClickListener, TeamMemberGeneralPresenter {

    //Dependencies
    private View view;
    private TeamMemberBO teamMemberBO;
    private TeamBO teamBO;
    private BriefingBO briefingBO;
    private Messages messages;

    //Data
    private List<TeamMember> allTeamMemberList = new ArrayList<>();
    private List<TeamMember> filteredTeamMemberList = new ArrayList<>();
    private Team selectedTeamToFilter;


    public TeamMemberPresenter(TeamMemberPresenter.View view, TeamMemberBO teamMemberBO,
                               TeamBO teamBO, BriefingBO briefingBO, Messages messages) {
        super(teamMemberBO, teamBO, briefingBO);
        this.view = view;
        this.teamMemberBO = teamMemberBO;
        this.teamBO = teamBO;
        this.briefingBO = briefingBO;
        this.messages = messages;
    }


    private void updateUserListItems(List<TeamMember> newItems) {
        //Update all-user-list
        this.allTeamMemberList.clear();
        this.allTeamMemberList.addAll(newItems);

        //Update and refresh filtered-user-list
        this.filteredTeamMemberList.clear();
        this.filteredTeamMemberList.addAll(newItems);
        this.view.refreshUserItems();
    }


    public void retrieveTeamMembers() {

        //Set filtering icon
        view.filtersActivated(selectedTeamToFilter != null && !"".equals(selectedTeamToFilter.getID()));

        //call business to retrieve users
        teamMemberBO.retrieveTeamMembers(selectedTeamToFilter, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<TeamMember> teamMembers = (List<TeamMember>) responseDTO.getData();
                updateUserListItems(teamMembers);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });
    }


    void filterUsers(String query) {
        if (allTeamMemberList.isEmpty()) {
            return;
        }

        //Clear the list
        filteredTeamMemberList.clear();

        //Add results
        for (TeamMember teamMember : allTeamMemberList) {
            if (teamMember.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredTeamMemberList.add(teamMember);
            }
        }
        this.view.refreshUserItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        TeamMember selectedTeamMember = filteredTeamMemberList.get(position);
        briefingBO.checkBriefingByUser(selectedTeamMember.getID(),
                briefingDone -> view.openTeamMemberDetailFragment(selectedTeamMember, briefingDone),
                error -> messages.showError(1)); //FIXME
    }

    public void updateOrCreateTeamMember(TeamMember teamMember) {
        createUser(teamMember);
    }

    private void createUser(TeamMember teamMember) {
        teamMemberBO.createTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveTeamMembers();
                messages.showInfo(responseDTO.getInfo());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });
    }


    void openCreateTeamMemberDialog() {
        teamBO.retrieveTeams(null, null,
                teams -> view.showCreateTeamMemberDialog(teams),
                error -> messages.showError(1)); //FIXME
    }

    public void filterIconClicked() {
        teamBO.retrieveTeams(null, null,
                teams -> view.showFilteringDialog(teams),
                error -> messages.showError(1)); //FIXME
    }

    List<TeamMember> getUserItemList() {
        return filteredTeamMemberList;
    }

    Team getSelectedTeamToFilter() {
        return selectedTeamToFilter;
    }

    public void setSelectedTeamToFilter(Team selectedTeamToFilter) {
        this.selectedTeamToFilter = selectedTeamToFilter;
    }

    public interface View {

        void filtersActivated(Boolean activated);

        /**
         * On retrieved timeline items
         */
        void refreshUserItems();

        /**
         * Show create user dialog
         *
         * @param teams
         */
        void showCreateTeamMemberDialog(List<Team> teams);

        /**
         * Show filtering dialog
         *
         * @param teams
         */
        void showFilteringDialog(List<Team> teams);

        /**
         * Open team member detail fragment
         * @param selectedTeamMember
         * @param lastBriefing
         */
        void openTeamMemberDetailFragment(TeamMember selectedTeamMember, Boolean lastBriefing);
    }
}