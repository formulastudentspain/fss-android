package es.formulastudent.app.mvp.view.activity.userlist;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.business.userrole.UserRoleBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;
import es.formulastudent.app.mvp.view.activity.userlist.recyclerview.RecyclerViewClickListener;

public class UserListPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;
    private UserBO userBO;
    private TeamBO teamBO;
    private UserRoleBO userRoleBO;

    //Data
    private List<User> allUserList = new ArrayList<>();
    private List<User> filteredUserList = new ArrayList<>();



    public UserListPresenter(UserListPresenter.View view, Context context, UserBO userBO, TeamBO teamBO, UserRoleBO userRoleBO) {
        this.view = view;
        this.context = context;
        this.userBO = userBO;
        this.teamBO = teamBO;
        this.userRoleBO = userRoleBO;
    }


    private void updateUserListItems(List<User> newItems){
        //Update all-user-list
        this.allUserList.clear();
        this.allUserList.addAll(newItems);

        //Update and refresh filtered-user-list
        this.filteredUserList.clear();
        this.filteredUserList.addAll(newItems);
        this.view.refreshUserItems();
    }



    void retrieveUsers(){

        //show loading
        view.showLoading();

        //call business to retrieve users
        userBO.retrieveUsers(new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                List<User> users = (List<User>) responseDTO.getData();

                //Update view with new results
                updateUserListItems(users);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO
            }
        });
    }




    public void filterUsers(String query){

        //Clear the list
        filteredUserList.clear();

        //Add results
        for(User user: allUserList){
            if(user.getName().toLowerCase().contains(query.toLowerCase())){
                filteredUserList.add(user);
            }
        }

        //Refresh list
        this.view.refreshUserItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        User selectedUser = filteredUserList.get(position);

        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("selectedUser", selectedUser);
        context.startActivity(intent);

    }


    public void createUser(User user){

        userBO.createUser(user, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                //Update list
                retrieveUsers();
                view.showMessage("User registered successfully!");
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
               //TODO
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

                List<UserRole> roles = (List<UserRole>) responseDTO.getData();

                //Retrieve Teams now
                retrieveTeams(roles);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO
            }
        });
    }



    private void retrieveTeams(final List<UserRole> roles){

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveAllTeams(new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                List<Team> teams = (List<Team>) responseDTO.getData();
                view.showCreateUserDialog(teams, roles);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO
            }
        });
    }


    public List<User> getUserItemList() {
        return filteredUserList;
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
        void showMessage(String message);

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
        void showCreateUserDialog(List<Team> teams, List<UserRole> roles);
    }

}
