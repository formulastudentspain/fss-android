package es.formulastudent.app.mvp.view.screen.teammember;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.DataConsumer;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;


public class TeamMemberPresenter extends DataConsumer implements RecyclerViewClickListener, TeamMemberGeneralPresenter {

    //Dependencies
    private View view;
    private TeamMemberBO teamMemberBO;
    private TeamBO teamBO;
    private BriefingBO briefingBO;

    //Data
    private List<TeamMember> allTeamMemberList = new ArrayList<>();
    private List<TeamMember> filteredTeamMemberList = new ArrayList<>();
    private Team selectedTeamToFilter;


    public TeamMemberPresenter(TeamMemberPresenter.View view, TeamMemberBO teamMemberBO,
                               TeamBO teamBO, BriefingBO briefingBO) {
        super(teamMemberBO, teamBO, briefingBO);
        this.view = view;
        this.teamMemberBO = teamMemberBO;
        this.teamBO = teamBO;
        this.briefingBO = briefingBO;
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
        teamMemberBO.retrieveTeamMembers(selectedTeamToFilter,
                this::updateUserListItems,
                this::setErrorToDisplay);
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
                this::setErrorToDisplay);
    }

    public void updateOrCreateTeamMember(TeamMember teamMember) {
        createUser(teamMember);
    }

    private void createUser(TeamMember teamMember) {
        teamMemberBO.createTeamMember(teamMember, onSuccess -> retrieveTeamMembers()
                , this::setErrorToDisplay);
    }


    void openCreateTeamMemberDialog() {
        teamBO.retrieveTeams(null, null,
                teams -> view.showCreateTeamMemberDialog(teams),
                this::setErrorToDisplay);
    }

    public void filterIconClicked() {
        teamBO.retrieveTeams(null, null,
                teams -> view.showFilteringDialog(teams),
                this::setErrorToDisplay);
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