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
import es.formulastudent.app.mvp.data.model.Role;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.TeamMemberRole;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.teammemberdetail.TeamMemberDetailActivity;

public class TeamMemberPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;
    private TeamMemberBO teamMemberBO;
    private TeamBO teamBO;
    private BriefingBO briefingBO;

    //Data
    private List<TeamMember> allTeamMemberList = new ArrayList<>();
    private List<TeamMember> filteredTeamMemberList = new ArrayList<>();



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



    void retrieveUsers(){

        //show loading
        view.showLoading();

        //call business to retrieve users
        teamMemberBO.retrieveTeamMembers(new BusinessCallback() {
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


    void onNFCTagDetected(final String tagNFC){

        //Show loading
        view.showLoading();

        teamMemberBO.retrieveTeamMemberByNFCTag(tagNFC, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                TeamMember teamMember = (TeamMember) responseDTO.getData();

                briefingBO.checkBriefingByUser(teamMember.getID(), new BusinessCallback() {
                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {
                        Boolean briefingAvailable = (Boolean) responseDTO.getData();


                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                        //TODO
                    }
                });

                view.hideLoading();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
                view.hideLoading();
            }
        });
    }



    public void filterUsers(String query){

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

        Intent intent = new Intent(context, TeamMemberDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("selectedTeamMember", selectedTeamMember);
        context.startActivity(intent);

    }


    public void createUser(TeamMember teamMember){

        teamMemberBO.createTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                //Update list
                retrieveUsers();
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
                view.showCreateTeamMemberDialog(teams, TeamMemberRole.getAll());
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



    public interface View {

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
         * @param roles
         */
        void showCreateTeamMemberDialog(List<Team> teams, List<Role> roles);
    }

}
