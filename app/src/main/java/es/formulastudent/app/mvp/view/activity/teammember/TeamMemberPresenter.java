package es.formulastudent.app.mvp.view.activity.teammember;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.teammemberdetail.TeamMemberDetailActivity;

public class TeamMemberPresenter implements RecyclerViewClickListener, TeamMemberGeneralPresenter {

    //Dependencies
    private View view;
    private Context context;
    private TeamMemberBO teamMemberBO;
    private TeamBO teamBO;
    private BriefingBO briefingBO;

    //Data
    private List<TeamMember> allTeamMemberList = new ArrayList<>();
    private List<TeamMember> filteredTeamMemberList = new ArrayList<>();
    private Team selectedTeamToFilter;



    public TeamMemberPresenter(TeamMemberPresenter.View view, Context context,
                               TeamMemberBO teamMemberBO, TeamBO teamBO, BriefingBO briefingBO) {
        this.view = view;
        this.context = context;
        this.teamMemberBO = teamMemberBO;
        this.teamBO = teamBO;
        this.briefingBO = briefingBO;
    }


    private void updateUserListItems(List<TeamMember> newItems){
        //Update all-user-list
        this.allTeamMemberList.clear();
        this.allTeamMemberList.addAll(newItems);

        //Update and refresh filtered-user-list
        this.filteredTeamMemberList.clear();
        this.filteredTeamMemberList.addAll(newItems);
        this.view.refreshUserItems();
    }



    public void retrieveTeamMembers(){

        //Set filtering icon
        view.filtersActivated(selectedTeamToFilter!=null && !"".equals(selectedTeamToFilter.getID()));

        //show loading
        view.showLoading();

        //call business to retrieve users
        teamMemberBO.retrieveTeamMembers(selectedTeamToFilter, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                List<TeamMember> teamMembers = (List<TeamMember>) responseDTO.getData();

                //Update view with new results
                updateUserListItems(teamMembers);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.team_member_get_all_error);
            }
        });
    }



    public void filterUsers(String query){

        if(allTeamMemberList.isEmpty()){
            return;
        }


        //Clear the list
        filteredTeamMemberList.clear();

        //Add results
        for(TeamMember teamMember : allTeamMemberList){
            if(teamMember.getName().toLowerCase().contains(query.toLowerCase())){
                filteredTeamMemberList.add(teamMember);
            }
        }

        //Refresh list
        this.view.refreshUserItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        TeamMember selectedTeamMember = filteredTeamMemberList.get(position);

        view.showLoading();
        briefingBO.checkBriefingByUser(selectedTeamMember.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.hideLoading();

                Boolean lastBriefing = (Boolean) responseDTO.getData();

                Intent intent = new Intent(context, TeamMemberDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("selectedTeamMember", selectedTeamMember);
                intent.putExtra("lastBriefing", lastBriefing);
                context.startActivity(intent);

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.hideLoading();
            }
        });
    }



    public void updateOrCreateTeamMember(TeamMember teamMember){
        createUser(teamMember);
    }

    private void createUser(TeamMember teamMember){

        teamMemberBO.createTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                //Update list
                retrieveTeamMembers();
                view.createMessage(R.string.team_member_create_info);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
               view.createMessage(R.string.dynamic_event_message_error_create_user);
            }
        });
    }


    void openCreateTeamMemberDialog(){

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveTeams(null, null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Hide loading
                view.hideLoading();

                List<Team> teams = (List<Team>) responseDTO.getData();
                view.showCreateTeamMemberDialog(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
               view.createMessage(R.string.team_member_get_teams_error);
            }
        });
    }


    public List<TeamMember> getUserItemList() {
        return filteredTeamMemberList;
    }


    public void filterIconClicked() {

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveTeams(null, null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Hide loading
                view.hideLoading();

                List<Team> teams = (List<Team>) responseDTO.getData();
                view.showFilteringDialog(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.team_member_get_teams_error);
            }
        });
    }

    public Team getSelectedTeamToFilter() {
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
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object...args);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading
         */
        void showLoading();

        /**
         * Hide loading
         */
        void hideLoading();

        /**
         * Show create user dialog
         * @param teams
         */
        void showCreateTeamMemberDialog(List<Team> teams);

        /**
         * Show filtering dialog
         * @param teams
         */
        void showFilteringDialog(List<Team> teams);
    }

}
