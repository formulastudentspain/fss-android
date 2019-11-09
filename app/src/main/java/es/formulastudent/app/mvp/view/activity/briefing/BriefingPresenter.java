package es.formulastudent.app.mvp.view.activity.briefing;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.briefing.dialog.ConfirmBriefingRegisterDialog;
import es.formulastudent.app.mvp.view.activity.briefing.dialog.DeleteEventRegisterDialog;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class BriefingPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private TeamBO teamBO;
    private BriefingBO briefingBO;
    private UserBO userBO;


    //Data
    List<BriefingRegister> allBriefingRegisterList = new ArrayList<>();
    List<BriefingRegister> filteredBriefingRegisterList = new ArrayList<>();

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;

    //Team
    private String selectedTeamID;


    public BriefingPresenter(BriefingPresenter.View view, Context context, TeamBO teamBO, BriefingBO briefingBO, UserBO userBO, FirebaseAuth firebaseAuth) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
        this.briefingBO = briefingBO;
        this.userBO = userBO;
        this.firebaseAuth = firebaseAuth;
    }

    /**
     * Create Briefing registry
     * @param user
     */
     public void createRegistry(User user){

        //Show loading
        view.showLoading();

        briefingBO.createBriefingRegistry(user, firebaseAuth.getCurrentUser().getEmail(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveBriefingRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Hide loading
                view.hideLoading();

                view.createMessage("Error. Unable to create Briefing Registry");
            }
        });
    }


    /**
     * Retrieve Briefing registers
     */
     void retrieveBriefingRegisterList() {

        //Show loading
        view.showLoading();

        //Call Briefing business
         briefingBO.retrieveBriefingRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<BriefingRegister> results = (List<BriefingRegister>) responseDTO.getData();
                     if(results == null){
                         results = new ArrayList<>();
                     }
                     updateBriefingRegisters(results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes
             }
         });
    }


    public void updateBriefingRegisters(List<BriefingRegister> items){
        //Update all-register-list
        this.allBriefingRegisterList.clear();
        this.allBriefingRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredBriefingRegisterList.clear();
        this.filteredBriefingRegisterList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }



    public void deleteBriefingRegister(String id) {

        briefingBO.deleteBriefingRegister(id, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                if(!responseDTO.getInfo().isEmpty()){
                    view.createMessage(responseDTO.getInfo().get(0));
                }

                retrieveBriefingRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes
            }
        });

     }



    /**
     * Retrieve user by NFC tag after read
     * @param tag
     */
    void onNFCTagDetected(String tag){

        //Show loading
        view.showLoading();

        userBO.retrieveUserByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Hide loading
                view.hideLoading();

                User user = (User)responseDTO.getData();

                FragmentManager fm = ((BriefingActivity)view.getActivity()).getSupportFragmentManager();
                ConfirmBriefingRegisterDialog createUserDialog = ConfirmBriefingRegisterDialog.newInstance(BriefingPresenter.this, user);
                createUserDialog.show(fm, "fragment_briefing_confirm");
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Hide loading
                view.hideLoading();

                view.createMessage("Error. Unable to get user by NFC tag");
            }
        });
    }


    /**
     * Retrieve teams from database
     */
    void retrieveTeams(){

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveAllTeams(null, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<Team> teams = (List<Team>)responseDTO.getData();

                //Add All option
                Team teamAll = new Team("-1", "All");
                teams.add(0, teamAll);

                view.initializeTeamsSpinner(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes de error
            }

        });
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {
        if(v.getId() == R.id.delete_run_button){
            BriefingRegister selectedRegister = filteredBriefingRegisterList.get(position);
            openConfirmDeleteRegister(selectedRegister);

        }
    }


    public void openConfirmDeleteRegister(EventRegister register){
        //With all the information, we open the dialog
        FragmentManager fm = ((BriefingActivity)view.getActivity()).getSupportFragmentManager();
        DeleteEventRegisterDialog deleteEventRegisterDialog = DeleteEventRegisterDialog.newInstance(this, register);
        deleteEventRegisterDialog.show(fm, "delete_event_confirm");
    }


    public List<BriefingRegister> getBriefingRegisterList() {
        return filteredBriefingRegisterList;
    }

    public Date getSelectedDateFrom() {
        return selectedDateFrom;
    }

    public void setSelectedDateFrom(Date selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }

    public Date getSelectedDateTo() {
        return selectedDateTo;
    }

    public void setSelectedDateTo(Date selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
    }

    public String getSelectedTeamID() {
        return selectedTeamID;
    }

    public void setSelectedTeamID(String selectedTeamID) {
        this.selectedTeamID = selectedTeamID;
    }




    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void createMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoading();

        /**
         * Refresh items in list
         */
        void refreshBriefingRegisterItems();

        /**
         * Initialize teams spinner
         */
        void initializeTeamsSpinner(List<Team> teams);
    }

}
