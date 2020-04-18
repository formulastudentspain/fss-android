package es.formulastudent.app.mvp.view.screen.user;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.DataConsumer;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.Role;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.utils.messages.Messages;


public class UserPresenter extends DataConsumer implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private UserBO userBO;
    private TeamBO teamBO;
    private Messages messages;

    //Data
    private List<User> allUsersList = new ArrayList<>();
    private List<User> filteredUserList = new ArrayList<>();

    //Filtering values
    private UserRole selectedRole;


    public UserPresenter(UserPresenter.View view, UserBO userBO, TeamBO teamBO, Messages messages) {
        super(userBO, teamBO);
        this.view = view;
        this.userBO = userBO;
        this.teamBO = teamBO;
        this.messages = messages;
    }


    private void updateUserListItems(List<User> newItems) {
        //Update all-user-list
        this.allUsersList.clear();
        this.allUsersList.addAll(newItems);

        //Update and refresh filtered-user-list
        this.filteredUserList.clear();
        this.filteredUserList.addAll(newItems);
        this.view.refreshUserItems();
    }


    public void retrieveUsers() {
        userBO.retrieveUsers(selectedRole, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<User> users = (List<User>) responseDTO.getData();
                updateUserListItems(users);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(R.string.team_member_get_all_error);
            }
        });
    }

    void filterUsers(String query) {
        filteredUserList.clear();
        for (User user : allUsersList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredUserList.add(user);
            }
        }
        this.view.refreshUserItems();
    }

    public void filterIconClicked() {
        teamBO.retrieveTeams(null, null, teams -> {
            //TODO los equipos están para cuando use la app los Team Leaders
            view.showFilteringDialog(selectedRole);
        }, this::setErrorToDisplay);
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {
        User selectedUser = filteredUserList.get(position);
        view.openUserDetailFragment(selectedUser);
    }


    public void createUser(User user) {
        userBO.createUser(user, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveUsers();
                messages.showInfo(responseDTO.getInfo());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });
    }

    public void setFilteringValues(UserRole selectedRole) {
        this.selectedRole = selectedRole;
        view.filtersActivated(selectedRole != null);
    }

    List<User> getUserItemList() {
        return filteredUserList;
    }


    public interface View {

        void filtersActivated(Boolean activated);

        /**
         * On retrieved timeline items
         */
        void refreshUserItems();

        /**
         * Open QR reader
         */
        void openQRCodeReader();

        /**
         * Show create user dialog
         */
        void showCreateUserDialog();

        /**
         * Show dialog to filter
         *
         * @param selectedRole
         */
        void showFilteringDialog(Role selectedRole);

        /**
         * Open user detail
         * @param user
         */
        void openUserDetailFragment(User user);
    }
}
