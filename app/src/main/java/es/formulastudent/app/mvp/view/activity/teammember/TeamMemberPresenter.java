package es.formulastudent.app.mvp.view.activity.teammember;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.business.userrole.UserRoleBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.Role;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;

public class TeamMemberPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;
    private UserBO userBO;
    private TeamBO teamBO;
    private UserRoleBO userRoleBO;

    //Data
    private List<TeamMember> allTeamMemberList = new ArrayList<>();
    private List<TeamMember> filteredTeamMemberList = new ArrayList<>();



    public TeamMemberPresenter(TeamMemberPresenter.View view, Context context, UserBO userBO, TeamBO teamBO, UserRoleBO userRoleBO) {
        this.view = view;
        this.context = context;
        this.userBO = userBO;
        this.teamBO = teamBO;
        this.userRoleBO = userRoleBO;
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
        userBO.retrieveUsers(new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                List<TeamMember> teamMembers = (List<TeamMember>) responseDTO.getData();

                //Update view with new results
                updateUserListItems(teamMembers);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.users_get_all_error);
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

        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("selectedTeamMember", selectedTeamMember);
        context.startActivity(intent);

    }


    public void createUser(TeamMember teamMember){

        userBO.createUser(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                //Update list
                retrieveUsers();
                view.createMessage(R.string.users_create_info);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
               view.createMessage(R.string.dynamic_event_message_error_create_user);
            }
        });
    }


    private void retrieveRoles(){

        //Show loading
        view.showLoading();

        //Call business to retrieve user roles
        userRoleBO.retrieveUserRoles(new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                List<Role> roles = (List<Role>) responseDTO.getData();

                //Retrieve Teams now
                retrieveTeams(roles);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.users_get_user_roles_error);
            }
        });
    }



    private void retrieveTeams(final List<Role> roles){

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveAllTeams(null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Hide loading
                view.hideLoading();

                List<Team> teams = (List<Team>) responseDTO.getData();
                view.showCreateUserDialog(teams, roles);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
               view.createMessage(R.string.users_get_teams_error);
            }
        });
    }


    public List<TeamMember> getUserItemList() {
        return filteredTeamMemberList;
    }

    public void retrieveCreateUserDialogData() {
        //First retrieve roles, then retrieve teams
        retrieveRoles();
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
        void showCreateUserDialog(List<Team> teams, List<Role> roles);
    }

}
