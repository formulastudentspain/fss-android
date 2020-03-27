package es.formulastudent.app.mvp.view.activity.user;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.Role;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;


public class UserPresenter implements RecyclerViewClickListener {


    //Dependencies
    private View view;
    private Context context;
    private UserBO userBO;
    private TeamBO teamBO;

    //Data
    private List<User> allUsersList = new ArrayList<>();
    private List<User> filteredUserList = new ArrayList<>();

    //Filtering values
    private UserRole selectedRole;


    public UserPresenter(UserPresenter.View view, Context context, UserBO userBO, TeamBO teamBO) {
        this.view = view;
        this.context = context;
        this.userBO = userBO;
        this.teamBO = teamBO;
    }


    private void updateUserListItems(List<User> newItems){
        //Update all-user-list
        this.allUsersList.clear();
        this.allUsersList.addAll(newItems);

        //Update and refresh filtered-user-list
        this.filteredUserList.clear();
        this.filteredUserList.addAll(newItems);
        this.view.refreshUserItems();
    }



    public void retrieveUsers(){


        //show loading
        view.showLoading();

        //call business to retrieve users
        userBO.retrieveUsers(selectedRole, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                List<User> users = (List<User>) responseDTO.getData();

                //Update view with new results
                updateUserListItems(users);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.team_member_get_all_error);
            }
        });
    }



    public void filterUsers(String query){

        //Clear the list
        filteredUserList.clear();

        //Add results
        for(User user : allUsersList){
            if(user.getName().toLowerCase().contains(query.toLowerCase())){
                filteredUserList.add(user);
            }
        }

        //Refresh list
        this.view.refreshUserItems();
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
                //TODO los equipos están para cuando use la app los Team Leaders

                view.showFilteringDialog(selectedRole);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.team_member_get_teams_error);
            }
        });
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
                view.createMessage(responseDTO.getInfo());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
               view.createMessage(responseDTO.getError());
            }
        });
    }


    public List<User> getUserItemList() {
        return filteredUserList;
    }

    public void retrieveCreateUserDialogData() {
        //First retrieve roles, then retrieve teams
        view.showCreateUserDialog();
    }

    public void setFilteringValues(UserRole selectedRole) {
        this.selectedRole = selectedRole;
        view.filtersActivated(selectedRole != null);
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
        void createMessage(Integer message, Object... args);

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
         * Open QR reader
         */
        void openQRCodeReader();

        /**
         * Show create user dialog
         */
        void showCreateUserDialog();

        void showFilteringDialog(Role selectedRole);
    }

}
